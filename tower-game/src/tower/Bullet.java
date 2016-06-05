package tower;

import java.awt.Graphics;

public class Bullet extends Projectile{

	public Bullet(double x, double y, Weapon parent) {
		super(x, y, parent);
		si = new StillImage(R.bullet);
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
		si.setLocation(x-R.gp.p.x+400, y-R.gp.p.y+300);
	}
	
	public void interactWith(Entity e) {
		// Just interact with Players for now.
		if(e instanceof Player) {
			R.removeEntity(this);
		} else {
			System.out.println("No interaction defined between " + this.getClass() + " and " + e.getClass());
		}
	}
}
