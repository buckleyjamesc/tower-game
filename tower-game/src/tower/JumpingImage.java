package tower;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.List;

public class JumpingImage extends StillImage {

	private Player p;
	
	public JumpingImage(Player p) {
		super(R.walking.get(3));
		setLocation(400, 300);
		setCenter(15,70);
		this.p = p;
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