package tower;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TowerGame {
	private static JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
	}
	public static void main(String[] args) throws IOException {
		R.init();
		JFrame frame = buildFrame();
		GamePanel gp = new GamePanel(frame);
		gp.addMouseListener(gp);
		frame.addKeyListener(gp);
		frame.getContentPane().setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.add(gp);
        Thread t = new Thread(gp);
        gp.buildWorld();
        Point location = frame.getLocation();
		frame.setLocation(new Point(0, 0));
		frame.setLocation(location);
		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		t.start();
	}
}
