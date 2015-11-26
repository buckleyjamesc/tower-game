package tower;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.awt.geom.Line2D;

import javax.imageio.ImageIO;

public class R {
	private static Map<String, Image> imageMap = new HashMap<String, Image>();
	private static Map<String, ArrayList<Line2D.Double>> collisionMap = new HashMap<String, ArrayList<Line2D.Double>>();
	private static Map<Integer, ArrayList<String>> data = new HashMap<Integer, ArrayList<String>>();
	public static Image getImage(String fileName) throws FileNotFoundException, IOException {
		fileName = "src/resources/" + fileName + ".png";
		if(!imageMap.containsKey(fileName)) {
			Image image = ImageIO.read(new File(fileName));
			imageMap.put(fileName, image);
		}
		return imageMap.get(fileName);
	}
	public static ArrayList<Line2D.Double> getCollisions(String fileName) throws FileNotFoundException {
		fileName = "src/resources/" + fileName + ".collisions";
		if(!collisionMap.containsKey(fileName)) {
			ArrayList<Line2D.Double> collisions = new ArrayList<Line2D.Double>();
			Scanner scan = new Scanner(new File(fileName));
			scan.useDelimiter(", \n");
			while(scan.hasNext()) {
				double x1 = scan.nextDouble();
				double y1 = scan.nextDouble();
				double x2 = scan.nextDouble();
				double y2 = scan.nextDouble();
				collisions.add(new Line2D.Double(x1, y1, x2, y2));
			}
			collisionMap.put(fileName, collisions);
		}
		return collisionMap.get(fileName);
	}
	public static ArrayList<String> getPossibleRooms(boolean left, boolean right, boolean up, boolean down) throws FileNotFoundException {
		int code = (left?8:0) + (right?4:0) + (up?2:0) + (down?1:0);
		if(data.isEmpty()) {
			Scanner scan = new Scanner(new File("src/resources/data.world"));
			scan.useDelimiter(", \n");
			while(scan.hasNext()) {
				String fileName = scan.next();
				int readCode = 8*scan.nextInt() + 4*scan.nextInt() + 2*scan.nextInt() + scan.nextInt();
				if(!data.containsKey(readCode)) data.put(readCode, new ArrayList<String>());
				data.get(readCode).add(fileName);
			}
		}
		return data.get(code);
	}
}
