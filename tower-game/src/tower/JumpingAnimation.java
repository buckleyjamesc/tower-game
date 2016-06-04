package tower;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.List;

public class JumpingAnimation extends Animation {

	private Player p;
	
	public JumpingAnimation(Player p) {
		super(R.jumping);
		setLocation(400, 300);
		setCenter(15,35);
		setDelayTime(100);
		this.p = p;
	}
	
	@Override
	protected void update() {
		//setDelayTime(Math.abs(400*0.75/p.dx));
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