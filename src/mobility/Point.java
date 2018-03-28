package mobility;

/**
 * Represents a point
 */
public class Point {

	/**
	 * Maximum X value
	 */
	public static final int X_DIMENSIONS = 800;

	/**
	 * Maximum Y value
	 */
	public static final int Y_DIMENSIONS = 600;

	/**
	 * @param pointToCheck - the point to check
	 * @return True if the point is inside the boundaries
	 */
	public static boolean checkBoundaries(Point pointToCheck) {
		return pointToCheck.x <= X_DIMENSIONS && pointToCheck.x >= 0 && pointToCheck.y <= Y_DIMENSIONS
				&& pointToCheck.y >= 0;
	}

	public int x; // the x value
	public int y; // the y value

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point newLocation) {
		this(newLocation.x, newLocation.y);
	}

	/**
	 * @return The X value
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return The Y value
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param newX - the new X value
	 * @return True if assignment is successful
	 */
	public boolean setX(int newX) {
			this.x = newX;
			return true;

	}

	/**
	 * @param newY - the new Y value
	 * @return True if assignment is successful
	 */
	public boolean setY(int newY) {
		
			this.y = newY;
			return true;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}
