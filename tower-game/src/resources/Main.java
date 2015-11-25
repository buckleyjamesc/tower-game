package resources;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Main {
	static String fileName = "level2";
	static int mx = 400;
	static int my = 300;
	static int tempx = 0;
	static int tempy = 0;
	static boolean drawing = false;
	static Image level;
	static Image lighten;
	static ArrayList<Line2D> collisions = new ArrayList<Line2D>();
	private static JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }
	public static void main(String[] args) throws IOException {
		try {
			level = ImageIO.read(new File("src/resources/" + fileName + ".png"));
	        lighten = ImageIO.read(new File("src/resources/lighten.png"));
		 } catch(Exception e) {
	          e.printStackTrace();
	     }	
        Main m = new Main();
		JFrame frame = buildFrame();
		JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(level, 0, 0, 800, 600, this);
        		g.drawImage(lighten, 0, 0, 800, 600, this);
        		
        		g.setColor(Color.BLACK);
        		for(Line2D current : collisions){
        			g.drawLine((int)current.getX1(), (int)current.getY1(), (int)current.getX2(), (int)current.getY2());
        		}
        		
        		g.setColor(Color.RED);
        		if(drawing) {
        			g.drawLine(tempx, tempy, mx, my);
        		}
            }
        };
		pane.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				mx  = arg0.getX();
				my  = arg0.getY();
				frame.repaint();
			}
			@Override
			public void mouseMoved(MouseEvent arg0) {
				mx  = arg0.getX();
				my  = arg0.getY();
				frame.repaint();
			}
		});
		pane.addMouseListener( new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(drawing){
					collisions.add(new Line2D.Double(tempx, tempy, mx, my));
				}
				else{
					tempx = mx;
					tempy = my;
				}
				drawing  = !drawing;
				frame.repaint();
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		frame.addKeyListener( new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_DELETE){
					deleteLine();
					
				} else if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					writeFile();
				}
				frame.repaint();
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		frame.getContentPane().setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.add(pane);
	}
	public static void writeFile() {
		String fileString = "";
		for(Line2D current : collisions) {
			fileString += "" + current.getX1() + "," + current.getY1() + "," + current.getX2() + "," + current.getY2() + "\n";
		}
		System.out.println(fileString);
		 try {
			 FileWriter pw = new FileWriter("src/resources/" + fileName + ".collisions");
			 pw.write(fileString);
			 pw.flush();
			 pw.close();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	     System.out.println("Written to file");
	}
	public static void deleteLine() {
		if(drawing){
			drawing = false;
		}
		else{
			if(collisions.size() > 0) collisions.remove(collisions.size() - 1);
		}
	}
}
