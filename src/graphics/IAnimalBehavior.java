package graphics;

import java.awt.event.ActionListener;

/**
 * Interface that describes animal behaviour
 * Extends Mobile class and implements IEdible,IDrawable,IAnimalBehavior and Runnable interfaces
 * @see ActionListener
 */
public interface IAnimalBehavior {
	/**
	 * Get animal name
	 * @return animal name
	 */
	public String getAnimalName();
	/**
	 * Get animal size
	 * @return animal size
	 */
	public int getSize();
	/**
	 * Increase number of food eaten by animal
	 */
	public void eatInc();
	/**
	 * Get number of food eaten by animal
	 * @return number of food
	 */
	public int getEatCount();
	/**
	 * Get changes - if animal moved or not
	 * @return true-if animal moved, false-if not
	 */
	public boolean getChanges();
	/**
	 * Set "flag" that indicates that thread is suspended
	 */
	public void setSuspended();
	/**
	 * Set "flag" that indicates that thread is resumed
	 */
	public void setResumed();
	/**
	 * Set changes, true if animal moved, false otherwise
	 * @param state - true or false
	 */
	public void setChanges (boolean state);

	 
}
