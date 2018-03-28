package mobility;


public interface ILocatable {

	/**
	 * @return The current location
	 */
	public Point getLocation();

	/**
	 * 
	 * @param location
	 *            - The new location
	 * @return True if assignment is successful
	 */
	public boolean setNewLocation(Point location);
}
