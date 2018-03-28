package graphics;


import java.awt.Graphics;
import java.awt.event.ActionListener;


/**
 * Interface for drawing object and images 
 * Extends Mobile class and implements IEdible,IDrawable,IAnimalBehavior and Runnable interfaces
 * @see ActionListener
 */
public interface IDrawable {
	
	/**
	 * Images path
	 */
	public final static String PICTURE_PATH = "src/pictures//";
	/**
	 * Load images 
	 * @param nm - part of image name to load
	 */
	public void loadImages(String nm);
	/**
	 * Draw object to panel
	 * @param g
	 */
	public void drawObject (Graphics g);
	/**
	 * Get colour
	 * @return colour
	 */
	public String getColor();	 
		 
	
}
