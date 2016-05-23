package tower;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Gun extends Weapon{
	Image gun;
	StillImage si;
	public Gun(){
		gun = R.gun;	
		si = new StillImage(gun);
	}
	@Override
	public void draw(Graphics g) {
		si.draw(g);
		g.setColor(Color.BLACK);
		g.drawLine(400, 260, (int)(Math.cos(angle) * ARMLENGTH)+ 400, (int)(Math.sin(angle) * ARMLENGTH) + 260);
	}

	@Override
	public void update() {
		si.setRotation(angle);
		si.setLocation(Math.cos(angle) * ARMLENGTH + 400.0, Math.sin(angle) * ARMLENGTH + 260.0);
		
	}

	@Override
	public void onClick() {
		
	}

}
