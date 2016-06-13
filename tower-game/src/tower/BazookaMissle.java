package tower;

import java.awt.Graphics;
import java.awt.geom.Line2D;

public class BazookaMissle extends Projectile{
	boolean hasHit;
	public BazookaMissle(double x, double y, Weapon parent) {
		super(x, y, parent);
		si = new StillImage(R.bazookamissle);
		si.setCenter(5, 2);
	}

	@Override
	public void draw(Graphics g) {
		si.draw(g);
		
	}

	@Override
	public void update() {
		super.update();
		si.setRotation(angle);
		si.setLocation(x-R.gp.p.x+R.getCenterX(), y-R.gp.p.y+R.getCenterY());
	}
	
	public void interactWith(Entity e) {
		// Just interact with Players for now.
		if(e instanceof Player) {
			R.removeEntity(this);
		} else {
			System.out.println("No interaction defined between " + this.getClass() + " and " + e.getClass());
		}
	}
	@Override
	public void onCollision(Line2D.Double c) {
		//todo spawnn an explosion
		super.onCollision(c);
		R.removeEntity(this);
	}
}
