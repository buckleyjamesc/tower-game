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

	public Player(double x, double y) {
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		colliding = false;
		state = State.WALKING;
		aHandler = new AnimationHandler(this);
	}
	
	/**
	 * @param state to change to
	 */
	public void setState(State state){
		this.state = state;
		//makes new animation start at zero, avoids OutOfBounds
		this.aHandler.resetPlace();
	}
	public State getState(){
		return state;
	}
	public void update(){
		aHandler.update();
		if(dx ==0 && dy ==0){
			setState(State.STILL);
		}
		else if(!colliding){
			this.setState(State.JUMPING);
		}
	}
	public AnimationHandler getAHandler(){
		return aHandler;
	}
}
