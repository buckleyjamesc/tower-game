package tower;

import java.awt.Color;
import java.awt.Graphics;

public class Fists extends Weapon{
	final static double ARMGAPINRADIANS = .1;
	@Override
	public void update() {
		
	}

	@Override
	public void onClick() {
		
	}

	@Override
	public void draw(Graphics g) {
		System.out.println(angle);
		g.setColor(Color.BLACK);
		g.drawLine(400, 260, (int)(Math.cos(angle) * ARMLENGTH)+ 400, (int)(Math.sin(angle) * ARMLENGTH) + 260);
		g.drawLine(400, 260, (int)(Math.cos(angle + .1) * ARMLENGTH)+ 400, (int)(Math.sin(angle + .1) * ARMLENGTH) + 260);

	}
}
