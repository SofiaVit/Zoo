package memento;

import java.util.Vector;

import animals.Animal;
import plants.Plant;



/**
 * Class to save animals and food states.
 */
public class ZooMemento {
	
	
	/**
	 * ZooMemento attributes
	 */
	private Vector<Animal> animals;
	private Plant plant;
	private boolean meat;
	
	
	
	/**
	 * Constructor
	 * @param animals - Vector of animals (Objects of type Animal)
	 * @param plant - Object of type Plant
	 * @param meat - boolean. false if there are no meat and true otherwise
	 */
	public ZooMemento(Vector<Animal>animals,Plant plant,boolean meat){
		this.animals = new Vector<Animal>();
		for(Animal animal : animals){
			try {
				Animal newAnimal = animal.clone();
				newAnimal.setLocation(animal.getLocation());
				this.animals.add(newAnimal);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
		}
		this.plant = plant;
		this.meat = meat;
	}
	
	
	
	
	/**
	 * Function to get list of animals
	 * @return vector of animals
	 */
	public Vector<Animal> getAnimals(){
		return animals;
	}
	
	
	
	
	/**
	 * Get plant
	 * @return plant or null if there are no plant
	 */
	public Plant getPlant(){
		return plant;
	}
	
	
	
	
	/**
	 * Get meat
	 * @return true if meat exist and false otherwise
	 */
	public boolean getMeat(){
		return meat;
	}
}

