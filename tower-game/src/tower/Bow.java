package tower;

import java.awt.Graphics;

public class Bow extends Weapon{
	StillImage si;
	StillImage hands;
	long startClick;
	public Bow(){
		hands = new StillImage(R.bowhands);
		hands.setLocation(R.getCenterX(), R.getCenterX() + 5.0);
		hands.setCenter(50,45);
		hands.setDifference(13);
		si = new StillImage(R.bow);
		si.setLocation(R.getCenterX(), R.getCenterX() + 5.0);
		si.setCenter(50,45);
	}
	
	@Override
	public void draw(Graphics g) {
		si.draw(g);
		hands.draw(g);
	}

	@Override
	public void update() {
		if(GamePanel.isPressing){
			hands.setScale(Math.pow((System.currentTimeMillis() - GamePanel.timeStartPress+ 500), .11), 1);
		}
		else{
			hands.setScale(1, 1);
		}
		si.setLocation(R.getCenterX(), R.getCenterY()+5.0);
		hands.setLocation(R.getCenterX(), R.getCenterY()+5.0);
		if(Math.cos(angle) < 0.0) {
			si.setImage(R.flip(R.bow));
			si.setRotation(angle+Math.PI);
			hands.setImage(R.flip(R.bowhands));
			hands.setRotation(angle+Math.PI);
			hands.setDifference(-1 * 13);
		} else {
			si.setImage(R.bow);
			si.setRotation(angle);
			hands.setImage(R.bowhands);
			hands.setRotation(angle);
			hands.setDifference(13);
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
