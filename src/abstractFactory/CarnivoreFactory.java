package abstractFactory;

import animals.Animal;
import animals.Lion;
import graphics.ZooPanel;




/**
 * Class that creates animal that belongs to carnivore factory. 
 * Extends implements CarnivoreFactory
 * @see AbstractZooFactory
 */
 public class CarnivoreFactory implements AbstractZooFactory {

	@Override
	public Animal produceAnimal(String type,int size, String col, int horSpeed, int verSpeed,ZooPanel pan) {
		Animal animal = null;
		if(type.equals("Lion"))
			animal = new Lion(size,col,horSpeed,verSpeed,pan);
		return animal;
	}

}
