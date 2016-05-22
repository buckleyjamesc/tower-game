package tower;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class AnimationHandler {
	//Lists of images to iterate through for images
	ArrayList<Image> walking;
	ArrayList<Image> hit;
	ArrayList<Image> jumping;
	ArrayList<Image> running;
	ArrayList<Image> walkingLeft;
	//delay between cycles
	static final double DELAY = 500;
	//will eventually get above Integer.MAX_VALUE
	//used to iterate through images
	int place;
	//needed to link with states etc. . . Bad coding practice?
	Player p;
	double lastChangeTime;
	double currentTime;
	public AnimationHandler(Player p){
		this.p = p;
		place = 0;
		//-infinity since last change. . . Impractical?
		lastChangeTime = Double.MIN_VALUE;
		currentTime = System.currentTimeMillis();
		//TODO Initialize images
		walking = new ArrayList<Image>();
		hit = new ArrayList<Image>();
		jumping = new ArrayList<Image>();
		running = new ArrayList<Image>();
		walkingLeft = new ArrayList<Image>();
	}
	
	/**
	 * @param graphics and ImageObserver (needed to drawImages) taken from paintComponent in GamePanel.java
	 * Precondition: arrays of images are initialized w/ size > 0
	 */
	public void draw(Graphics g, ImageObserver io){
	/*	switch (p.getState()){
			case WALKING:
				if(p.dx >= 0){
					g.drawImage(walking.get(place % walking.size()), 390, 280, io);
				}
				else{
					g.drawImage(walkingLeft.get(place % walkingLeft.size()), 390, 280, io);
				}
			break;
			case RUNNING:
				g.drawImage(running.get(place % running.size()), 390, 280, io);
			break;
			case HIT:
				g.drawImage(hit.get(place % hit.size()),390 ,280 , io);
			break;
			case JUMPING:
				g.drawImage(jumping.get(place % jumping.size()), 390, 280, io);
			break;
		case STILL:
			break;
		}*/
	}
	//no posts or pres
	public void update(){
		currentTime = System.currentTimeMillis();	
		if(currentTime - lastChangeTime >= DELAY){
			place++;
			lastChangeTime = System.currentTimeMillis();
		}
	}
	//used when changing state of player
	public void resetPlace(){
		place = 0;
	}
}
