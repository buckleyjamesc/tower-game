package tower;

import java.awt.Graphics;
import java.awt.Image;

public class Fists extends Weapon{
	final static double ARMGAPINRADIANS = .1;
	Image fists;
	StillImage si;
	public Fists(){
		fists = R.fists;
		si = new StillImage(fists);
		si.setLocation(R.getCenterX(), R.getCenterY()+5.0);
		si.setCenter(50,45);	
	}
	@Override
	public void update() {
		if(Math.cos(angle) < 0.0) {
			si.setImage(R.flip(R.fists));
			si.setRotation(angle+Math.PI);
		} else {
			si.setImage(R.fists);
			si.setRotation(angle);
		}
	}

	@Override
	public void onClick() {
		
	}

	@Override
	public void draw(Graphics g) {
		si.draw(g);
	}
}
