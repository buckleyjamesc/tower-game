package tower;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.List;

public class StillImage implements Drawable {
	private Image image;	// The image to draw
	private double screenX;	// The x location where the center of the image should be
	private double screenY;	// The y location where the center of the image should be
	private double centerX;	// The x location on the image which is the center
	private double centerY;	// The y location on the image which is the center
	private double theta;	// The angle of rotation around the center to use when drawing the image
	
	/**
	 * @param frames The list of images which will be cycled through
	 * @effects creates a new animation object, initializing all variables.
	 */
	public StillImage(Image image) {
		this.image = image;
		this.screenX = 0.0;
		this.screenY = 0.0;
		this.centerX = 0.0;
		this.centerY = 0.0;
		this.theta = 0.0;
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
}