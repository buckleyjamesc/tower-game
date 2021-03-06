package tower;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Entity implements Drawable {
	
	public double x;			// x location of center of entity
	public double y;			// y location of center of entity
	public double dx;			// x velocity of entity
	public double dy;			// y velocity of entity
	public double f;			// friction related coefficient
	public boolean colliding;	// whether or not the entity collided with a wall this cycle
	public double hitBoxWidth;	// the width of the hit box
	public double hitBoxHeight;	// the height of the hit box
	
	/**
	 * @param w width of the hit box
	 * @param h height of the hit box
	 */
	public Entity(double w, double h) {
		hitBoxWidth = w;
		hitBoxHeight = h;
		colliding = false;
		x = 0;
		y = 0;
		dx = 0;
		dy = 0;
		f = 0.9;
	}
	
	/**
	 * this function will apply the effects of gravity to the entity.
	 * only @Override it if the entity should act abnormally with gravity.
	 */
	public void applyGravity() {
		dy += .40;
	}
	
	// PRIVATE FUNCTION
	private void addIfConflict(ArrayList<Line2D.Double> collisions, Line2D.Double c) {
		if(Line2D.linesIntersect(x, y, x+dx, y+dy, c.x1, c.y1, c.x2, c.y2)) {
			if(Line2D.relativeCCW(c.x1, c.y1, c.x2, c.y2, x, y)>0) {
				collisions.add(c);
			}
		}
	}
	
	/**
	 * @return a list of lines which conflict with the desired movement of this entity
	 */
	public List<Line2D.Double> getConflicts() {
		int room_i = (int) (x / R.gp.S_WIDTH);
		int room_j = (int) (y / R.gp.S_HEIGHT);
		double w = hitBoxWidth/2.0; double h = hitBoxHeight/2.0;
		double x1, x2, y1, y2, d, r;
		ArrayList<Line2D.Double> collisions = new ArrayList<Line2D.Double>();
		for(int i = Math.max(room_i-1,0); i <= Math.min(room_i+1,R.gp.rooms.length-1); i++) {
			for(int j = Math.max(room_j-1,0); j <= Math.min(room_j+1,R.gp.rooms[i].length-1); j++) {
				for (Line2D.Double c : R.gp.rooms[i][j].getHardCollisions()) {
					x1 = c.x1+(double)(R.gp.S_WIDTH*i);
					x2 = c.x2+(double)(R.gp.S_WIDTH*i);
					y1 = c.y1+(double)(R.gp.S_HEIGHT*j);
					y2 = c.y2+(double)(R.gp.S_HEIGHT*j);
					d = (y2>y1)?1.0:-1.0; r = (x2>x1)?1.0:-1.0;
					if(y1==y2) {
						addIfConflict(collisions, new Line2D.Double(x1-w*r, y1-h*r, x2+w*r, y2-h*r));
					} else if (x1==x2) {
						addIfConflict(collisions, new Line2D.Double(x1+w*d, y1-h*d, x2+w*d, y2+h*d));
					} else {
						addIfConflict(collisions, new Line2D.Double(x1-w*r, y1-h*d, x1+w*d, y1-h*r));
						addIfConflict(collisions, new Line2D.Double(x1+w*d, y1-h*r, x2+w*d, y2-h*r));
						addIfConflict(collisions, new Line2D.Double(x2+w*d, y2-h*r, x2+w*r, y2+h*d));
					}
				}
				for (Line2D.Double c : R.gp.rooms[i][j].getSoftCollisions()) {
					x1 = c.x1+(double)(R.gp.S_WIDTH*i);
					x2 = c.x2+(double)(R.gp.S_WIDTH*i);
					y1 = c.y1+(double)(R.gp.S_HEIGHT*j);
					y2 = c.y2+(double)(R.gp.S_HEIGHT*j);
					addIfConflict(collisions, new Line2D.Double(x1, y1-h, x2, y2-h));
				}
			}
		}
		return collisions;
	}
	
	/**
	 * @param c Line to be collided with
	 * @effects will correct the movement of this entity so that it is not
	 * 			conflicting with c anymore, and still maintains as much of the
	 * 			velocity vector as possible
	 */
	public void onCollision(Line2D.Double c) {
		colliding = true;
		double dist = 0*c.ptLineDist(x, y);
		double comp = f*Math.sqrt(dx*dx + dy*dy)*Math.cos(Math.atan2(dy, dx) - Math.atan2(c.y2-c.y1, c.x2-c.x1));
		double len = c.getP1().distance(c.getP2());
		dx = dist*(c.y1-c.y2)/len;
		dy = dist*(c.x2-c.x1)/len;
		dx += comp*(c.x2-c.x1)/len;
		dy += comp*(c.y2-c.y1)/len;
	}
	
	/**
	 * @effects cycle based updates for this entity
	 */
	public void update(){}
	
	/**
	 * @param g Graphics to be painted to
	 * @effects Draws a hit box based in red if colliding, and green if not colliding
	 */
	public void drawHitBox(Graphics g) {
		g.setColor(Color.GREEN);
		if(colliding) g.setColor(Color.RED);
		g.drawRect((int)(x-R.gp.p.x+400-hitBoxWidth/2.0), (int)(y-R.gp.p.y+300-hitBoxHeight/2.0), (int)hitBoxWidth, (int)hitBoxHeight);
	}
	
	@Override
	public void draw(Graphics g) {}

	/**
	 * @param e Entity to interact with
	 * @effects will determine the correct action to take when this entity collides with e.
	 */
	public void interactWith(Entity e) {
		System.out.println("No interaction defined between " + this.getClass() + " and " + e.getClass());
	}
}
