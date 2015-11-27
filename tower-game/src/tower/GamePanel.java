package tower;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private JFrame frame;
	Player p;
	int width, height;
	boolean keyUp, keyDown, keyLeft, keyRight;
	Room[][] rooms;
	
	public GamePanel(JFrame frame) {
		this.frame = frame;
		p = new Player(1000,1100);
		// Does not work yet, not enough rooms
		// rooms = WorldBuilder.buildWorld(width, height);
		rooms = new Room[4][3];
		for(int i = 0; i < rooms.length; i++){
			for(int j = 0; j < rooms[i].length; j++){
				rooms[i][j] = new Room("lighten");
			}
		}
		rooms[1][1] = new Room("template");
		rooms[2][1] = new Room("template");
		keyLeft = false;
		keyUp = false;
		keyRight = false;
		keyDown = false;
	}
	
	private void update() {
		p.dy += 0.15;
		p.colliding = false;
		double vx = p.dx;
		double vy = p.dy;
		ArrayList<Line2D.Double> collisions = new ArrayList<Line2D.Double>();
		collisions.add(new Line2D.Double(0,0,0,0));
		while (!collisions.isEmpty()) {
			collisions = new ArrayList<Line2D.Double>();
			for(int i = 0; i < rooms.length; i++){
				for(int j = 0; j < rooms[i].length; j++){
					for (Line2D.Double c : rooms[i][j].getCollisions()) {
						System.out.println("i"+i+"j"+j+":"+c.x1+","+c.y1+","+c.x2+","+c.y2);
						if(Line2D.linesIntersect(p.x, p.y, p.x+vx, p.y+vy, c.x1+(double)(800*i), c.y1 + (double)(600*j), c.x2 + (double)(800*i), c.y2 + (double)(600*j))) {
							collisions.add(c);
							p.colliding = true;
						}
					}
				}
			}
			
		}
		p.dx = vx;
		p.dy = vy;
		p.x += p.dx;
		p.y += p.dy;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < rooms.length; i++) {
			for(int j = 0; j < rooms[i].length; j++) {
				g.drawImage(rooms[i][j].getImage(), (int)(400 + 800*i - p.x), (int)(300 + 600*j - p.y), 800, 600,this);
			}
		}
		g.drawRect(380, 280, 40, 40);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				update();
				frame.repaint();
				Thread.sleep(17);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP){
			keyUp = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			keyLeft = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			keyDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			keyRight = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP){
			keyUp = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			keyLeft = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			keyDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			keyRight = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
