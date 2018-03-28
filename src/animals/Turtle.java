package animals;



import diet.Herbivore;
import graphics.ZooPanel;
import mobility.Point;
import utilities.MessageUtility;

/**
 * Class that defines methods and attributes specific to Turtle
 * @see AnimalThatChews
 */
public class Turtle extends AnimalThatChews {

	
	
	/**
	 * Turtle constructor
	 * @param size - Turtle size
	 * @param col - Turtle colour
	 * @param horSpeed - Turtle horizontal speed
	 * @param verSpeed - Turtle vertical speed
	 * @param pan - reference to ZooPanel
	 */
	public Turtle(int size, String col, int horSpeed, int verSpeed,ZooPanel pan) {
		super(new Point(0, 0),size,col,horSpeed,verSpeed, pan);
		this.setWeight(size*0.5);
		this.setDiet(new Herbivore());
		this.loadImages("trt");
		this.setName("Turtle");
	}

	
	
	@Override
	protected void chew() {
		MessageUtility.logSound(this.name, "Retracts its head in then eats quietly");
	}
	

	
	@Override
	public void run() {
		while(true){
			synchronized(this){
				while(threadSuspended){
					try{
						wait();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
			try{
				Thread.sleep(50);
			}catch(InterruptedException e){
				System.out.println(getAnimalName() + "dead...");
				return;}
			if(pan.plant == null){
				this.animalMove();
				
	
			}
			else if(pan.plant != null){
				this.animalMoveFoodExist();
			}
			}
		}
	
}
