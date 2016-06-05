package tower;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {
	public final boolean SHOW_HITBOXES = false;
	public final int WIDTH = 160;
	public final int HEIGHT = 120;
	public final int S = 5;
	public final int S_WIDTH = S*WIDTH;
	public final int S_HEIGHT = S*HEIGHT;
	private JFrame frame;
	public Player p;
	public int width, height;
	public Room[][] rooms;
	public List<Entity> entities;

	
	public GamePanel(JFrame frame) {
		R.gp = this;
		this.frame = frame;
		rooms = new Room[0][0];
		entities = Collections.synchronizedList(new ArrayList<Entity>());
		p = new Player(1000,1100);
		entities.add(p);
		width = 3;
		height = 15;
		addMouseMotionListener(new MouseMotionHandler());
	}
	
	public void buildWorld() throws FileNotFoundException {
		rooms = WorldBuilder.buildWorld(width, height);
	}
	
	private void update() {
		// Update all entities
		synchronized(entities) {
		for(Entity e : entities) {
			e.update();
		}}
		
		// Loop over entities and figure out their movements
		synchronized(entities) {
		for(Entity e : entities) {
			e.applyGravity();
			e.colliding = false;
			List<Line2D.Double> collisions = e.getConflicts();
			int count = 0; int max = 100;
			while(count < max && !R.isRemoved(e) && !collisions.isEmpty()) {
				for(Line2D.Double c : collisions) {
					e.onCollision(c);
				}
				collisions = e.getConflicts();
				count++;
			}
			if (count == max) {e.dx = 0; e.dy = 0;}
			e.x += e.dx; e.y += e.dy;
		}}
		
		// Loop over entities and determine if they are overlapping with other entities
		synchronized(entities) {
		for(Entity e : entities) {
			for(Entity o : entities) {
				if(		e.x+e.hitBoxWidth/2.0  > o.x-o.hitBoxWidth /2.0 &&
						e.x-e.hitBoxWidth/2.0  < o.x+o.hitBoxWidth /2.0 &&
						e.y+e.hitBoxHeight/2.0 > o.y-o.hitBoxHeight/2.0 &&
						e.y-e.hitBoxHeight/2.0 < o.y+o.hitBoxHeight/2.0		) {
					if(e != o) e.interactWith(o);
				}
			}
		}}
		
		// Actually remove the ones which were told to be removed
		R.removeEntities();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int room_i = (int) (p.x / S_WIDTH);
		int room_j = (int) (p.y / S_HEIGHT);
		super.paintComponent(g);
		for(int i = (room_i-1>=0)?room_i-1:0; i <= room_i+1 && i < rooms.length; i++) {
			for(int j = (room_j-1>=0)?room_j-1:0; j <= room_j+1 && j < rooms[i].length; j++) {
				g.drawImage(rooms[i][j].getImage(), (int)(400 + S_WIDTH*i - p.x),
							(int)(300 + S_HEIGHT*j - p.y), S_WIDTH, S_HEIGHT,this);
			}
		}
		for(Entity e : entities) {
			e.draw(g);
			if(SHOW_HITBOXES) e.drawHitBox(g);
		}
	}

	@Override
	public void run() {
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
	public void mouseReleased(MouseEvent e) {
		synchronized(entities) {p.onClick();}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		R.pressedKeys.add(e.getKeyCode());
		if(e.getKeyCode() == KeyEvent.VK_1){
			synchronized(entities) {p.setWeaponEquiped(new Fists());}
		}
		if(e.getKeyCode() == KeyEvent.VK_2){
			synchronized(entities) {p.setWeaponEquiped(new Gun());}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		R.pressedKeys.remove(e.getKeyCode());
	}

	// NEEDED TO HAVE // DO NOTHING
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
