package tower;

import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Entity implements Drawable {
	public double x;
	public double y;
	public double dx;
	public double dy;
	public double f;
	public boolean colliding;
	public double hitBoxWidth;
	public double hitBoxHeight;
	
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
	
	public void applyGravity() {
		dy += .40;
	}
	
	private void addIfConflict(ArrayList<Line2D.Double> collisions, Line2D.Double c) {
		if(Line2D.linesIntersect(x, y, x+dx, y+dy, c.x1, c.y1, c.x2, c.y2)) {
			if(Line2D.relativeCCW(c.x1, c.y1, c.x2, c.y2, x, y)>0) {
				collisions.add(c);
			}
		}
	}
	
	public ArrayList<Line2D.Double> getConflicts() {
		int room_i = (int) (x / R.gp.S_WIDTH);
		int room_j = (int) (y / R.gp.S_HEIGHT);
		double w = hitBoxWidth/2.0; double h = hitBoxHeight/2.0;
		double x1, x2, y1, y2, d, r;
		Line2D.Double L1, L2, L12;
		ArrayList<Line2D.Double> collisions = new ArrayList<Line2D.Double>();
		for(int i = room_i-1; i <= room_i+1; i++) {
			for(int j = room_j-1; j <= room_j+1; j++) {
				for (Line2D.Double c : R.gp.rooms[i][j].getCollisions()) {
					x1 = c.x1+(double)(R.gp.S_WIDTH*i);
					x2 = c.x2+(double)(R.gp.S_WIDTH*i);
					y1 = c.y1+(double)(R.gp.S_HEIGHT*j);
					y2 = c.y2+(double)(R.gp.S_HEIGHT*j);
					d = (y2>y1)?1.0:-1.0; r = (x2>x1)?1.0:-1.0;
					addIfConflict(collisions, new Line2D.Double(x1-w*r, y1-h*d, x1+w*d, y1-h*r));
					addIfConflict(collisions, new Line2D.Double(x1+w*d, y1-h*r, x2+w*d, y2-h*r));
					addIfConflict(collisions, new Line2D.Double(x2+w*d, y2-h*r, x2+w*r, y2+h*d));
				}
			}
		}
		return collisions;
	}
	
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
	public void update(){}
	
	public void drawHitBox(Graphics g) {
		g.drawRect((int)(x-R.gp.p.x+400-hitBoxWidth/2.0), (int)(y-R.gp.p.y+300-hitBoxHeight/2.0), (int)hitBoxWidth, (int)hitBoxHeight);
	}
	
	@Override
	public void draw(Graphics g) {}

	public void interact(Entity o) {
		System.out.println("No interaction defined between " + this.getClass() + " and " + o.getClass());
	}
}
