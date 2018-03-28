package diet;

import food.EFoodType;
import food.IEdible;


public interface IDiet {

	/**
	 * @param food
	 *            - food to test
	 * @return true if food can bee eaten
	 */
	public boolean canEat(EFoodType food);

	/**
	 * @param currentWeight
	 *            - the animal current weight, used to calculate the additional
	 *            weight after eating
	 * @param food
	 *            - food to eat
	 * @return The weight to be added
	 */
	public double eat(double currentWeight, IEdible food);
}
