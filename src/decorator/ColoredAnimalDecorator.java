package decorator;



/**
 * Class to paint animal
 * Implements ColoredAnimal
 * @see ColoredAnimal
 */
public class ColoredAnimalDecorator implements ColoredAnimal {
	
	/**
	 * Attributes
	 */
	protected ColoredAnimal animal;
	
	
	
	
	/**
	 * Constructor
	 * @param animal - object of animal to paint afterward 
	 */
	public ColoredAnimalDecorator(ColoredAnimal animal){
		this.animal = animal;
	}

	
	
	
	@Override
	public void PaintAnimal(String color) {
		animal.PaintAnimal(color);
		
	}
	
	
}
