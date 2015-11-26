package tower;

import java.awt.Dimension;
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
		JFrame frame = buildFrame();
		GamePanel gp = new GamePanel(frame);
		gp.addMouseMotionListener(gp);
		gp.addMouseListener(gp);
		frame.addKeyListener(gp);
		frame.getContentPane().setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.add(gp);
        Thread t = new Thread(gp);
        t.start();
	}
}
