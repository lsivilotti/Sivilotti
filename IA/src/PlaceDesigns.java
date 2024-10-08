import java.util.LinkedList;

public class PlaceDesigns {
	private LinkedList<Design> designs;
	private LinkedList<Sheet> sheets;
	private double minDesignDimension;
	private boolean areasFit;

	/**
	 * Constructor for PlaceDesigns class
	 * 
	 * @param d LinkedList of Designs
	 * @param s LinkedList of Sheets
	 */
	public PlaceDesigns(LinkedList<Design> d, LinkedList<Sheet> s) {
		designs = d;
		sheets = s;
		minDesignDimension = calcMinDimension();
		areasFit = true;
	}

	/**
	 * Constructor for PlaceDesigns class
	 * 
	 * @param s LinkedList of Sheets
	 */
	public PlaceDesigns(LinkedList<Sheet> s) {
		designs = new LinkedList<Design>();
		sheets = s;
		minDesignDimension = calcMinDimension();
		areasFit = true;
	}

	/**
	 * Constructor for PlaceDesigns class
	 * 
	 * @param s first Sheet, will be the original size of the sheet
	 */
	public PlaceDesigns(Sheet s) {
		designs = new LinkedList<Design>();
		sheets = new LinkedList<Sheet>();
		sheets.add(s);
		minDesignDimension = calcMinDimension();
		areasFit = true;
	}

	/**
	 * adds Designs to LinkedList designs
	 * uses binary sort to order the designs from largest to smallest area
	 * recalculates minDesignDimension and areasFit
	 * 
	 * @param d Design being added
	 * @return LinkedList of all the Designs
	 */
	public LinkedList<Design> addDesign(Design d) {
		int tooLow = -1;
		int highEnough = designs.size();
		while (highEnough - tooLow > 1) {
			// true: designs[too low] > d >= designs[high enough]
			int mid = (highEnough + tooLow) / 2;
			if (d.compareTo(designs.get(mid)) > 0) {
				highEnough = mid;
			} else {
				tooLow = mid;
			}
		}
		designs.add(highEnough, d);
		minDesignDimension = calcMinDimension();
		areasFit = areaCheck();
		return designs;
	}

	/**
	 * removes the design at a given index
	 * 
	 * @param index the index that the design being removed is at
	 * @return Design removed from the LinkedList
	 */
	public Design removeDesign(int index) {
		Design d = designs.remove(index);
		minDesignDimension = calcMinDimension();
		return d;
	}

	/**
	 * adds Sheet to LinkedList sheets
	 * only adds the sheet if it has a dimension larger than the smallest dimension
	 * a Design in designs has
	 * prevents sheets that can't fit any desings from being added to sheets
	 * 
	 * @param s Sheet being added to the LinkedList
	 */
	public void addSheet(Sheet s) {
		if (s.getWidth() >= minDesignDimension && s.getHeight() >= minDesignDimension) {
			sheets.add(s);
		}
	}

	/**
	 * checks that the total area of the designs are smaller than the total area of
	 * sheets
	 * 
	 * @return boolean: whether the total area of designs is smaller than or equal
	 *         to the sheets
	 */
	public boolean areaCheck() {
		int totalDArea = 0;
		int totalSArea = 0;
		for (Design d : designs) {
			totalDArea += d.getArea();
		}
		for (Sheet s : sheets) {
			totalSArea += s.getArea();
		}
		return totalDArea <= totalSArea;
	}

