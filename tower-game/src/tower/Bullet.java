package tower;

import java.awt.Graphics;

public class Bullet extends Projectile{

	public Bullet(double x, double y, Weapon parent) {
		super(x, y, parent);
		si = new StillImage(R.bullet);
	}

	@Override
	public void draw(Graphics g) {
		si.draw(g);
	}

	@Override
	public void update() {
		si.setRotation(angle);
		si.setLocation(x, y);
		x+= dx;
		y+= dy;
	}

}
