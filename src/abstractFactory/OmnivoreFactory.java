package abstractFactory;

import animals.Animal;
import animals.Bear;
import graphics.ZooPanel;




/**
 * Class that creates animal that belongs to omnivore factory. 
 * Extends implements CarnivoreFactory
 * @see AbstractZooFactory
 */
public class OmnivoreFactory implements AbstractZooFactory {

	@Override
	public Animal produceAnimal(String type,int size, String col, int horSpeed, int verSpeed,ZooPanel pan) {
		Animal animal = null;
		if(type.equals("Bear")){
			System.out.println("Bear created");
			animal = new Bear(size,col,horSpeed,verSpeed,pan);
		}
		return animal;
	}

}
