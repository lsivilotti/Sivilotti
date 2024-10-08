import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class IARunner {
	public static void main(String[] args) {
		JFrame frame = new JFrame("xUp v2");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setPreferredSize(screenSize);
		frame.setVisible(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		IAGUI gui = new IAGUI(frame);
		gui.mainPage(frame);
	}
}