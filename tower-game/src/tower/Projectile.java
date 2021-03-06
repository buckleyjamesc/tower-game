package tower;

import java.awt.geom.Line2D;

public class Projectile extends Entity {
	protected double angle;
	protected StillImage si;
	protected Weapon parent;

	public Projectile(double x, double y, Weapon parent) {
		super(10,10);
		angle = parent.getAngle();
		this.x = x;
		this.y = y;
		this.parent = parent;
		dx = Math.cos(angle) * 10.0 + R.gp.p.dx;
		dy = Math.sin(angle) * 10.0 + R.gp.p.dy;
	}
	public Projectile(double x, double y, Weapon parent, double dFactor) {
		super(10,10);
		angle = parent.getAngle();
		this.x = x;
		this.y = y;
		this.parent = parent;
		dx = Math.cos(angle) * 10.0 * dFactor + R.gp.p.dx ;
		dy = Math.sin(angle) * 10.0 * dFactor + R.gp.p.dy ;
	}
	@Override
	public void applyGravity() {
		dy += .05;
	}
	
	@Override
	public void update() {
		super.update();
		angle = Math.atan2(dy,dx);
	}
	
	@Override
	public void onCollision(Line2D.Double c) {
		super.onCollision(c);
		R.removeEntity(this);
	}
}
