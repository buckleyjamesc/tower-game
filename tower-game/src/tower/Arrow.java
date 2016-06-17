package tower;

import java.awt.Graphics;

public class Arrow extends Projectile{

	public Arrow(double x, double y, Weapon parent, double dFactor) {
		super(x, y, parent, dFactor);
		si = new StillImage(R.arrow);
		si.setCenter(5, 2);
	}
	
	@Override
	public void draw(Graphics g){
		si.draw(g);
	}
	
	@Override
	public void update() {
		super.update();
		si.setRotation(angle);
		si.setLocation(x-R.gp.p.x+R.getCenterX(), y-R.gp.p.y+R.getCenterY());
	}
	
	public void interactWith(Entity e) {
		if(e instanceof Player) {
			R.removeEntity(this);
		} else {
			System.out.println("No interaction defined between " + this.getClass() + " and " + e.getClass());
		}
	}
}
