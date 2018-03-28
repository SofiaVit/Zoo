package abstractFactory;

import animals.Animal;
import animals.Elephant;
import animals.Giraffe;
import animals.Turtle;
import graphics.ZooPanel;



/**
 * Class that creates animal that belongs to herbivore factory. 
 * Extends implements CarnivoreFactory
 * @see AbstractZooFactory
 */
public class HerbivoreFactory implements AbstractZooFactory {

	@Override
	public Animal produceAnimal(String type,int size, String col, int horSpeed, int verSpeed,ZooPanel pan) {
		Animal animal = null;
		if(type.equals("Elephant"))
			animal = new Elephant(size,col,horSpeed,verSpeed,pan);
		else if(type.equals("Giraffe"))
			animal = new Giraffe(size,col,horSpeed,verSpeed,pan);
		else if(type.equals("Turtle"))
			animal = new Turtle(size,col,horSpeed,verSpeed,pan);
		
		return animal;
	}

}
