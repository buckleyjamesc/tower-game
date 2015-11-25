import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Main extends Applet implements Runnable {
	private Image i;
	private Graphics doubleG; 
	
	

	public void init() {
		setSize(800,600);
		
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.start();
		
	}
	

	public void stop() {
		
		
	}
	
	public void destroy() {
		
	}

	public void update(Graphics g) {
		if(i == null){
			i = createImage(this.getSize().width, this.getSize().height);
			doubleG = i.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		doubleG.setColor(getForeground());
		paint(doubleG);
		
		g.drawImage(i, 0, 0, this);
	}
	
	
	
	

	
	@Override
	public void paint(Graphics g) {
		
	}
	
	public void run() {
		while(true){
			repaint();
			
		try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
