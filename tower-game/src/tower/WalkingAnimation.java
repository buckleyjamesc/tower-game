package tower;

import java.awt.geom.AffineTransform;

public class WalkingAnimation extends Animation {

	private Player p;
	
	public WalkingAnimation(Player p) {
		super(R.walking);
		setCenter(15,35);
		setDelayTime(50);
		this.p = p;
	}
	
	@Override
	protected void update() {
		setLocation(R.getCenterX(),R.getCenterY());
		if(Math.abs(p.dx) > 0.001) {
			setDelayTime(Math.abs(400*0.75/p.dx));
		} else {
			setDelayTime(99999);
		}
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