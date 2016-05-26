package tower;

import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Projectile implements Drawable {
	protected boolean isFlying;
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	protected double angle;
	protected StillImage si;
	protected Weapon parent;
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public Projectile(double x, double y, Weapon parent){
		angle = parent.getAngle();
		this.x = x;
		this.y = y;
		this.parent = parent;
		dx = Math.cos(angle) * 10;
		dy = Math.sin(angle) * 10;
		isFlying = true;
		
	}
	public abstract void update();
	public static void drawList(Graphics g){
		for(int i = 0; i < projectiles.size(); ++i){
			projectiles.get(i).draw(g);
		}
	}
	public static void updateList(){
		for(int i = 0; i < projectiles.size(); ++i){
			projectiles.get(i).update();
		}
	}
	public boolean isColliding(){
		//TODO MAKE THIS
		return false;
	}
}
