package tower;

import java.awt.Image;
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Room {
	private String filename;
	public Room(String filename){
		this.filename = filename;
	}
	public Image getImage() {			
		try {
			return R.getImage(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Line2D.Double> getCollisions() {
		try {
			return R.getCollisions(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
