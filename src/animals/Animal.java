package animals;



import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import decorator.ColoredAnimal;
import diet.IDiet;
import food.EFoodType;
import food.IEdible;
import graphics.IAnimalBehavior;
import graphics.IDrawable;
import graphics.ZooPanel;
import mobility.Point;
import utilities.MessageUtility;

/**
 * Abstract class that defines shared properties of all animals. 
 * Extends Mobile class and implements IEdible,IDrawable,IAnimalBehavior and Runnable interfaces
 * @see Mobile, IEdible, IDrawable, IAnimalBehavior,Runnable,ColoredAnimal,Cloneable
 */
public abstract class Animal extends Observable implements IEdible,IDrawable,IAnimalBehavior,Runnable,ColoredAnimal,Cloneable {

	
	/**
	 * Weight loss factor after moving
	 */
	public static final double WEIGHT_LOSS_FACTOR = 0.00025;

	
	/**
	 * Animal attributes
	 */
	private IDiet diet;
	private double weight;
	protected String name;
	private final int EAT_DISTANCE = 5;
	protected int size;
	private String col;
	protected int horSpeed,verSpeed;
	private boolean coordChanged;
	protected int x_dir;
	protected int y_dir;
	private int eatCount;
	protected ZooPanel pan;
	protected boolean threadSuspended;	 
	private BufferedImage img1, img2;
	protected Future<?> task;
	protected boolean isRunning;
	protected Point location;


	
	/**
	 * Animal constructor
	 * @param location - animal starting location
	 * @param size - animal size
	 * @param col - animal colour
	 * @param horSpeed - animal horizontal speed
	 * @param verSpeed - animal vertical speed
	 * @param pan - reference to ZooPanel
	 */
	public Animal(Point location, int size, String col, int horSpeed, int verSpeed, ZooPanel pan) {

		
		this.setLocation(location);
		this.pan = pan;
		this.col = col;
		this.size = size;
		this.horSpeed = horSpeed;
		this.verSpeed = verSpeed;
		threadSuspended = false;
		coordChanged = false;
		y_dir = 1;
		x_dir = 1;
		isRunning = true;
		
	}

	
	
	
	/**
	 * The animal eats the food and gains weight
	 * 
	 * @param food
	 *            - Food to eat
	 * @return True if food was eaten
	 */
	public boolean eat(IEdible food) {
		double gainedWeight = this.diet.eat(this.weight, food);
		if (gainedWeight > 0) {
			this.weight += gainedWeight;
			this.makeSound();
			return true;
		}
		return false;
	}

	
	
	
	/**
	 * @return the diet
	 */
	public IDiet getDiet() {
		return this.diet;
	}

	
	

	@Override
	public EFoodType getFoodType() {
		return EFoodType.MEAT;
	}


	
	
	/**
	 * @return animal weight
	 */
	public double getWeight() {
		return this.weight;
	}
	
	
	
	
	@Override
	public String getColor(){
		return this.col;
	}

	
	
	
	/**
	 * Animal makes a sound
	 */
	public abstract void makeSound();


	
	
	
	/**
	 * @param newDiet
	 */
	protected void setDiet(IDiet newDiet) {
		this.diet = newDiet;
	}


	
	
