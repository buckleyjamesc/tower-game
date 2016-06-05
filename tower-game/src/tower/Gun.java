package tower;

import java.awt.Graphics;
import java.awt.Image;

public class Gun extends Weapon{
	Image gun;
	StillImage si;
	public Gun(){
		gun = R.gun;	
		si = new StillImage(gun);
		si.setLocation(R.getCenterX(), R.getCenterY()+5.0);
		si.setCenter(50,45);
	}
	@Override
	public void draw(Graphics g) {
		si.draw(g);
	}

	@Override
	public void update() {
		si.setLocation(R.getCenterX(), R.getCenterY()+5.0);
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
		if(Math.cos(angle) < 0.0) {
			R.gp.entities.add(new Bullet(R.gp.p.x - 10.0*Math.sin(angle) + Math.cos(angle) * Math.sqrt(15.0*15.0+35.0*35.0), R.gp.p.y + 5.0*Math.cos(angle) + Math.sin(angle) * Math.sqrt(15.0*15.0+35.0*35.0), this));
		} else {
			R.gp.entities.add(new Bullet(R.gp.p.x + 10.0*Math.sin(angle) + Math.cos(angle) * Math.sqrt(15.0*15.0+35.0*35.0), R.gp.p.y - 5.0*Math.cos(angle) + Math.sin(angle) * Math.sqrt(15.0*15.0+35.0*35.0), this));
		}
	}
}
