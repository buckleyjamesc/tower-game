package tower;

import java.awt.Graphics;

public class Player extends Entity {
	private State state = null;
	Drawable legs;
	Weapon weaponEquiped;
	Hud hud;

	public Player(double x, double y) {
		super(30, 70);
		this.x = x;
		this.y = y;
		setState(State.WALKING);
		weaponEquiped = new Fists();
		hud = new Hud();
	}
	
	/**
	 * @param state to change to
	 * @effects change the state, updating leg image as needed.
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
		
		// Check if on ground, in free fall, or hitting wall
		double store_dx = dx; double store_dy = dy; 
		dx = 0; dy = 1;
		if(!getConflicts().isEmpty()) {
			setState(State.WALKING);
		} else {
			setState(State.JUMPING);
		}
		dx = store_dx; dy = store_dy;
		
		// Jumping code
		if(getState() == State.WALKING && R.pressedKeys.contains(R.UP_KEY)) {
			dy = -13;
		}
		if(getState() == State.WALKING && R.pressedKeys.contains(R.SHIFT_KEY)) {
			hud.setStamina(hud.getStamina() - .7);
			dx *= 1.01;
		}
		// Left/right movement code
		double speed = 5; f = 0.9;
		if (R.pressedKeys.contains(R.LEFT_KEY)!=R.pressedKeys.contains(R.RIGHT_KEY)) {
			f = 1.0;
			if(R.pressedKeys.contains(R.LEFT_KEY) && dx > -speed) dx += -.75;
			if(R.pressedKeys.contains(R.RIGHT_KEY) && dx < speed) dx += .75;
		}
		
		//should be negative but... works when it is not negative !? check this
		weaponEquiped.setAngle((Math.atan2(MouseMotionHandler.y - R.getCenterY()-5.0, MouseMotionHandler.x - R.getCenterX())));
		weaponEquiped.update();
		
		//update the hud
		hud.update();
	}

	@Override
	public void draw(Graphics g) {
		legs.draw(g);
		weaponEquiped.draw(g);
		hud.draw(g);

	}
	public void onClick(){
		weaponEquiped.onClick();
	}

	public void setWeaponEquiped(Weapon weaponEquiped) {
		this.weaponEquiped = weaponEquiped;
	}
	
	public void interactWith(Entity e) {
		// Just interact with bullet for now.
		if(e instanceof Bullet) {
			System.out.println("OW!!!");
		} else {
			System.out.println("No interaction defined between " + this.getClass() + " and " + e.getClass());
		}
	}
	public Hud getHud(){
		return hud;
	}
}
