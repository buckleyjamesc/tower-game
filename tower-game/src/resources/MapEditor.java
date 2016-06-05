package resources;

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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MapEditor {
	static final int DOOR_W = 60;
	static final int DOOR_H = 100;
	static String fileName = "template1111";
	static int mx = 400;
	static int my = 300;
	static int tempx = 0;
	static int tempy = 0;
	static boolean drawing = false;
	static Image level;
	static Image lighten;
	static boolean leftOpen = true;
	static boolean rightOpen = true;
	static boolean upOpen = true;
	static boolean downOpen = true;
	static ArrayList<Line2D> collisions = new ArrayList<Line2D>();
	static ArrayList<Line2D> walls = new ArrayList<Line2D>();
	private static JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
	}
	@SuppressWarnings("serial")
	public static void main(String[] args) throws IOException {
		try {
			level = ImageIO.read(new File("src/resources/" + fileName + ".png"));
			lighten = ImageIO.read(new File("src/resources/lighten.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		remakeWalls();
		JFrame frame = buildFrame();
		JPanel pane = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(level, 0, 0, 800, 600, this);
        		g.drawImage(lighten, 0, 0, 800, 600, this);
        		g.setColor(Color.BLACK);
        		for(Line2D current : walls){
        			g.drawLine((int)current.getX1(), (int)current.getY1(), (int)current.getX2(), (int)current.getY2());
        		}
        		for(Line2D current : collisions){
        			g.drawLine((int)current.getX1(), (int)current.getY1(), (int)current.getX2(), (int)current.getY2());
        		}
        		g.setColor(Color.RED);
        			g.fillRect(0, 570-DOOR_H, 5, DOOR_H);
        			g.fillRect(795, 570-DOOR_H, 5, DOOR_H);
        			g.fillRect(400-DOOR_W/2, 0, DOOR_W, 5);
        			g.fillRect(400-DOOR_W/2, 595, DOOR_W, 5);
        		if(drawing) {
        			g.drawLine(tempx, tempy, mx, my);
        		}
        		g.setColor(Color.GREEN);
    			if(leftOpen) g.fillRect(0, 570-DOOR_H, 5, DOOR_H);
    			if(rightOpen) g.fillRect(795, 570-DOOR_H, 5, DOOR_H);
    			if(upOpen) g.fillRect(400-DOOR_W/2, 0, DOOR_W, 5);
    			if(downOpen) g.fillRect(400-DOOR_W/2, 595, DOOR_W, 5);
            }
        };
		pane.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent arg0) {
				mx  = arg0.getX();
				my  = arg0.getY();
				frame.repaint();
			}
			public void mouseMoved(MouseEvent arg0) {
				mx  = arg0.getX();
				my  = arg0.getY();
				frame.repaint();
			}
		});
		pane.addMouseListener( new MouseListener() {
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
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});
		frame.addKeyListener( new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_DELETE){
					deleteLine();
					
				} else if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					writeFile();
				} else if(arg0.getKeyCode() == KeyEvent.VK_LEFT){
					leftOpen = !leftOpen;
				} else if(arg0.getKeyCode() == KeyEvent.VK_RIGHT){
					rightOpen = !rightOpen;
				} else if(arg0.getKeyCode() == KeyEvent.VK_UP){
					upOpen = !upOpen;
				} else if(arg0.getKeyCode() == KeyEvent.VK_DOWN){
					downOpen = !downOpen;
				}
				
				remakeWalls();
				
				frame.repaint();
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}
		});
		frame.getContentPane().setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.add(pane);
	}
	public static void remakeWalls() {
		walls = new ArrayList<Line2D>();
		if(downOpen) {
			walls.add(new Line2D.Double(leftOpen?0:30,570,400-DOOR_W/2,570));
			walls.add(new Line2D.Double(400-DOOR_W/2,570,400-DOOR_W/2,600));
			walls.add(new Line2D.Double(400+DOOR_W/2,600,400+DOOR_W/2,570));
			walls.add(new Line2D.Double(400+DOOR_W/2,570,rightOpen?800:770,570));
		} else {
			walls.add(new Line2D.Double(leftOpen?0:30,570,rightOpen?800:770,570));
		}
		if(rightOpen) {
			walls.add(new Line2D.Double(800,570-DOOR_H,770,570-DOOR_H));
		}
		walls.add(new Line2D.Double(770,rightOpen?570-DOOR_H:570,770,30));
		if(upOpen) {
			walls.add(new Line2D.Double(770,30,400+DOOR_W/2,30));
			walls.add(new Line2D.Double(400+DOOR_W/2,30,400+DOOR_W/2,0));
			walls.add(new Line2D.Double(400-DOOR_W/2,0,400-DOOR_W/2,30));
			walls.add(new Line2D.Double(400-DOOR_W/2,30,30,30));
		} else {
			walls.add(new Line2D.Double(770,30,30,30));
		}
		walls.add(new Line2D.Double(30,30,30,leftOpen?570-DOOR_H:570));
		if(leftOpen) {
			walls.add(new Line2D.Double(30,570-DOOR_H,0,570-DOOR_H));
		}
	}
	public static void writeFile() {
		String fileString = "";
		//for(Line2D current : walls) {
			//fileString += "" + current.getX1() + "," + current.getY1() + "," + current.getX2() + "," + current.getY2() + "\n";
		//}
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
