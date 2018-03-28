package animals;


import diet.Herbivore;
import graphics.ZooPanel;
import mobility.Point;
import utilities.MessageUtility;

/**
 * Class that defines methods and attributes specific to Giraffe
 * @see AnimalThatChews
 */
public class Giraffe extends AnimalThatChews {


	
	/**
	 * Giraffe constructor
	 * @param size - Giraffe size
	 * @param col - Giraffe colour
	 * @param horSpeed - Giraffe horizontal speed
	 * @param verSpeed - Giraffe vertical speed
	 * @param pan - reference to ZooPanel
	 */
	public Giraffe(int size, String col, int horSpeed, int verSpeed,ZooPanel pan) {
		super(new Point(0, 0),size,col,horSpeed,verSpeed, pan);
		this.setWeight(size*2.2);
		this.setDiet(new Herbivore());
		this.loadImages("grf");
		this.setName("Giraffe");
	}

	
	
	@Override
	public void chew() {
		MessageUtility.logSound(this.name, "Bleats and Stomps its legs, then chews");
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

