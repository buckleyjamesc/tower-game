package tower;

import java.awt.geom.AffineTransform;

public class JumpingAnimation extends Animation {

	private Player p;
	
	public JumpingAnimation(Player p) {
		super(R.jumping);
		setCenter(15,35);
		setDelayTime(100);
		this.p = p;
	}
	
	@Override
	protected void update() {
		setLocation(R.getCenterX(),R.getCenterY());
		super.update();
	}
	
	@Override
	protected void buildTransform(AffineTransform trans) {
		super.buildTransform(trans);
		if(p.dx < 0) {
			trans.scale(-1, 1);
			trans.translate(-30, 0);
		}
	}
}