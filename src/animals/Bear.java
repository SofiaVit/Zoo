package animals;


import diet.Omnivore;
import graphics.ZooPanel;
import mobility.Point;
import utilities.MessageUtility;



/**
 * Class that defines methods and attributes specific to bear
 * @see AnimalThatRoars
 */
public class Bear extends AnimalThatRoars {
	

	/**
	 * Bear constructor
	 * @param size - bear size
	 * @param col - bear colour
	 * @param horSpeed - bear horizontal speed
	 * @param verSpeed - bear vertical speed
	 * @param pan - reference to ZooPanel
	 */
	public Bear(int size, String col, int horSpeed, int verSpeed,ZooPanel pan) {
		super(new Point(0, 0),size,col,horSpeed,verSpeed, pan);
		this.setWeight(size*1.5);
		this.setDiet(new Omnivore());
		this.loadImages("bea");
		this.setName("Bear");
	}



	@Override
	public void roar() {
		MessageUtility.logSound(this.name, "Stands on its hind legs, roars and scratches its belly");
	}



	@Override
	public void run() {
		
		while(true){
			synchronized(this){
				while(threadSuspended){
					try{
						System.out.println("Wait");
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
		
			if(pan.plant == null && pan.meat == false){
				this.animalMove();
	
			}
			else if(pan.plant != null || pan.meat == true){
				this.animalMoveFoodExist();
			}
			}
			
			}
		}

}
		


