package tower;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Hud implements Drawable{
	double hp;
	double maxHP;
	double stamina;
	double maxStam;
	double hpRegin;
	double stamRegin;
	final double STARTINGSTAM = 100;
	final double STARTINGHP = 400;
	final int HEIGHT = 75;
	final int LENGTH = 400;

	public Hud(){
		maxHP = 400;
		maxStam = 100;
		stamina = STARTINGSTAM;
		hp = STARTINGHP;
		hpRegin = .1;
		stamRegin = .2;
	}
	@Override
	public void draw(Graphics g) {
		//assumes 1920x1080, draws bars for stamina and hp
		g.setColor(Color.BLACK);
		g.drawRect(5, 1080 - HEIGHT - 25, (int) LENGTH, HEIGHT);
		g.setColor(Color.red);
		g.fillRect(6, 1080 - HEIGHT - 25 + 1, (int) (hp / maxHP * LENGTH) - 1, HEIGHT - 1);
		g.setColor(Color.BLACK);
		g.drawRect(10 + LENGTH, 1080 - HEIGHT - 25, (int) LENGTH, HEIGHT);
		g.setColor(Color.YELLOW);
		g.fillRect(11 + LENGTH, 1080 - HEIGHT - 25 + 1, (int) (stamina / maxStam * LENGTH) - 1, HEIGHT - 1);

	}
	public void update(){
		//regen up to max, may need tweaking
		if(hp < maxHP){
			hp += hpRegin;
		}
		if(hp > maxHP){
			hp = maxHP;
		}
		if(stamina < maxStam){
			stamina += stamRegin;
		}
		if(stamina > maxStam){
			stamina = maxStam;
		}
	}
	public void setStamina(double stamina){
		this.stamina = stamina;
	}
	public double getStamina(){
		return stamina;
	}
}
