package animals;



import graphics.ZooPanel;
import mobility.Point;




/**
 * Abstract class that defines shared properties of animals that roars. 
 * Extends Animal class 
 * @see Animal
 */
public abstract class AnimalThatRoars extends Animal {

	
	
	
	/**
	 * Constructor
	 * @param location - animal location
	 * @param size - animal size
	 * @param col - animal color
	 * @param horSpeed - animal horizontal speed
	 * @param verSpeed - animal vertical speed
	 * @param pan - reference to ZooPanel
	 */
	public AnimalThatRoars(Point location, int size, String col, int horSpeed, int verSpeed, ZooPanel pan) {
		super(location,size,col,horSpeed,verSpeed,pan);
	}

	
	
	
	@Override
	public void makeSound() {
		this.roar();
	}

	
	
	
	
	/**
	 * the animal roars
	 */
	protected abstract void roar();
}
