import java.awt.Point;

public class Sheet {
	// it is a given that the width of the Sheet > height of Sheet
	private double width;
	private double height;
	private Point pos;

	/**
	 * Basic constructor of Sheet Class
	 */
	public Sheet() {
		this(0, 0, 0, 0);
	}

	/**
	 * Constructor of Sheet
	 * 
	 * @param w width of Sheet
	 * @param h height of Sheet
	 */
	public Sheet(double w, double h) {
		this(w, h, 0, 0);
	}

	/**
	 * Constructor of Sheet
	 * 
	 * @param w width of Sheet
	 * @param h height of Sheet
	 * @param x x position of the top left corner of the Sheet
	 * @param y y position of the top left corner of the Sheet
	 */
	public Sheet(double w, double h, int x, int y) {
		height = h;
		width = w;
		pos = new Point(x, y);
	}

	/**
	 * Constructor of Sheet
	 * 
	 * @param w width of Sheet
	 * @param h height of Sheet
	 * @param p position of the top left corner of the Sheet
	 */
	public Sheet(double w, double h, Point p) {
		height = h;
		width = w;
		pos = p;
	}

	// returns the height of the Sheet
	public double getHeight() {
		return height;
	}

	// returns the width of the Sheet
	public double getWidth() {
		return width;
	}

	// returns the area of the Sheet
	/**
	 * Multiplies height and width of the Sheet
	 * 
	 * @return int: the area of the Sheet
	 */
	public double getArea() {
		return height * width;
	}

	// returns the x position of the Sheet
	// this is useful during recursion when a smaller Sheet is made at a certain
	// position
	// effectively cutting the placed design out of the original Sheet
	public double getX() {
		return pos.getX();
	}

	// returns the y position of the Sheet
	// this is useful during recursion when a smaller Sheet is made at a certain
	// position
	// effectively cutting the placed design out of the original Sheet
	public double getY() {
		return pos.getY();
	}

	// sets and returns the height of the Sheet
	public double setHeight(double h) {
		height = h;
		return height;
	}

	// sets and returns the width of the Sheet
	public double setWidth(double w) {
		width = w;
		return width;
	}

	/**
	 * Checks whether a given design will fit on the Sheet if it is rotated
	 * 
	 * @param d the design being compared
	 * @return boolean: whether the design fits on the Sheet
	 */
	public boolean fitsWithRotation(Design d) {
		d.setOrientation(true);
		return fits(d);
	}

	/**
	 * Checks whether a given design will fit on the Sheet if it is not rotated
	 * 
	 * @param d the design being compared
	 * @return boolean: whether the design fits on the Sheet
	 */
	public boolean fitsNoRotation(Design d) {
		d.setOrientation(false);
		return fits(d);
	}

	/**
	 * Checks whether a given design fits on a Sheet
	 * 
	 * @param d the design being compared
	 * @return boolean: whether the design fits on the Sheet
	 */
	private boolean fits(Design d) {
		return areaFits(d) && dimensionsFit(d);
	}

	/**
	 * Checks whether the area of the Sheet is larger than the area of the design
	 * 
	 * @param d the design being compared
	 * @return boolean: whether the area of the design is smaller than the area of
	 *         the Sheet
	 */
	private boolean areaFits(Design d) {
		return d.getArea() <= getArea();
	}

	// checks that the design's dimensions fit on the Sheet
	/**
	 * Checks the height of the design is less than the height of the Sheet, and the
	 * width of the design is less than the width of the Sheet
	 * 
	 * @param d the design being compared
	 * @return boolean: whether the dimensions of the designs fit on the Sheet
	 */
	private boolean dimensionsFit(Design d) {
		return (d.getHeight() <= height) && (d.getWidth() <= width);
	}

	// searches for dimensions that fill the width of the Sheet
	public String toString() {
		return "h: " + height + ", w: " + width;
	}

}