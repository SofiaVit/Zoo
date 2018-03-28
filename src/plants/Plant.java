package plants;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import food.EFoodType;
import food.IEdible;
import graphics.IDrawable;
import graphics.ZooPanel;
import mobility.ILocatable;
import mobility.Point;
import utilities.MessageUtility;
import utilities.Validators;

/**
 * Represents a plant
 */
public abstract class Plant implements IEdible, ILocatable, IDrawable {

	private double height;
	private Point location;
	private double weight;
	protected BufferedImage img1;
	protected ZooPanel pan;

	public Plant(ZooPanel pan) {
		Random rand = new Random();
		int x = rand.nextInt(30);
		int y = rand.nextInt(12);
		this.location = new Point(x, y);
		this.height = rand.nextInt(30);
		this.weight = rand.nextInt(12);
		this.pan = pan;
		MessageUtility.logConstractor("Plant", "Plant");
	}

	@Override
	public EFoodType getFoodType() {
		MessageUtility.logGetter(this.getClass().getSimpleName(), "getFoodType", EFoodType.VEGETABLE);
		return EFoodType.VEGETABLE;
	}

	/**
	 * @return The height of the plant
	 */
	public double getHeight() {
		MessageUtility.logGetter(this.getClass().getSimpleName(), "getHeight", this.height);
		return this.height;
	}

	@Override
	public Point getLocation() {
		MessageUtility.logGetter(this.getClass().getSimpleName(), "getLocation", this.location);
		return this.location;
	}

	/**
	 * @return The weight of the plant
	 */
	public double getWeight() {
		MessageUtility.logGetter(this.getClass().getSimpleName(), "getWeight", this.weight);
		return weight;
	}

	/**
	 * @param newHeight - the new height
	 * @return True if assignment is successful
	 */
	public boolean setHeight(double newHeight) {

		if (Validators.IsPositive(newHeight)) {
			this.height = newHeight;
			return true;
		}
		this.height = 0;

		return false;
	}

	@Override
	public boolean setNewLocation(Point newLocation) {
		if (Point.checkBoundaries(newLocation)) {
			this.location = new Point(newLocation);
			return true;
		}

		this.location = new Point(0, 0);
		return false;
	}

	/**
	 * @param newWeight - the new weight
	 * @return True if assignment is successful
	 */
	public boolean setWeight(double newWeight) {

		if (Validators.IsPositive(newWeight)) {
			this.weight = newWeight;
			return true;
		}
		this.weight = 0;

		return false;
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + "] ";
	}
	
	@Override
	public void loadImages(String nm) {
		  switch(nm){  
			 case "cabbage":
				 try { img1 = ImageIO.read(new File(PICTURE_PATH + nm + ".png"));
				 } catch (IOException e) { System.out.println("Cannot load picture"); }
				 break;
			 case "lettuce":
				 try { img1 = ImageIO.read(new File(PICTURE_PATH + nm + ".png"));
				 }catch (IOException e) { System.out.println("Cannot load picture"); }			 
				 }
		  }
	
	
	public String getColor(){
		return "color";
	}
	

}
