package tower;

import java.awt.Graphics;
import java.awt.geom.Line2D;

public class Player extends Entity {
	public boolean colliding;
	private State state = null;
	Drawable legs;
	Weapon weaponEquiped;
	
	@Override
	public void onCollision(Line2D.Double c) {
		super.onCollision(c);
		colliding = true;
	}
	
	public Player(double x, double y) {
		super(30, 70);
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		colliding = false;
		setState(State.WALKING);
		weaponEquiped = new Fists();
	}
	
	/**
	 * @param state to change to
	 */
	public void setState(State state){
		if (this.state == state) return;
		this.state = state;
		if (state == State.WALKING) legs = new WalkingAnimation(this);
		if (state == State.JUMPING) legs = new JumpingAnimation(this);
	}
	public State getState(){
		return state;
	}
	@Override
	public void update(){
		if(!colliding){
			this.setState(State.JUMPING);
		} else {
			this.setState(State.WALKING);
		}
		//should be negative but... works when it is not negative !? check this
		weaponEquiped.setAngle((Math.atan2(MouseMotionHandler.y - 295, MouseMotionHandler.x - 400)));
		weaponEquiped.update();

	}

	@Override
	public void draw(Graphics g) {
		legs.draw(g);
		weaponEquiped.draw(g);

	}
	public void onClick(){
		weaponEquiped.onClick();
	}

	public void setWeaponEquiped(Weapon weaponEquiped) {
		this.weaponEquiped = weaponEquiped;
	}
}
