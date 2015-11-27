package tower;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private JFrame frame;
	Player p;
	int width, height;
	Room[][] rooms;
	
	public GamePanel(JFrame frame) {
		this.frame = frame;
		p = new Player(1200,900);
		// Does not work yet, not enough rooms
		// rooms = WorldBuilder.buildWorld(width, height);
		rooms = new Room[4][3];
		for(int i = 0; i < rooms.length; i++){
			for(int j = 0; j < rooms[i].length; j++){
				rooms[i][j] = new Room("lighten");
			}
		}
		rooms[1][1] = new Room("level");
		rooms[2][1] = new Room("level2");
		
		
	}
	
	private void update() {
		// TODO
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < rooms.length; i++){
			for(int j = 0; j < rooms[i].length; j++){
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
			p.y -= 5;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			p.x -= 5;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			p.y += 5;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			p.x += 5;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
