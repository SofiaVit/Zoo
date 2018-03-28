package animals;


import diet.Herbivore;
import graphics.ZooPanel;
import mobility.Point;
import utilities.MessageUtility;

/**
 * Class that defines methods and attributes specific to Elephant
 * @see AnimalThatChews
 */
public class Elephant extends AnimalThatChews {


	/**
	 * Elephant constructor
	 * @param size - Elephant size
	 * @param col - Elephant colour
	 * @param horSpeed - Elephant horizontal speed
	 * @param verSpeed - Elephant vertical speed
	 * @param pan - reference to ZooPanel
	 */
	public Elephant(int size, String col, int horSpeed, int verSpeed,ZooPanel pan) {
		super(new Point(0, 0),size,col,horSpeed,verSpeed, pan);
		this.setWeight(size*10);
		this.setDiet(new Herbivore());
		this.loadImages("elf");
		this.setName("Elephant");
	}

	
	
	@Override
	public void chew() {
		MessageUtility.logSound(this.name, "Trumpets with joy while flapping its ears, then chews");
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
			try{
				Thread.sleep(50);
			}catch(InterruptedException e){
				System.out.println(getAnimalName() + "dead...");
				return;}
			 if(pan.plant == null ){
				this.animalMove();
				
	
			}
			else if(pan.plant != null ){
				this.animalMoveFoodExist();
			}
		
			
			}
			
		}
		}
	

	
}


