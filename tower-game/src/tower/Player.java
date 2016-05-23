package tower;

import java.awt.Image;
import java.util.ArrayList;

public class Player {
	public double x;
	public double y;
	public double dx;
	public double dy;
	public boolean colliding;
	private State state;
	AnimationHandler aHandler;
	Animation animation;

	public Player(double x, double y) {
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		colliding = false;
		state = State.WALKING;
		aHandler = new AnimationHandler(this);
		animation = new Animation(R.walking);
		animation.setLocation(400, 300);
		animation.setCenter(15,70);
		animation.setDelayTime(50);
		animation.setRotation(Math.PI/1.0);
	}
	
	/**
	 * @param state to change to
	 */
	public void setState(State state){
		if (this.state == state) return;
		this.state = state;
		//makes new animation start at zero, avoids OutOfBounds
		this.aHandler.resetPlace();
	}
	public State getState(){
		return state;
	}
	public void update(){
		aHandler.update();
		if(dx>-.05 && dx<.05 && dy>-.05 && dy<.05){
			
			setState(State.STILL);
		}
		else if(!colliding){
			this.setState(State.JUMPING);
		} else {
			this.setState(State.WALKING);
		}
	}
	public AnimationHandler getAHandler(){
		return aHandler;
	}
	public Animation getAnimation(){
		return animation;
	}
}
