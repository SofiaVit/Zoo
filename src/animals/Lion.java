package animals;


import diet.Carnivore;

import food.EFoodType;

import graphics.ZooPanel;
import mobility.Point;
import utilities.MessageUtility;


/**
 * Class that defines methods and attributes specific to Giraffe

 * @see AnimalThatRoars
 */
public class Lion extends AnimalThatRoars {


	
	/**
	 * Giraffe constructor
	 * @param size - Giraffe size
	 * @param col - Giraffe colour
	 * @param horSpeed - Giraffe horizontal speed
	 * @param verSpeed - Giraffe vertical speed
	 * @param pan - reference to ZooPanel
	 */
	public Lion(int size, String col, int horSpeed, int verSpeed,ZooPanel pan) {
		super(new Point(0, 0),size,col,horSpeed,verSpeed, pan);
		this.setWeight(size*0.8);
		this.setDiet(new Carnivore());
		this.loadImages("lio");
		this.setName("Lion");
	}



	@Override
	public EFoodType getFoodType() {
		return EFoodType.NOTFOOD;
	}

	
	
	@Override
	public void roar() {
		MessageUtility.logSound(this.name, "Roars, then stretches and shakes its mane");
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

			if(pan.meat == false){
				this.animalMove();
				
	
			}
			else if(pan.meat == true){
				this.animalMoveFoodExist();
			}
		
			
			}
		}
	

}
