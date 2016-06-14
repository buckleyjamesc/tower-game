package tower;

import java.awt.Color;
import java.awt.Graphics;

public class Hud implements Drawable{
	double hp;
	double maxHP;
	double stamina;
	double maxStam;
	double hpRegin;
	double stamRegin;
	final double STARTINGSTAM = 100;
	final double STARTINGHP = 400;
	final int HEIGHT = 35;
	final int LENGTH = 300;

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
		g.drawRect(5, (int) (R.getHeight()) - HEIGHT - 25, (int) LENGTH, HEIGHT);
		g.setColor(Color.red);
		g.fillRect(6, (int) (R.getHeight()) - HEIGHT - 25 + 1, (int) (hp / maxHP * LENGTH) - 1, HEIGHT - 1);
		g.setColor(Color.BLACK);
		g.drawRect(10 + LENGTH, (int) (R.getHeight()) - HEIGHT - 25, (int) LENGTH, HEIGHT);
		g.setColor(Color.YELLOW);
		g.fillRect(11 + LENGTH, (int) (R.getHeight()) - HEIGHT - 25 + 1, (int) (stamina / maxStam * LENGTH) - 1, HEIGHT - 1);

	}
	public void update(){
		//regen up to max, may need tweaking
		if(hp < maxHP){
			hp += hpRegin;
		}
		if(hp > maxHP){
			hp = maxHP;
		}
		if(hp <= 0){
			hp = 0;
		}
		if(stamina < maxStam){
			stamina += stamRegin;
		}
		if(stamina > maxStam){
			stamina = maxStam;
		}
		if(stamina <= 0){
			stamina = 0;
		}
	}
	public void setStamina(double stamina){
		this.stamina = stamina;
	}
	public double getStamina(){
		return stamina;
	}
	public void setHP(double hp){
		this.hp = hp;
	}
	public double getHP(){
		return hp;
	}
}
