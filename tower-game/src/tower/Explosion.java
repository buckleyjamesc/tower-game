package tower;

import java.awt.Graphics;

public class Explosion extends Entity {

	protected StillImage si;
	private long creationTime;
	private long duration;
	
	public Explosion(double x, double y) {
		super(50, 50);
		this.x = x;
		this.y = y;
		creationTime = System.currentTimeMillis();
		duration = 250;
		si = new StillImage(R.explosion);
		si.setCenter(25, 25);
		si.setLocation(x-R.gp.p.x+R.getCenterX(), y-R.gp.p.y+R.getCenterY());
	}
	
	@Override
	public void applyGravity() {}
	
	@Override
	public void update() {
		si.setLocation(x-R.gp.p.x+R.getCenterX(), y-R.gp.p.y+R.getCenterY());
		if(System.currentTimeMillis() > creationTime + duration) {
			R.removeEntity(this);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		si.draw(g);
	}
}
