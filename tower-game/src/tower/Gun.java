package tower;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Gun extends Weapon{
	Image gun;
	StillImage si;
	public Gun(){
		gun = R.gun;	
		si = new StillImage(gun);
		si.setLocation(400.0, 270.0);
		si.setCenter(50,45);
	}
	@Override
	public void draw(Graphics g) {
		si.draw(g);
	}

	@Override
	public void update() {
		if(Math.cos(angle) < 0.0) {
			si.setImage(R.flip(R.gun));
			si.setRotation(angle+Math.PI);
		} else {
			si.setImage(R.gun);
			si.setRotation(angle);
		}
	}
	
	@Override
	public void onClick() {
		Projectile.projectiles.add(new Bullet(400 + Math.cos(angle) * 15, 260 + Math.sin(angle) * 15, this));
	}
}
