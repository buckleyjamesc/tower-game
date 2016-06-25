package tower;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class StillImage implements Drawable {
	private Image image;	// The image to draw
	private double screenX;	// The x location where the center of the image should be
	private double screenY;	// The y location where the center of the image should be
	private double centerX;	// The x location on the image which is the center
	private double centerY;	// The y location on the image which is the center
	private double theta;	// The angle of rotation around the center to use when drawing the image
	private double xScale; 
	private double yScale;
	private double difference;
	/**
	 * @param frames The list of images which will be cycled through
	 * @effects creates a new animation object, initializing all variables.
	 */
	public StillImage(Image image) {
		setLocation(0.0,0.0);
		setCenter(0.0,0.0);
		setRotation(0.0);
		setScale(1.0,1.0);
		setImage(image);
	}
	
	
	/**
	 * @param trans AffineTransform to be effected
	 * @effects will apply rotation and translation transforms to trans
	 */
	protected void buildTransform(AffineTransform trans) {
		trans.translate(screenX,screenY);
		trans.rotate(theta);
		trans.translate(difference, 0);
		trans.scale(xScale,yScale);
		trans.translate(-1*difference, 0);
		trans.translate(-centerX,-centerY);
	}
	
	/**
	 * @effects will change frameNumber to reflect the correct value
	 */
	protected void update() {}
	
	/**
	 * @param g Graphics to draw the animation to
	 * @effects will draw the correct image to the screen in the correct location
	 */
	@Override
	public void draw(Graphics g) {
		update();
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
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public void setDifference(double difference){
		this.difference = difference;
	}
	
	public void setScale(double xScale, double yScale){
		this.xScale = xScale;
		this.yScale = yScale;
	}
}