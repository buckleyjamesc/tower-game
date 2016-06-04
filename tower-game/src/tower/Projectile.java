package tower;

import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Projectile extends Entity {
	protected boolean isFlying;
	protected double angle;
	protected StillImage si;
	protected Weapon parent;
	//public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public Projectile(double x, double y, Weapon parent) {
		super(5,5);
		angle = parent.getAngle();
		this.x = x;
		this.y = y;
		this.parent = parent;
		dx = Math.cos(angle) * 10 + R.gp.p.dx;
		dy = Math.sin(angle) * 10 + R.gp.p.dy;
		isFlying = true;
		
	}

	@Override
	public void applyGravity() {
		
	}
	
	@Override
	public void onCollision(Line2D.Double c) {
		super.onCollision(c);
		R.removeEntity(this);
	}
	/*
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
	}*/
}
