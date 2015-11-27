package tower;

import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Room {
	String filename;
	public Room(String filename){
		this.filename = filename;
	}
	public Image getImage(){
		
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
	
	
}
