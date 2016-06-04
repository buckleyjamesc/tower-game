package tower;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;

public class R {
	private static Map<String, Image> imageMap = new HashMap<String, Image>();
	private static Map<String, ArrayList<Line2D.Double>> collisionMap = new HashMap<String, ArrayList<Line2D.Double>>();
	private static Map<Integer, ArrayList<String>> data = new HashMap<Integer, ArrayList<String>>();
	public static ArrayList<Image> walking = new ArrayList<Image>();
	public static List<Image> jumping = new ArrayList<Image>();;
	public static BufferedImage gun;
	public static BufferedImage bullet;
	public static BufferedImage fists;
	public static AffineTransform identity;
	public static GamePanel gp;
	public static Set<Entity> toRemove = new HashSet<Entity>();
	
	public static void init() throws IOException {
		identity = new AffineTransform();
		gun = ImageIO.read(new File("src/resources/" + "gun2" + ".png"));
		fists = ImageIO.read(new File("src/resources/" + "fists" + ".png"));
		bullet = ImageIO.read(new File("src/resources/" + "bullet" + ".png"));
		for(int i = 0; i < 4; ++i) {
			String fileName = "src/resources/" + "player_walking_" + i + ".png";
			BufferedImage image = ImageIO.read(new File(fileName));
			walking.add(image);
		}
		for(int i = 0; i < 5; ++i) {
			String fileName = "src/resources/" + "player_jumping_" + i + ".png";
			BufferedImage image = ImageIO.read(new File(fileName));
			jumping.add(image);
		}
	}
	
	public static Image flip(BufferedImage image) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-image.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		BufferedImage imageLeft = op.filter(image, null);
		return imageLeft;
	}
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
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				Scanner scan2 = new Scanner(line);
				scan2.useDelimiter(",");
				System.out.println("Scanning line '" + line + "' of file " + fileName);
				double x1 = scan2.nextDouble();
				double y1 = scan2.nextDouble();
				double x2 = scan2.nextDouble();
				double y2 = scan2.nextDouble();
				collisions.add(new Line2D.Double(x1, y1, x2, y2));
				scan2.close();
			}
			scan.close();
			collisionMap.put(fileName, collisions);
		}
		return collisionMap.get(fileName);
	}
	public static ArrayList<String> getPossibleRooms(boolean left, boolean right, boolean up, boolean down) throws FileNotFoundException {
		int code = (left?8:0) + (right?4:0) + (up?2:0) + (down?1:0);
		if(data.isEmpty()) {
			Scanner scan = new Scanner(new File("src/resources/data.world"));
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				Scanner scan2 = new Scanner(line);
				scan2.useDelimiter(",");
				String fileName = scan2.next();
				int readCode = 8*scan2.nextInt() + 4*scan2.nextInt() + 2*scan2.nextInt() + scan2.nextInt();
				if(!data.containsKey(readCode)) data.put(readCode, new ArrayList<String>());
				System.out.println("Scanning line '" + line + "' of file " + "src/resources/data.world" + ", " + readCode + " -> \"" + fileName + "\"");
				data.get(readCode).add(fileName);
				scan2.close();
			}
			scan.close();
		}
		System.out.println("" + code + " -> \"" + data.get(code).get(0) + "\"");
		return data.get(code);
	}

	public static boolean isRemoved(Entity e) {
		return toRemove.contains(e);
	}
	public static void removeEntity(Entity e) {
		toRemove.add(e);
	}

	public static void removeEntities() {
		for(Entity e : toRemove) {
			gp.entities.remove(e);
		}
		toRemove.clear();
	}
}
