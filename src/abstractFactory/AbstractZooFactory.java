package abstractFactory;

import animals.Animal;
import graphics.ZooPanel;


/**
 * Interface to produce animal by factory 
 */
public interface AbstractZooFactory {

	
	
	
	/**
	 * Produce animal by factory
	 * @param type - type of animal (Factory- carnivore,herbivore or omnivore)
	 * @param size - size of animal
	 * @param col - color of animal
	 * @param horSpeed - animal horizontal speed
	 * @param verSpeed - animal vertical speed
	 * @param pan - reference to ZooPanel
	 * @return produced animal
	 */
	public Animal produceAnimal(String type,int size, String col, int horSpeed, int verSpeed,ZooPanel pan);
}
