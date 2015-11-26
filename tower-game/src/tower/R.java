package tower;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.awt.geom.Line2D;

import javax.imageio.ImageIO;

public class R {
	private static Map<String, Image> imageMap = new HashMap<String, Image>();
	private static Map<String, ArrayList<Line2D.Double>> collisionMap = new HashMap<String, ArrayList<Line2D.Double>>();
	public static Image getImage(String fileName) throws FileNotFoundException, IOException {
		fileName = "src/resources/" + fileName + ".png";
		if(imageMap.containsKey(fileName)) {
			return imageMap.get(fileName);
		} else {
			Image image = ImageIO.read(new File(fileName));
			imageMap.put(fileName, image);
			return image;
		}
	}
	public static ArrayList<Line2D.Double> getCollisions(String fileName) throws FileNotFoundException {
		fileName = "src/resources/" + fileName + ".collisions";
		if(collisionMap.containsKey(fileName)) {
			return collisionMap.get(fileName);
		} else {
			ArrayList<Line2D.Double> collisions = new ArrayList<Line2D.Double>();
			Scanner scan = new Scanner(new File(fileName));
			scan.useDelimiter(", ");
			while(scan.hasNext()) {
				double x1 = scan.nextDouble();
				double y1 = scan.nextDouble();
				double x2 = scan.nextDouble();
				double y2 = scan.nextDouble();
				collisions.add(new Line2D.Double(x1, y1, x2, y2));
			}
			collisionMap.put(fileName, collisions);
			return collisions;
		}
	}
}
