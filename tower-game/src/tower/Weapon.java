package tower;

public abstract class Weapon implements Drawable{
	final static double ARMLENGTH = 30.0;
	protected double angle;
	public abstract void update();
	public abstract void onClick();
	public void setAngle(double angle){
		this.angle = angle;
	}
}
