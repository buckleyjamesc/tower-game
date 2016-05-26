package tower;

import java.awt.Graphics;

public class Punch extends Projectile{
	static final int BULLETWIDTH = 17;
	static final int BULLETHEIGHT = 5;
	public Punch(double x, double y, Weapon parent) {
		super(x, y, parent);
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect((int)x, (int)y, BULLETWIDTH, BULLETHEIGHT);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