	/**
	 * tries placing a design on any Sheet in sheets, trying the design normally
	 * with a horizontal cut immediately after, the design normally with a vetical
	 * cut immediately after, the design rotated with a horizontal cut immediately
	 * after, and the design rotated with a vertical cut immediately after
	 * 
	 * Uses the heuristics of pickDesign()
	 * 
	 * @return boolean: whether the design fits, and every design that is placed
	 *         after it also fits
	 */
	public boolean fitDesigns() {
		if (!areasFit) {
			return false;
		}
		// picks next design to placed
		int index = pickDesign();
		Design d;
		if (index == designs.size()) {
			return true;
		}
		d = designs.get(index);
		boolean go = false;
		index = 0;
		while (!go && index < sheets.size()) {
			Sheet s = sheets.remove(index);
			/**
			 * original orientation with a horizontal cut immediately after
			 */
			// checks that the design fits on s in its original orientation
			if (s.fitsNoRotation(d)) {
				d.setFit(true);
				d.setPos(s.getX(), s.getY());
				/**
				 * cuts s horizontally first by creating a new Sheet directly below s with a
				 * width equal to s and a height of s height - design height
				 */
				Sheet s1 = new Sheet(s.getWidth(), s.getHeight() - d.getHeight(), (int) s.getX(),
						(int) s.getY() + d.getHeight());
				/**
				 * cuts s vertically next by creating a new Sheet directly beside s with a
				 * width of s width - design width and a height equal to s
				 */
				Sheet s2 = new Sheet(s.getWidth() - d.getWidth(), d.getHeight(), (int) s.getX() + d.getWidth(),
						(int) s.getY());
				/**
				 * adds the two new Sheets to sheets, calls the "helper method", then once the
				 * "helper method" is done removes either the 0, 1, or 2 Sheets that were added
				 * to sheets depending on their dimensions
				 * (check addSheet() method for more details)
				 */
				int origLength = sheets.size();
				addSheet(s1);
				addSheet(s2);
				go = fitDesigns();
				while (sheets.size() > origLength) {
					sheets.removeLast();
				}
			}
			/**
			 * original orientation and vertical cut
			 */
			// checks that the Design fits on s in its original orientation and that the
			// previous if statement didn't fit all the Designs
			if (!go && s.fitsNoRotation(d)) {
				d.setFit(true);
				d.setPos(s.getX(), s.getY());
				// vertical cut (look in the first if statement of loop)
				Sheet s1 = new Sheet(s.getWidth() - d.getWidth(), s.getHeight(), (int) s.getX() + d.getWidth(),
						(int) s.getY());
				// horizontal cut (look in the first if statement of loop)
				Sheet s2 = new Sheet(d.getWidth(), s.getHeight() - d.getHeight(), (int) s.getX(),
						(int) s.getY() + d.getHeight());
				// check first if statement of loop
				int origLength = sheets.size();
				addSheet(s1);
				addSheet(s2);
				go = fitDesigns();
				while (sheets.size() > origLength) {
					sheets.removeLast();
				}
			}
			/**
			 * rotated orientation and horizontal cut
			 */
			// checks that the Design fits on s when rotated and that the previous if
			// statements didn't fit all the Designs
			if (!go && s.fitsWithRotation(d)) {
				d.setFit(true);
				d.setPos(s.getX(), s.getY());
				// horizontal cut (look in the first if statement of loop)
				Sheet s1 = new Sheet(s.getWidth(), s.getHeight() - d.getHeight(), (int) s.getX(),
						(int) s.getY() + d.getHeight());
				// vertical cut (look in the first if statement of loop)
				Sheet s2 = new Sheet(s.getWidth() - d.getWidth(), d.getHeight(), (int) s.getX() + d.getWidth(),
						(int) s.getY());
				// check first if statement of loop
				int origLength = sheets.size();
				addSheet(s1);
				addSheet(s2);
				go = fitDesigns();
				while (sheets.size() > origLength) {
					sheets.removeLast();
				}
			}
			/**
			 * rotated orientation and vertical cut
			 */
			// checks that the design fits on s when rotated and that the previous if
			// statements didn't fit all the Designs
			if (!go && s.fitsWithRotation(d)) {
				d.setFit(true);
				d.setPos(s.getX(), s.getY());
				// vertical cut (look in the first if statement of loop)
				Sheet s1 = new Sheet(s.getWidth() - d.getWidth(), s.getHeight(), (int) s.getX() + d.getWidth(),
						(int) s.getY());
				// horizontal cut (look in the first if statement of loop)
				Sheet s2 = new Sheet(d.getWidth(), s.getHeight() - d.getHeight(), (int) s.getX(),
						(int) s.getY() + d.getHeight());
				// check first if statement of loop
				int origLength = sheets.size();
				addSheet(s1);
				addSheet(s2);
				go = fitDesigns();
				while (sheets.size() > origLength) {
					sheets.removeLast();
				}
			}
			// if the Design was never successfully placed on s, then it's fit is set to
			// false
			if (!go) {
				d.setFit(false);
			}
			// s is added back on to sheets
			sheets.add(index, s);
			index++;
		}
		return go;

	}

