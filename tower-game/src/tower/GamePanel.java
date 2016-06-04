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
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	public final int WIDTH = 160;
	public final int HEIGHT = 120;
	public final int S = 5;
	public final int S_WIDTH = S*WIDTH;
	public final int S_HEIGHT = S*HEIGHT;
	private JFrame frame;
	public Player p;
	public int width, height;
	public boolean keyUp, keyDown, keyLeft, keyRight;
	public Room[][] rooms;
	public List<Entity> entities;

	
	public GamePanel(JFrame frame) {
		R.gp = this;
		this.frame = frame;
		rooms = new Room[0][0];
		entities = Collections.synchronizedList(new ArrayList<Entity>());
		p = new Player(1000,1100);
		entities.add(p);
		keyLeft = false;
		keyUp = false;
		keyRight = false;
		keyDown = false;
		width = 3;
		height = 15;
		addMouseMotionListener(new MouseMotionHandler());
	}
	
	public void buildWorld() throws FileNotFoundException {
		// Does not work yet, not enough rooms
		rooms = WorldBuilder.buildWorld(width, height);
	}
	
	private void update() {
		int room_i = (int) (p.x / S_WIDTH);
		int room_j = (int) (p.y / S_HEIGHT);
		p.colliding = false;
		for(int i = room_i-1; i <= room_i+1; i++) {
			for(int j = room_j-1; j <= room_j+1; j++) {
				for (Line2D.Double c : rooms[i][j].getCollisions()) {
					if(Line2D.linesIntersect(p.x, p.y+p.hitBoxHeight/2.0, p.x+0.0, p.y+p.hitBoxHeight/2.0+1.0, c.x1+(double)(S_WIDTH*i), c.y1 + (double)(S_HEIGHT*j), c.x2 + (double)(S_WIDTH*i), c.y2 + (double)(S_HEIGHT*j))) {
						if(Line2D.relativeCCW(c.x1+(double)(S_WIDTH*i), c.y1 + (double)(S_HEIGHT*j), c.x2 + (double)(S_WIDTH*i), c.y2 + (double)(S_HEIGHT*j), p.x, p.y)>0) {
							p.colliding = true;
						}
					}
				}
			}
		}
		double speed = 5;
		p.f = 0.9;
		if(p.colliding && keyUp) {
			p.dy = -13;
		}
		if (keyLeft!=keyRight) {
			p.f = 1.0;
			if(keyLeft && p.dx > -speed) p.dx += -.75;
			if(keyRight && p.dx < speed) p.dx += .75;
		}
		//p.dy += .40;
		/*
		double vx = p.dx;
		double vy = p.dy;
		*/
		for(Entity e : entities) {
			e.applyGravity();
			e.colliding = false;
			ArrayList<Line2D.Double> collisions = e.getConflicts();
			while(!R.isRemoved(e) && !collisions.isEmpty()) {
				for(Line2D.Double c : collisions) {
					e.onCollision(c);
				}
				collisions = e.getConflicts();
			}
			e.x += e.dx;
			e.y += e.dy;
			e.update();
		}
		R.removeEntities();
		/*
		ArrayList<Line2D.Double> collisions = new ArrayList<Line2D.Double>();
		collisions.add(new Line2D.Double(0,0,0,0));
		while (!collisions.isEmpty()) {
			collisions = new ArrayList<Line2D.Double>();
			for(int i = room_i-1; i <= room_i+1; i++) {
				for(int j = room_j-1; j <= room_j+1; j++) {
					for (Line2D.Double c : rooms[i][j].getCollisions()) {
						if(Line2D.linesIntersect(p.x, p.y, p.x+vx, p.y+vy, c.x1+(double)(S_WIDTH*i), c.y1 + (double)(S_HEIGHT*j), c.x2 + (double)(S_WIDTH*i), c.y2 + (double)(S_HEIGHT*j))) {
							if(Line2D.relativeCCW(c.x1+(double)(S_WIDTH*i), c.y1 + (double)(S_HEIGHT*j), c.x2 + (double)(S_WIDTH*i), c.y2 + (double)(S_HEIGHT*j), p.x, p.y)>0) {
								collisions.add(new Line2D.Double(c.x1+(double)(S_WIDTH*i), c.y1 + (double)(S_HEIGHT*j), c.x2 + (double)(S_WIDTH*i), c.y2 + (double)(S_HEIGHT*j)));
								p.colliding = true;
							}
						}
					}
				}
			}
			for(Line2D.Double c : collisions) {
				double dist = 0*c.ptLineDist(p.x, p.y);
				double comp = f*Math.sqrt(vx*vx + vy*vy)*Math.cos(Math.atan2(vy, vx) - Math.atan2(c.y2-c.y1, c.x2-c.x1));
				double len = c.getP1().distance(c.getP2());
				vx = dist*(c.y1-c.y2)/len;
				vy = dist*(c.x2-c.x1)/len;
				vx += comp*(c.x2-c.x1)/len;
				vy += comp*(c.y2-c.y1)/len;
				
			}
		}
		p.dx = vx;
		p.dy = vy;
		*/
		//Projectile.updateList();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int room_i = (int) (p.x / S_WIDTH);
		int room_j = (int) (p.y / S_HEIGHT);
		super.paintComponent(g);
		for(int i = (room_i-1>=0)?room_i-1:0; i <= room_i+1 && i < rooms.length; i++) {
			for(int j = (room_j-1>=0)?room_j-1:0; j <= room_j+1 && j < rooms[i].length; j++) {
				g.drawImage(rooms[i][j].getImage(), (int)(400 + S_WIDTH*i - p.x), (int)(300 + S_HEIGHT*j - p.y), S_WIDTH, S_HEIGHT,this);
			}
		}
		//g.drawRect(390, 280, 30, 70);
		//p.getAHandler().draw(g, this);
		for(Entity e : entities) {
			e.draw(g);
		}
		for(Entity e : entities) {
			g.setColor(Color.GREEN);
			if(e.colliding) g.setColor(Color.RED);
			e.drawHitBox(g);
		}
		//Projectile.drawList(g);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				frame.repaint();
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
		p.onClick();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			keyUp = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			keyLeft = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			keyDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			keyRight = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_1){
			p.setWeaponEquiped(new Fists());
		}
		if(e.getKeyCode() == KeyEvent.VK_2){
			p.setWeaponEquiped(new Gun());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			keyUp = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			keyLeft = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			keyDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			keyRight = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