	/**
	 * @param newWeight
	 *            - The new weight
	 * @return True if assignment is successful
	 */
	public boolean setWeight(double newWeight) {
		if (utilities.Validators.IsPositive(newWeight)) {
			this.weight = newWeight;
			return true;
		}
		return false;
	}
	
	
	
	
	@Override
	public int getSize(){
		return this.size;
	}
	
	
	
	
	@Override
	public void eatInc(){
			this.eatCount++;
	}
	
	
	
	
	@Override
	public int getEatCount(){
		return this.eatCount;
	}
	
	
	
	
	@Override
	public void loadImages(String nm) {
		  switch(getColor () ){  
			 case "Red":
				 try { img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_r_1.png"));
				 	   img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_r_2.png"));} 
				 catch (IOException e) { System.out.println("Cannot load picture"); }
				 break;
			 case "Blue":
				 try { img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_b_1.png"));
				 	   img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_b_2.png"));} 
				 catch (IOException e) { System.out.println("Cannot load picture"); }
				 break;
			 default:
				 try { img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_n_1.png"));
			 	       img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_n_2.png"));} 
				 catch (IOException e) { System.out.println("Cannot load picture"); }			 
				 }
		  }
	
	
	
	
	@Override
	public  boolean getChanges() {
			return coordChanged;
	}

	
	
	@Override
	public void setSuspended() {
		threadSuspended = true;	
	}

	
	
	
	@Override
	public synchronized void setResumed() {
		threadSuspended = false;
			notify();
	}
	
	
	
	
	/**
	 * Check if thread is suspended
	 * @return true if thread is suspended, false otherwise
	 */
	public  boolean getSusspended(){
		return threadSuspended;
	}

	
	
	
	@Override
	public  void setChanges (boolean state) {
			coordChanged = state;	
	}
	
	
	
	/**
	 * Animal regular movement (No food that he eats exist)
	 */
	protected void animalMove(){
		
		location.setX(location.getX() + horSpeed*x_dir);
		location.setY(location.getY() + verSpeed*y_dir);
		
		if(location.getX() <= 0)
			x_dir = 1;
		else if(location.getX()+ size >= pan.getWidth())
				x_dir = -1;
		 if(location.getY() <= 0)
	    		y_dir = 1;
		 else if(location.getY() + size+size/6 >= pan.getHeight())
				y_dir = -1;
			
		setChanges(true);
		setChanged();
		notifyObservers();
	}
	
	
	
	
	/**
	 * Animal movement towards food 
	 */
	protected void animalMoveFoodExist(){
		double oldSpeed = Math.sqrt(horSpeed*horSpeed+verSpeed*verSpeed);
		double newHorSpeed = oldSpeed*(location.getX() - pan.getWidth()/2)/
				(Math.sqrt(Math.pow(location.getX() - pan.getWidth()/2, 2)+
						Math.pow(location.getY()-pan.getHeight()/2, 2)));
		double newVerSpeed = oldSpeed*(location.getY() - pan.getHeight()/2)/
				(Math.sqrt(Math.pow(location.getX() - pan.getWidth()/2, 2)+
						Math.pow(location.getY()-pan.getHeight()/2, 2)));
		int v = 1;
		if(newVerSpeed<0){
			v=-1;
			newVerSpeed = -newVerSpeed;
		}
		if(newVerSpeed > 10){
			newVerSpeed = 10;
		}
		else if(newVerSpeed < 1){
			if(location.getY() != pan.getHeight()/2)
				newVerSpeed = 1;
			else
				newVerSpeed = 0;
		}
		int h = 1;
		if(newHorSpeed<0){
			h=-1;
			newHorSpeed = -newHorSpeed;
		}
		if(newHorSpeed > 10)
			newHorSpeed = 10;
		else if(newHorSpeed < 1){
			if(location.getX() != pan.getWidth()/2)
				newHorSpeed = 1;
			else
				newHorSpeed = 0;
		}
		
		location.setX((int)(location.getX() - newHorSpeed*h));
		location.setY((int)(location.getY() - newVerSpeed*v));
		if(location.getX() < pan.getWidth()/2)
			x_dir = 1;
		else 
			x_dir = -1;
		if(Math.abs(location.getX() - pan.getWidth()/2)<EAT_DISTANCE 
			&& Math.abs(location.getY() - pan.getHeight()/2) < EAT_DISTANCE)
			pan.eatFood(this);
	
		setChanges(true);
		setChanged();
		notifyObservers();
	}
	
	
	
	
	@Override
	public void drawObject(Graphics g) {
		if(x_dir==1) // Bear goes to right side
			g.drawImage(img1, location.x, location.y, size, size, pan);
		else 
			g.drawImage(img2, location.x, location.y, size, size, pan);	

	}
	
	
	
	
	/**
	 * Get animal horizontal speed
	 * @return horizontal speed
	 */
	public int getHorSpeed(){
		return horSpeed;
	}
	
	
	
	
	/**
	 * Get animal vertical speed
	 * @return vertical speed
	 */
	public int getVerSpeed() {
		return verSpeed;
	}
	
	
	
	
	@Override
	public String getAnimalName() {
		return this.name;
	}
	
	
	
	/**
	 * Set animal name
	 * @param name - animal name
	 * @return true if name contains only letters, false otherwise
	 */
	public boolean setName(String name){
		if(name.matches("[a-zA-Z]+")){
			this.name = name;
			MessageUtility.logSetter(name, "setName", name, true);
			return true;
		}
		MessageUtility.logSetter(name, "setName", name, false);
		return false;
	}

	
	
	
	/**
	 * Returns a string representation of this object
	 * @return a string representation of this object
	 */
	public String toString() {
		return "[" + this.getClass().getSimpleName() + "] " + name;
	}

	
	
	
	/**
	 * Set future task
	 * @param task - runnable task
	 */
	public void setFutureTask(Future<?> task){
		this.task = task;
	}
	
	
	
	
	/**
	 * Get future task
	 * @return task;
	 */
	public Future<?> getFutureTask(){
		return task;
	}
	
	
	
	
	/**
	 * Check if animal is running
	 * @return True if animal is running, false otherwise
	 */
	public boolean getIsRunnig(){
		return isRunning;
	}
	
	
	
	
	/**
	 * Set if animal is running or not
	 * @param state - true or false. true if animal is running, false otherwise.
	 */
	public void setIsRunnig(boolean state){
		isRunning = state;
	}
	
	
	
	
	/**
	 * Set animal color
	 * @param color - name of color
	 */
	protected void setColor(String color){
		this.col = color;
	}
	
	
	
	
	/**
	 * Get animal location
	 * @return location (x and y)
	 */
	public Point getLocation() {
		return location;
	}
	
	
	
	
	/**
	 * Set animal location
	 * @param newLocation - new location of an animal
	 * @return false if location is null. true if location changed
	 */
	public boolean setLocation(Point newLocation) {
		if (newLocation == null) {
			this.location = new Point(0, 0);
			return false;
		}
			this.location = new Point(newLocation);
			return true;
	}
	
	
	
	
	/**
	 * Clone animal
	 * @return cloned animal
	 */
	public  Animal clone()  throws CloneNotSupportedException  {
		Animal clone = null;
		try {
			clone = (Animal) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}    
		return clone;
	}

	
	
	
	/**
	 * Set animal horizontal speed
	 * @param value - horizontal speed
	 */
	public void setHorSpeed(int value) {
		this.horSpeed = value;		
	}

	
	
	
	/**
	 * Set animal vertical speed
	 * @param value - vertical speed
	 */
	public void setVerSpeed(int value) {
		this.verSpeed = value;
		
	}
	
	
	
	

	@Override
	public void PaintAnimal(String color){
		this.setColor(color);
		String nm = null;
		switch(this.getAnimalName()){
		case "Giraffe":
			nm = "grf";
			break;
		case "Lion":
			nm = "lio";
			break;
		case "Turtle":
			nm = "trt";
			break;
		case "Bear":
			nm = "bea";
			break;
		case "Elephant":
			nm = "elf";
			break;
		}
		this.loadImages(nm);
	}

}