	/**
	 * picks the first Design whose fit is false, unless there is another Design
	 * whose height or width is able to fill a Sheet's dimension (either height or
	 * width)
	 * 
	 * @return int: index of the next design to be placed
	 */
	public int pickDesign() {
		Design d;
		int designIndex = 0;
		int firstDesignIndex = designs.size();
		while (designIndex < designs.size()) {
			d = designs.get(designIndex);
			if (!d.getFit()) {
				if (firstDesignIndex > designIndex) {
					// identifies the first Design that hasn't been placed
					firstDesignIndex = designIndex;
				}
				// checks sheets to see if this Design is able to fill a Sheet's dimensions
				int sheetIndex = 0;
				while (sheetIndex < sheets.size()) {
					Sheet s = sheets.get(sheetIndex);
					if (s.fitsNoRotation(d) && (d.getWidth() == s.getWidth() || d.getHeight() == s.getHeight())) {
						return designIndex;
					} else if (s.fitsWithRotation(d)
							&& (d.getWidth() == s.getWidth() || d.getHeight() == s.getHeight())) {
						return designIndex;
					}
					sheetIndex++;
				}
			}
			designIndex++;
		}
		return firstDesignIndex;
	}

	/**
	 * tries placing a Design on any Sheet in sheets, trying the Design normally
	 * with a horizontal cut immediately after, the Design normally with a vertical
	 * cut immediately after, the Design rotated with a horizontal cut immediately
	 * after, and the Design rotated with a vertical cut immediately after
	 * 
	 * Does not use the heuristics of pickDesign(), means that it will not check
	 * whether a Design fills a dimension of a Sheet
	 * 
	 * @return boolean: whether the Design fits, and every Design that is placed
	 *         after it also fits
	 */
	public boolean exhaustiveFitDesigns() {
		if (!areasFit) {
			return false;
		}
		boolean go = false;
		boolean designFound = false;
		int dIndex = 0;
		while (!go && dIndex < designs.size()) {
			Design d = designs.get(dIndex);
			if (!d.getFit()) {
				designFound = true;
				int sIndex = 0;
				while (!go && sIndex < sheets.size()) {
					Sheet s = sheets.remove(sIndex);
					/**
					 * original orientation with a horizontal cut immediately after
					 */
					// checks that the design fits on s in its original orientation
					if (s.fitsNoRotation(d)) {
						d.setFit(true);
						d.setPos(s.getX(), s.getY());
						/**
						 * cuts s horizontally first by creating a new Sheet directly below s with a
						 * width equal to s and a height of s height - design height
						 */
						Sheet s1 = new Sheet(s.getWidth(), s.getHeight() - d.getHeight(), (int) s.getX(),
								(int) s.getY() + d.getHeight());
						/**
						 * cuts s vertically next by creating a new Sheet directly beside s with a
						 * width of s width - design width and a height equal to s
						 */
						Sheet s2 = new Sheet(s.getWidth() - d.getWidth(), d.getHeight(), (int) s.getX() + d.getWidth(),
								(int) s.getY());
						/**
						 * adds the two new Sheets to sheets, calls the "helper method", then once the
						 * "helper method" is done removes either the 0, 1, or 2 Sheets that were added
						 * to sheets depending on their dimensions
						 * (check addSheet() method for more details)
						 */
						int origLength = sheets.size();
						addSheet(s1);
						addSheet(s2);
						go = exhaustiveFitDesigns();
						while (sheets.size() > origLength) {
							sheets.removeLast();
						}
					}
					/**
					 * original orientation and vertical cut
					 */
					// checks that the design fits on s in its original orientation and that the
					// previous if statement didn't fit all the designs
					if (!go && s.fitsNoRotation(d)) {
						d.setFit(true);
						d.setPos(s.getX(), s.getY());
						// vertical cut (look in the first if statement of loop)
						Sheet s1 = new Sheet(s.getWidth() - d.getWidth(), s.getHeight(), (int) s.getX() + d.getWidth(),
								(int) s.getY());
						// horizontal cut (look in the first if statement of loop)
						Sheet s2 = new Sheet(d.getWidth(), s.getHeight() - d.getHeight(), (int) s.getX(),
								(int) s.getY() + d.getHeight());
						// check first if statement of loop
						int origLength = sheets.size();
						addSheet(s1);
						addSheet(s2);
						go = exhaustiveFitDesigns();
						while (sheets.size() > origLength) {
							sheets.removeLast();
						}
					}
					/**
					 * rotated orientation and horizontal cut
					 */
					// checks that the design fits on s when rotated and that the previous if
					// statements didn't fit all the designs
					if (!go && s.fitsWithRotation(d)) {
						d.setFit(true);
						d.setPos(s.getX(), s.getY());
						// horizontal cut (look in the first if statement of loop)
						Sheet s1 = new Sheet(s.getWidth(), s.getHeight() - d.getHeight(), (int) s.getX(),
								(int) s.getY() + d.getHeight());
						// vertical cut (look in the first if statement of loop)
						Sheet s2 = new Sheet(s.getWidth() - d.getWidth(), d.getHeight(), (int) s.getX() + d.getWidth(),
								(int) s.getY());
						// check first if statement of loop
						int origLength = sheets.size();
						addSheet(s1);
						addSheet(s2);
						go = exhaustiveFitDesigns();
						while (sheets.size() > origLength) {
							sheets.removeLast();
						}
					}
					/**
					 * rotated orientation and vertical cut
					 */
					// checks that the design fits on s when rotated and that the previous if
					// statements didn't fit all the designs
					if (!go && s.fitsWithRotation(d)) {
						d.setFit(true);
						d.setPos(s.getX(), s.getY());
						// vertical cut (look in the first if statement of loop)
						Sheet s1 = new Sheet(s.getWidth() - d.getWidth(), s.getHeight(), (int) s.getX() + d.getWidth(),
								(int) s.getY());
						// horizontal cut (look in the first if statement of loop)
						Sheet s2 = new Sheet(d.getWidth(), s.getHeight() - d.getHeight(), (int) s.getX(),
								(int) s.getY() + d.getHeight());
						// check first if statement of loop
						int origLength = sheets.size();
						addSheet(s1);
						addSheet(s2);
						go = exhaustiveFitDesigns();
						while (sheets.size() > origLength) {
							sheets.removeLast();
						}
					}
					// if the design was never successfully placed on s, then it's fit is set to
					// false
					if (!go) {
						d.setFit(false);
					}
					// s is added back on to sheets
					sheets.add(sIndex, s);
					sIndex++;
				}
			}
			dIndex++;
		}
		if (!designFound) {
			return true;
		}
		return go;
	}

	/**
	 * calculates the shortest dimension (width or height) of the Designs in designs
	 * 
	 * @return double: length of the shortest dimension
	 */
	public double calcMinDimension() {
		double min = Integer.MAX_VALUE;
		for (Design d : designs) {
			if (d.getHeight() < min) {
				min = d.getHeight();
			}
			if (d.getWidth() < min) {
				min = d.getWidth();
			}
		}
		if (min == Integer.MAX_VALUE) {
			return 0.0;
		}
		return min;
	}

	/**
	 * finds the Design with the highest demand
	 * 
	 * @return int: the demand of the Design with the highest demand
	 */
	public int getMaxDemand() {
		int max = -1;
		for (Design d : designs) {
			if (d.getDemand() > max) {
				max = d.getDemand();
			}
		}
		return max;
	}
}