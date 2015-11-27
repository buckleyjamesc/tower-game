package tower;

public class Player {
	public double x;
	public double y;
	public double dx;
	public double dy;
	public boolean colliding;
	public Player(double x, double y) {
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		colliding = false;
	}

}
