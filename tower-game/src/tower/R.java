package tower;

import java.awt.Image;
import java.awt.event.KeyEvent;
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
import java.awt.geom.Line2D.Double;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class R {
	public static final Integer UP_KEY = KeyEvent.VK_W;
	public static final Integer DOWN_KEY = KeyEvent.VK_S;
	public static final Integer LEFT_KEY = KeyEvent.VK_A;
	public static final Integer RIGHT_KEY = KeyEvent.VK_D;
	public static final int DOOR_W = 60;
	public static final int DOOR_H = 100;
	private static Map<String, Image> imageMap = new HashMap<String, Image>();
	private static Map<String, ArrayList<Line2D.Double>> collisionMap = new HashMap<String, ArrayList<Line2D.Double>>();
	private static Map<Integer, ArrayList<Line2D.Double>> wallsMap = new HashMap<Integer, ArrayList<Line2D.Double>>();
	private static Map<Integer, ArrayList<String>> data = new HashMap<Integer, ArrayList<String>>();
	public static ArrayList<Image> walking = new ArrayList<Image>();
	public static List<Image> jumping = new ArrayList<Image>();
	public static Set<String> leftRooms = new HashSet<String>();
	public static Set<String> rightRooms = new HashSet<String>();
	public static Set<String> upRooms = new HashSet<String>();
	public static Set<String> downRooms = new HashSet<String>();
	public static BufferedImage gun;
	public static BufferedImage bullet;
	public static BufferedImage fists;
	public static AffineTransform identity;
	public static GamePanel gp;
	public static Set<Entity> toRemove = new HashSet<Entity>();
	public static Set<Integer> pressedKeys = new HashSet<Integer>();

	public static double getWidth() { return gp.getWidth(); }
	public static double getHeight() { return gp.getHeight(); }
	public static double getCenterX() { return getWidth()/2.0; }
	public static double getCenterY() { return getHeight()/2.0; }
	
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
 	public static ArrayList<Line2D.Double> getHardCollisions(String fileName) throws FileNotFoundException {
 		return makeWalls(fileName);
 	}
	public static ArrayList<Line2D.Double> getSoftCollisions(String fileName) throws FileNotFoundException {
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
	private static ArrayList<Double> makeWalls(String fileName) {
		boolean leftOpen = leftRooms.contains(fileName);
		boolean rightOpen = rightRooms.contains(fileName);
		boolean upOpen = upRooms.contains(fileName);
		boolean downOpen = downRooms.contains(fileName);
		int code = (leftOpen?8:0) + (rightOpen?4:0) + (upOpen?2:0) + (downOpen?1:0);
		if(!wallsMap.containsKey(code)) {
			ArrayList<Line2D.Double> walls = new ArrayList<Line2D.Double>();
			if(downOpen) {
				walls.add(new Line2D.Double(leftOpen?0:30,570,400-DOOR_W/2,570));
				walls.add(new Line2D.Double(400-DOOR_W/2,570,400-DOOR_W/2,600));
				walls.add(new Line2D.Double(400+DOOR_W/2,600,400+DOOR_W/2,570));
				walls.add(new Line2D.Double(400+DOOR_W/2,570,rightOpen?800:770,570));
			} else {
				walls.add(new Line2D.Double(leftOpen?0:30,570,rightOpen?800:770,570));
			}
			if(rightOpen) {
				walls.add(new Line2D.Double(800,570-DOOR_H,770,570-DOOR_H));
			}
			walls.add(new Line2D.Double(770,rightOpen?570-DOOR_H:570,770,30));
			if(upOpen) {
				walls.add(new Line2D.Double(770,30,400+DOOR_W/2,30));
				walls.add(new Line2D.Double(400+DOOR_W/2,30,400+DOOR_W/2,0));
				walls.add(new Line2D.Double(400-DOOR_W/2,0,400-DOOR_W/2,30));
				walls.add(new Line2D.Double(400-DOOR_W/2,30,30,30));
			} else {
				walls.add(new Line2D.Double(770,30,30,30));
			}
			walls.add(new Line2D.Double(30,30,30,leftOpen?570-DOOR_H:570));
			if(leftOpen) {
				walls.add(new Line2D.Double(30,570-DOOR_H,0,570-DOOR_H));
			}
			wallsMap.put(code, walls);
		}
		return wallsMap.get(code);
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
				int roomLeft = scan2.nextInt();
				int roomRight = scan2.nextInt();
				int roomUp = scan2.nextInt();
				int roomDown = scan2.nextInt();
				int readCode = 8*roomLeft + 4*roomRight + 2*roomUp + roomDown;
				if(!data.containsKey(readCode)) data.put(readCode, new ArrayList<String>());
				System.out.println("Scanning line '" + line + "' of file " + "src/resources/data.world" + ", " + readCode + " -> \"" + fileName + "\"");
				data.get(readCode).add(fileName);
				if(0!=roomLeft) leftRooms.add(fileName);
				if(0!=roomRight) rightRooms.add(fileName);
				if(0!=roomUp) upRooms.add(fileName);
				if(0!=roomDown) downRooms.add(fileName);
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
		synchronized(gp.entities) {
		for(Entity e : toRemove) {
			gp.entities.remove(e);
		}}
		toRemove.clear();
	}
}
