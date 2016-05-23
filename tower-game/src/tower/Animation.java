package tower;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.List;

public class Animation implements Drawable {
	private List<Image> frames;		// The list of images to cycle through
	private int frameNumber;		// The current image index to be displayed
	private double screenX;			// The x location where the center of the image should be
	private double screenY;			// The y location where the center of the image should be
	private double centerX;			// The x location on the image which is the center
	private double centerY;			// The y location on the image which is the center
	private double theta;			// The angle of rotation around the center to use when drawing the image
	private double delayTime;		// The amount of time to delay between image changes
	private double lastChangeTime;	// The time when the image was last changed
	
	protected Animation() {
		this.frames = null;
		this.frameNumber = 0;
		this.lastChangeTime = System.currentTimeMillis();
		setLocation(0.0,0.0);
		setCenter(0.0,0.0);
		setRotation(0.0);
		setDelayTime(500.0);
	}
	
	protected void setFrames(List<Image> frames) {
		this.frames = frames;
	}
	
	/**
	 * @param frames The list of images which will be cycled through
	 * @effects creates a new animation object, initializing all variables.
	 */
	public Animation(List<Image> frames) {
		this.frames = frames;
		this.frameNumber = 0;
		this.screenX = 0.0;
		this.screenY = 0.0;
		this.centerX = 0.0;
		this.centerY = 0.0;
		this.theta = 0.0;
		this.delayTime = 500.0;
		this.lastChangeTime = System.currentTimeMillis();
	}
	
	/**
	 * @effects will change frameNumber to reflect the correct value
	 */
	protected void update() {
		if (delayTime <= 0.01) return;
		while (lastChangeTime + delayTime < System.currentTimeMillis()) {
			lastChangeTime += delayTime;
			++frameNumber;
		}
		frameNumber %= frames.size();
	}
	
	/**
	 * @param trans AffineTransform to be effected
	 * @effects will apply rotation and translation transforms to trans
	 */
	protected void buildTransform(AffineTransform trans) {
		trans.translate(screenX,screenY);
		trans.rotate(theta);
		trans.translate(-centerX,-centerY);
	}
	
	/**
	 * @param g Graphics to draw the animation to
	 * @effects will draw the correct image to the screen in the correct location
	 */
	@Override
	public void draw(Graphics g) {
		update();
		Image image = frames.get(frameNumber);
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform trans = new AffineTransform();
		trans.setTransform(R.identity);
		buildTransform(trans);
		g2d.drawImage(image, trans, R.gp);
	}
	
	public void setLocation(double screenX, double screenY) {
		this.screenX = screenX;
		this.screenY = screenY;
	}
	
	public void setCenter(double centerX, double centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}
	
	public void setRotation(double theta) {
		this.theta = theta;
	}
	
	public void setDelayTime(double delayTime) {
		this.delayTime = delayTime;
	}
}