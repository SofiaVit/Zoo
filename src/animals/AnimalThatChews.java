package animals;



import graphics.ZooPanel;
import mobility.Point;



/**
 * Abstract class that defines shared properties of animals that chews. 
 * Extends Animal class 
 * @see Animal
 */
public abstract class AnimalThatChews extends Animal {

	
	
	/**
	 * Constructor
	 * @param location - animal location
	 * @param size - animal size
	 * @param col - animal color
	 * @param horSpeed - animal horizontal speed
	 * @param verSpeed - animal vertical speed
	 * @param pan - reference to ZooPanel
	 */
	public AnimalThatChews(Point location, int size, String col, int horSpeed, int verSpeed,ZooPanel pan) {
		super(location,size,col,horSpeed,verSpeed,pan);
	}

	
	
	
	/**
	 * the animal chews
	 */
	protected abstract void chew();

	
	
	
	@Override
	public void makeSound() {
		this.chew();
	}
}
