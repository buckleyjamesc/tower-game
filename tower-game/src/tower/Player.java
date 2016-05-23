package tower;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class Player implements Drawable{
	public double x;
	public double y;
	public double dx;
	public double dy;
	public boolean colliding;
	private State state = null;
	Drawable legs;

	public Player(double x, double y) {
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		colliding = false;
		setState(State.WALKING);
	}
	
	/**
	 * @param state to change to
	 */
	public void setState(State state){
		if (this.state == state) return;
		this.state = state;
		if (state == State.WALKING) legs = new WalkingAnimation(this);
		if (state == State.JUMPING) legs = new JumpingImage(this);
	}
	public State getState(){
		return state;
	}
	public void update(){
		if(!colliding){
			this.setState(State.JUMPING);
		} else {
			this.setState(State.WALKING);
		}
	}

	@Override
	public void draw(Graphics g) {
		legs.draw(g);
	}
}
