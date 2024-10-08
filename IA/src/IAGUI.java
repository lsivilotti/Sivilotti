import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class IAGUI extends Canvas {
	private static final long serialVersionUID = 1L;
	// to set the size of the JFrame
	private Dimension screenSize;
	// relates the string selected in the drop down to a certain size sheet
	private Map<String, Sheet> sheetMap;
	// LinkedList of the deigns
	private LinkedList<Design> d;
	// LinkedList of the colors of each design so colors remain the same each time
	// the button is pressed
	private LinkedList<Color> cols;
	// cell renderer for the JTable
	private DefaultTableCellRenderer renderer;
	// number of designs that have been added to the table - only increases
	private int designNum = 0;

	// frame of the entire GUI
	private JFrame frame;

	// JPanel that contains everything (JTable, buttons, etc.)
	private JPanel xUp;
	// JPanel that contains the layout of designs
	private JPanel layout;

	// table where the user inputs the dimensions of the designs
	private JTable designTable;

	// scroll pane for the table, so user can enter more designs and scroll through
	// them
	private JScrollPane tableScroll;

	// button that lets the user add a design
	private JButton addDesign;
	// button that removes the design of a given ID
	private JButton removeDesignAt;
	// button that places the designs in the table (heuristically)
	private JButton place;
	// button that places the designs in the table (exhaustively)
	private JButton exhaustiveFit;

	// title of panel
	private JLabel panelLabel;
	// labels sheet size drop down
	private JLabel sheetSizeLabel;
	// labels custom sheet height text field
	private JLabel heightLabel;
	// labels custom sheet width text field
	private JLabel widthLabel;
	// labels design layout
	private JLabel layoutLabel;
	// labels the table
	private JLabel tableLabel;
	// blank space between title and rest of panel content
	private JLabel headerSpacer;
	// blank space between left edge of panel and rest of panel content
	private JLabel lineStartSpacer;
	// blank space in the middle of panel, dividing panel content in half
	private JLabel centerSpacer;
	// blank space between right edge of panel and rest of panel content
	private JLabel lineEndSpacer;
	// blank space between bottom of panel and rest of panel content
	private JLabel footerSpacer;

	// input for custom sheet height
	private JTextField sheetHeight;
	// input for custom sheet width
	private JTextField sheetWidth;
	// input of design ID to be removed
	private JTextField removingDesignID;

	// drop down of sheet sizes
	private JComboBox<String> sheetDimensions;

	// output of all information
	private JTextArea output;

	/**
	 * Constructor of IAGUI
	 * 
	 * @param f JFrame that contains the entire GUI
	 */
	public IAGUI(JFrame f) {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		sheetMap = new TreeMap<String, Sheet>();
		Sheet s1 = new Sheet(19, 13);
		sheetMap.put(s1.toString(), s1);
		Sheet s2 = new Sheet(18, 12);
		sheetMap.put(s2.toString(), s2);
		Sheet s3 = new Sheet(40, 28);
		sheetMap.put(s3.toString(), s3);
		Sheet s4 = new Sheet(45, 31);
		sheetMap.put(s4.toString(), s4);
		frame = f;
		frame.setMinimumSize(new Dimension(792, 566));
		frame.setSize(screenSize);
	}

	/**
	 * set up and appearance of panel that the user interacts with
	 * 
	 * @param frame JFrame that contains the GUI
	 */
	public void mainPage(JFrame frame) {
		// the way that components are placed on the panel
		GridBagConstraints c = new GridBagConstraints();
		// variable meaning that component is aligned to the center in the cell
		int center = GridBagConstraints.CENTER;
		// variable meaning that component is aligned to the left in the cell
		int left = GridBagConstraints.LINE_START;
		// variable meaning that component is aligned to the right in the cell
		int right = GridBagConstraints.LINE_END;

		xUp = new JPanel(new GridBagLayout());
		xUp.setVisible(true);
		frame.setContentPane(xUp);
		c.weightx = 0.0;
		c.weighty = 0.0;

		panelLabel = new JLabel("X Up 2.0");
		panelLabel.setFont(new Font("Cambria", Font.BOLD, 30));
		c.gridx = 0;
		c.gridwidth = 9;
		c.gridy = 0;
		c.gridheight = 1;
		c.ipady = 50;
		c.anchor = center;
		xUp.add(panelLabel, c);

		headerSpacer = new JLabel();
		headerSpacer.setVisible(true);
		c.gridy = 1;
		c.ipady = 10;
		xUp.add(headerSpacer, c);
		c.gridheight = 1;
		c.gridwidth = 1;
		c.ipady = 0;
		c.ipadx = 0;

		lineStartSpacer = new JLabel();
		lineStartSpacer.setVisible(true);
		c.gridy = 2;
		c.gridheight = 8;
		c.ipadx = 30;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = .1;
		c.weightx = 1;
		xUp.add(lineStartSpacer, c);
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.ipadx = 0;

		sheetSizeLabel = new JLabel("Sheet Dimensions (height, width):");
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = right;
		c.weightx = .1;
		sheetSizeLabel.setVisible(true);
		xUp.add(sheetSizeLabel, c);

		sheetDimensions = new JComboBox<String>();
		sheetDimensions.addItem("Select");
		sheetDimensions.addItem(new Sheet(18, 12).toString());
		sheetDimensions.addItem(new Sheet(19, 13).toString());
		sheetDimensions.addItem(new Sheet(40, 28).toString());
		sheetDimensions.addItem(new Sheet(45, 31).toString());
		sheetDimensions.addItem("Custom");
		c.gridx = 2;
		c.gridy = 2;
		c.anchor = left;
		sheetDimensions.setVisible(true);
		xUp.add(sheetDimensions, c);

		heightLabel = new JLabel("Custom height:");
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = right;
		heightLabel.setVisible(true);
		xUp.add(heightLabel, c);

		sheetHeight = new JTextField("Height");
		sheetHeight.setForeground(Color.GRAY);
		c.gridx = 2;
		c.gridy = 3;
		c.ipadx = 50;
		c.anchor = left;
		c.insets = new Insets(0, 0, 0, 0);
		sheetHeight.setVisible(true);
		xUp.add(sheetHeight, c);
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 0;

		widthLabel = new JLabel("Custom width:");
		c.gridx = 1;
		c.gridy = 4;
		c.anchor = right;
		c.insets = new Insets(0, 0, 0, 0);
		c.fill = GridBagConstraints.NONE;
		widthLabel.setVisible(true);
		xUp.add(widthLabel, c);

		sheetWidth = new JTextField("Width");
		sheetWidth.setForeground(Color.GRAY);
		c.gridx = 2;
		c.gridy = 4;
		c.ipadx = 50;
		c.anchor = left;
		c.insets = new Insets(0, 0, 0, 0);
		sheetWidth.setVisible(true);
		xUp.add(sheetWidth, c);
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 0;

		tableLabel = new JLabel("Designs");
		tableLabel.setFont(new Font("Cambria", Font.BOLD, 15));
		tableLabel.setVisible(true);
		c.gridx = 1;
		c.gridwidth = 3;
		c.gridy = 5;
		c.anchor = center;
		xUp.add(tableLabel, c);

		String[] tableHeads = { "Design ID", "Height", "Width", "Demand" };
		String[][] data = { { "ID" + designNum++, "11", "8", "1" },
				{ "ID" + designNum++, "7", "7", "1" },
				{ "ID" + designNum++, "5", "7", "1" },
				{ "ID" + designNum++, "12", "1", "1" },
				{ "ID" + designNum++, "11", "3", "1" },
				{ "ID" + designNum++, "19", "1", "1" } };
		designTable = new JTable();
		designTable.setVisible(true);
		renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		cols = new LinkedList<Color>();
		for (int i = 0; i < 6; i++) {
			Color col = new Color((int) (Math.random() * 256), (int) (Math.random() * 256),
					(int) (Math.random() * 256));
			cols.add(col);
		}
		DefaultTableModel tableModel = new DefaultTableModel(data, tableHeads);
		designTable.setModel(tableModel);
		for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
			designTable.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
		}
		renderer = (DefaultTableCellRenderer) designTable.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		designTable.setRowMargin(0);

		tableScroll = new JScrollPane(designTable);
		c.gridx = 1;
		c.gridwidth = 3;
		c.gridy = 6;
		c.ipady = 100;
		c.anchor = center;
		c.weightx = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.BOTH;
		tableScroll.setVisible(true);
		xUp.add(tableScroll, c);

		addDesign = new JButton("Add Design");
		addDesign.setVisible(true);
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridy = 7;
		c.ipady = 0;
		c.fill = GridBagConstraints.NONE;
		c.weightx = .2;
		c.weighty = .5;
		xUp.add(addDesign, c);

		removeDesignAt = new JButton("Remove Design By ID");
		removeDesignAt.setVisible(true);
		c.gridx = 2;
		c.anchor = right;
		xUp.add(removeDesignAt, c);

		removingDesignID = new JTextField("ID");
		removingDesignID.setForeground(Color.GRAY);
		removingDesignID.setVisible(true);
		c.gridx = 3;
		c.ipadx = 50;
		c.anchor = left;
		xUp.add(removingDesignID, c);

		place = new JButton("Fit Designs");
		place.setVisible(true);
		c.gridy = 8;
		c.ipady = 50;
		c.gridx = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		xUp.add(place, c);

		exhaustiveFit = new JButton("Fit Exhaustively");
		exhaustiveFit.setVisible(true);
		c.gridy = 9;
		xUp.add(exhaustiveFit, c);

		centerSpacer = new JLabel();
		centerSpacer.setVisible(true);
		c.gridy = 2;
		c.gridx = 5;
		c.ipadx = 30;
		c.gridheight = 8;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.ipadx = 30;
		xUp.add(centerSpacer, c);
		c.weightx = 1;
		c.weighty = 1;

		output = new JTextArea("Result");
		output.setEditable(false);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		output.setMargin(new Insets(5, 5, 5, 5));
		output.setFont(new Font("Cambria", Font.PLAIN, 15));
		output.setVisible(true);
		c.gridx = 6;
		c.gridy = 2;
		c.gridheight = 3;
		c.ipady = 0;
		c.ipadx = 0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		xUp.add(output, c);

		layoutLabel = new JLabel("Design layout");
		layoutLabel.setFont(new Font("Cambria", Font.BOLD, 15));
		layoutLabel.setVisible(true);
		c.gridy = 5;
		c.gridheight = 1;
		c.anchor = center;
		c.fill = GridBagConstraints.NONE;
		xUp.add(layoutLabel, c);

		layout = new JPanel();
		layout.setBackground(Color.WHITE);
		layout.setVisible(true);
		c.gridy = 6;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 4;
		c.gridwidth = 2;
		c.weightx = 0;
		c.weighty = 0;
		int padding = frame.getWidth() / 4;
		c.ipadx = padding;
		xUp.add(layout, c);

		lineEndSpacer = new JLabel();
		lineEndSpacer.setVisible(true);
		c.gridx = 8;
		c.gridy = 2;
		c.ipadx = 30;
		c.gridwidth = 1;
		c.gridheight = 8;
		c.weightx = 1;
		c.weighty = 1;
		xUp.add(lineEndSpacer, c);

		footerSpacer = new JLabel();
		footerSpacer.setVisible(true);
		c.gridx = 1;
		c.gridheight = 8;
		c.gridy = 10;
		c.gridheight = 1;
		c.gridwidth = 9;
		c.ipady = 30;
		c.ipadx = 0;
		xUp.add(footerSpacer, c);

		// action listener to place designs
		place.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String dimension = (String) sheetDimensions.getSelectedItem();
				switch (dimension) {
					case "Select":
						output.setText("Select a sheet size");
						break;
					case "Custom":
						try {
							int width = Integer.parseInt(sheetWidth.getText());
							int height = Integer.parseInt(sheetHeight.getText());
							runner(height > width ? new Sheet(height, width) : new Sheet(width, height));
						} catch (NumberFormatException n) {
							output.setText("Please enter valid sheet dimensions");
						}
						break;
					default:
						runner(sheetMap.get((String) sheetDimensions.getSelectedItem()));
				}

			}

		});

		// action listener to place the designs exhaustively
		exhaustiveFit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String dimension = (String) sheetDimensions.getSelectedItem();
				switch (dimension) {
					case "Select":
						output.setText("Select a sheet size");
						break;
					case "Custom":
						try {
							int width = Integer.parseInt(sheetWidth.getText());
							int height = Integer.parseInt(sheetHeight.getText());
							eRunner(height > width ? new Sheet(height, width) : new Sheet(width, height));
						} catch (NumberFormatException n) {
							output.setText("Please enter valid sheet dimensions");
						}
						break;
					default:
						eRunner(sheetMap.get((String) sheetDimensions.getSelectedItem()));
				}
			}

		});

		// action listener to add a design
		addDesign.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.addRow(new Object[] { "ID" + designNum++, "0", "0", "1" });
				Color c = new Color((int) (Math.random() * 256), (int) (Math.random() * 256),
						(int) (Math.random() * 256));
				cols.add(c);
			}

		});

		// action listener to remove a design
		removeDesignAt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = removingDesignID.getText();
				int row = 0;
				while (row < designTable.getRowCount() && !id.equals(designTable.getValueAt(row, 0))) {
					row++;
				}
				if (row < designTable.getRowCount()) {
					tableModel.removeRow(row);
					cols.remove(row);
				}
			}

		});

		// focus listener to make the ID input look pretty
		removingDesignID.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (removingDesignID.getText().equals("ID")) {
					removingDesignID.setText("");
					removingDesignID.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (removingDesignID.getText().equals("")) {
					removingDesignID.setText("ID");
					removingDesignID.setForeground(Color.GRAY);
				}
			}

		});

		// focus listener to make the sheet height input look pretty
		sheetHeight.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (sheetHeight.getText().equals("Height")) {
					sheetHeight.setText("");
					sheetHeight.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (sheetHeight.getText().equals("")) {
					sheetHeight.setText("Height");
					sheetHeight.setForeground(Color.GRAY);
				}
			}

		});

		// focus listener to make the sheet width input look pretty
		sheetWidth.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (sheetWidth.getText().equals("Width")) {
					sheetWidth.setText("");
					sheetWidth.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (sheetWidth.getText().equals("")) {
					sheetWidth.setText("Width");
					sheetWidth.setForeground(Color.GRAY);
				}
			}

		});

		frame.setVisible(true);
		frame.pack();
	}

	/**
	 * connects GUI to PlaceDesigns and runs the heuristic placer
	 * 
	 * @param s Sheet of dimensions that the user specified
	 */
	public void runner(Sheet s) {
		PlaceDesigns pd = new PlaceDesigns(s);
		d = new LinkedList<Design>();
		clearSheet(s);
		String error = "";
		boolean designsPlaced = true;
		try {
			for (int i = 0; i < designTable.getRowCount(); i++) {
				// adds design to d
				d.add(new Design(Integer.parseInt((String) designTable.getValueAt(i, 1)),
						Integer.parseInt((String) designTable.getValueAt(i, 2)),
						Integer.parseInt((String) designTable.getValueAt(i, 3)),
						(String) designTable.getValueAt(i, 0)));
				// assigns design at i the color at i
				d.get(i).setColor(cols.get(i));
				pd.addDesign(d.get(i));
			}
		} catch (NumberFormatException e) {
			error = error + "Enter valid design information";
			output.setText(error);
			designsPlaced = false;
		}
		boolean allFit = pd.fitDesigns();
		if (designsPlaced) {
			if (allFit) {
				output.setText("Result:\nAll fit!\nSheets required: " + pd.getMaxDemand());
				layout(s);
			} else {
				output.setText(
						"Result:\nDesigns do not fit. To fit the designs on the sheet some human intervention may be required, or you can try to exhaustively place the designs using the 'Fit Exhaustively' button.");
				exhaustiveFit.setVisible(true);
			}
		}
	}

	/**
	 * connects GUI to PlaceDesigns and runs exhaustive placer
	 * 
	 * @param s Sheet of dimensions that user specified
	 */
	public void eRunner(Sheet s) {
		PlaceDesigns pd = new PlaceDesigns(s);
		d = new LinkedList<Design>();
		clearSheet(s);
		String error = "";
		boolean designsPlaced = true;
		try {
			for (int i = 0; i < designTable.getRowCount(); i++) {
				// adds design to d
				d.add(new Design(Integer.parseInt((String) designTable.getValueAt(i, 1)),
						Integer.parseInt((String) designTable.getValueAt(i, 2)),
						Integer.parseInt((String) designTable.getValueAt(i, 3)),
						(String) designTable.getValueAt(i, 0)));
				// assigns design at i the color at i
				d.get(i).setColor(cols.get(i));
				pd.addDesign(d.get(i));
			}
		} catch (NumberFormatException e) {
			error = error + "Enter valid design information";
			output.setText(error);
			designsPlaced = false;
		}
		boolean allFit = pd.exhaustiveFitDesigns();
		if (designsPlaced) {
			if (allFit) {
				output.setText("Result:\nAll fit!\nSheets required: " + pd.getMaxDemand());
				layout(s);
			} else {
				output.setText(
						"Result:\nDesigns do not fit. To fit the designs on the sheet some human intervention may be required.");
				exhaustiveFit.setVisible(true);
			}
		}
	}

	/**
	 * creates a layout of the designs with each design having a random color and
	 * labeled by ID their top left corner
	 * 
	 * @param s Sheet of dimensions that user specified
	 */
	public void layout(Sheet s) {
		// used to proportionally scale designs and sheet up to fit in layout
		double pxRatio;
		int sideResult = compareSideLengths(s);
		switch (sideResult) {
			case 0:
				// since sheet more square, want to maximize the height of the sheet on layout
				pxRatio = (layout.getHeight() / s.getHeight());
				break;
			default:
				// since sheet less or same squareness, want to maximize the width of the sheet
				// on layout
				pxRatio = (layout.getWidth() / s.getWidth());
		}
		Graphics g = layout.getGraphics();
		// places each design on layout
		for (Design design : d) {
			// x position of the design (proportional to layout dimensions)
			int x = (int) (design.getPos().getX() * pxRatio);
			// y position of the design (proportional to layout dimensions)
			int y = (int) (design.getPos().getY() * pxRatio);
			// width of the design (proportional to layout dimensions)
			int width = (int) (design.getWidth() * pxRatio);
			// length of the design (proportional to layout dimensions)
			int length = (int) (design.getHeight() * pxRatio);
			g.setColor(design.getColor());
			g.fillRect(x, y, width, length);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Cambria", Font.PLAIN, 7));
			g.drawString(design.getID(), x, y + 7);
		}
	}

	/**
	 * compares the squareness of the layout and the sheet
	 * 
	 * @param s Sheet of dimensions that user specified
	 * @return int: whether the sheet is more square than the JPanel layout (0 ==
	 *         yes, 1 == no)
	 */
	public int compareSideLengths(Sheet s) {
		double layoutRatio = layout.getWidth() / (double) layout.getHeight();
		double sheetRatio = s.getWidth() / s.getHeight();
		// this means that the sheet's proportions are more square than layout
		// (guaranteed that sheetWidth > sheetHeight)
		if (layoutRatio > sheetRatio) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * places a rectangle the same size as the JPanel layout and color as the xUp
	 * JPanel over the existing sheet, making the previous Sheet disappear, then
	 * creates a white rectangle and border the same proportions as the new Sheet
	 * over the "emptied" JPanel
	 * 
	 * @param s Sheet of dimensions that user specified
	 */
	public void clearSheet(Sheet s) {
		// used to proportionally scale designs and sheet up to fit in layout
		double pxRatio;
		int sideResult = compareSideLengths(s);
		switch (sideResult) {
			case 0:
				// since sheet more square, want to maximize the height of the sheet on layout
				pxRatio = (layout.getHeight() / s.getHeight());
				break;
			default:
				// since sheet less or same squareness, want to maximize the width of the sheet
				// on layout
				pxRatio = (layout.getWidth() / s.getWidth());
		}
		Graphics g = layout.getGraphics();
		// "clears" the rectangles off of the layout
		g.setColor(xUp.getBackground());
		g.fillRect(0, 0, layout.getWidth(), layout.getHeight());
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, (int) (s.getWidth() * pxRatio), (int) (s.getHeight() * pxRatio));
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, (int) (s.getWidth() * pxRatio) - 1, (int) (s.getHeight() * pxRatio) - 1);
	}
}
