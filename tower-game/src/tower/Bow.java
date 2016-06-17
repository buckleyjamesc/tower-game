package tower;

import java.awt.Graphics;

public class Bow extends Weapon{
	StillImage si;
	long startClick;
	public Bow(){
		si = new StillImage(R.bow);
		si.setLocation(R.getCenterX(), R.getCenterX() + 5.0);
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
			si.setImage(R.flip(R.bow));
			si.setRotation(angle+Math.PI);
		} else {
			si.setImage(R.bow);
			si.setRotation(angle);
		}		
	}

	@Override
	public void onClick() {
		if(Math.cos(angle) < 0.0) {
			R.addEntity(new Arrow(R.gp.p.x - 8.0*Math.sin(angle) + Math.cos(angle) * Math.sqrt(15.0*15.0+35.0*35.0), R.gp.p.y + 8.0*Math.cos(angle) + Math.sin(angle) * Math.sqrt(15.0*15.0+35.0*35.0), this, Math.pow((System.currentTimeMillis() - GamePanel.timeStartPress+ 500), .88) / 700.0));
		} else {
			R.addEntity(new Arrow(R.gp.p.x + 8.0*Math.sin(angle) + Math.cos(angle) * Math.sqrt(15.0*15.0+35.0*35.0), R.gp.p.y - 8.0*Math.cos(angle) + Math.sin(angle) * Math.sqrt(15.0*15.0+35.0*35.0),this,Math.pow((System.currentTimeMillis() - GamePanel.timeStartPress+ 500), .88) / 700.0));
		}
	
	}

}
