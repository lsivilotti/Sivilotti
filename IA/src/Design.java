import java.awt.Color;
import java.awt.Point;

public class Design implements Comparable<Design> {
	private int width;
	private int height;
	private int demand;
	private int qps;
	private boolean fits;
	private boolean rotated;
	private String id;
	private Point pos;
	private Color col;

	/**
	 * Basic constructor of Design Class
	 */
	public Design() {
		this(0, 0, 0, "", 0, 0, null);
	}

	/**
	 * Constructor of Design
	 * 
	 * @param w  width of the Design
	 * @param h  height of the Design
	 * @param d  number of the Design requested
	 * @param ID ID of the Design
	 */
	public Design(int w, int h, int d, String ID) {
		this(w, h, d, ID, 0, 0, null);
	}

	/**
	 * Constructor of Design
	 * 
	 * @param w  width of the Design
	 * @param h  height of the Design
	 * @param d  number of the Design requested
	 * @param ID ID of the Design
	 * @param x  x position of the top left corner of the Design
	 * @param y  y position of the top left corner of the Design
	 * @param c  color the Design will get filled in on the layout
	 */
	public Design(int w, int h, int d, String ID, int x, int y, Color c) {
		width = w;
		height = h;
		demand = d;
		qps = 0;
		fits = false;
		rotated = false;
		id = ID;
		pos = new Point(x, y);
		col = c;
	}

	/**
	 * Constructor of Design
	 * 
	 * @param w  width of the Design
	 * @param h  height of the Design
	 * @param d  number of the Design requested
	 * @param ID ID of the Design
	 * @param p  point of the top left corner of the Design
	 * @param c  color the Design will get filled in on the layout
	 */
	public Design(int w, int h, int d, String ID, Point p, Color c) {
		width = w;
		height = h;
		demand = d;
		qps = 0;
		fits = false;
		rotated = false;
		id = ID;
		pos = new Point(p);
		col = c;
	}

	/**
	 * Constructor of Design
	 * 
	 * @param d Design already instantiated
	 */
	public Design(Design d) {
		this(d.getWidth(), d.getHeight(), d.getDemand(), d.getID(), d.getPos(), d.getColor());
	}

	// returns width of the Sheet (x-axis)
	public int getWidth() {
		return width;
	}

	// returns height of the Sheet (y-axis)
	public int getHeight() {
		return height;
	}

	// returns total demand of the Design
	public int getDemand() {
		return demand;
	}

	// returns number of Designs that can fit on one Sheet
	public int getQPS() {
		return qps;
	}

	// returns whether the Design fits on the Sheet
	public boolean getFit() {
		return fits;
	}

	// returns whether the Design is rotated or not
	public boolean getOrientation() {
		return rotated;
	}

	// returns the id of the Design
	public String getID() {
		return id;
	}

	// returns the (x, y) coordinate of the top left corner
	public Point getPos() {
		return pos.getLocation();
	}

	// returns the color of the Design
	public Color getColor() {
		return col;
	}

	// returns the area of the Design
	/**
	 * Multiplies the height and width of the Design
	 * 
	 * @return int: the area of the Design
	 */
	public int getArea() {
		return height * width;
	}

	// sets and returns the width of the Design
	public int setWidth(double w) {
		width = (int) Math.ceil(w);
		return width;
	}

	// sets and returns the height of the Design
	public int setHeight(int h) {
		height = (int) Math.ceil(h);
		return height;
	}

	// sets and returns the demand of the Design
	public int setDemand(int d) {
		demand = d;
		return demand;
	}

	// sets and returns the qps of the Design
	public int setQPS(int quant) {
		qps = quant;
		return qps;
	}

	// sets and returns whether the Design fits on the Sheet
	public boolean setFit(boolean f) {
		fits = f;
		return fits;
	}

	// sets and returns whether the Design is rotated as well as flip height and
	// width
	public boolean setOrientation(boolean r) {
		if (rotated != r) {
			int temp = height;
			height = width;
			width = temp;
			rotated = r;
		}
		return rotated;
	}

	// sets and returns the ID of the Design
	public String setID(String ID) {
		id = ID;
		return id;
	}

	// sets and returns the location of the Design given an x coordinate and y
	// coordinate (ints)
	public Point setPos(int x, int y) {
		pos.setLocation(x, y);
		return pos.getLocation();
	}

	// sets and returns the location of the Design given Point p
	public Point setPos(Point p) {
		pos.setLocation(p);
		return pos.getLocation();
	}

	// sets and returns the location of the Design given an x coordinate and y
	// coordinate (doubles)
	public Point setPos(double x, double y) {
		pos.setLocation(x, y);
		return pos.getLocation();
	}

	// sets the new x coordinate and returns the location of the Design
	public Point setX(int x) {
		pos.setLocation(x, pos.getY());
		return pos.getLocation();
	}

	// sets the new y coordinate and returns the location of the Design
	public Point setY(int y) {
		pos.setLocation(pos.getX(), y);
		return pos.getLocation();
	}

	// sets the color of the Design using Color class
	public Color setColor(Color c) {
		col = c;
		return col;
	}

	// sets the color of the Design by instantiating a new color
	public Color setColor(int r, int g, int b) {
		col = new Color(r, g, b);
		return col;
	}

	// compares the areas of two Designs (larger returns 1, smaller returns -1)
	public int compareTo(Design other) {
		if ((this.getArea() > other.getArea())) {
			return 1;
		} else if ((this.getArea() < other.getArea())) {
			return -1;
		} else {
			return 0;
		}
	}

	// to String method
	public String toString() {
		return "\nID: " + getID() + "\nPosition: " + getPos() + "\nWidth: " + getWidth() + "\nHeight: " + getHeight()
				+ /* "\nDemand: " + getDemand() + "\nQuantity per Sheet: " + getQPS() + */"\nFits: " + getFit()
				+ "\nRotated: " + getOrientation();
	}
}
