package tower;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class WorldBuilder {
	public static Room[][] buildWorld(int w, int h) {
		Cell[][] cells = new Cell[w][h];
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				cells[i][j] = new Cell();
			}
		}
		ArrayList<Integer> xList = new ArrayList<Integer>();
		ArrayList<Integer> yList = new ArrayList<Integer>();
		xList.add(0);
		yList.add(0);
		while (!xList.isEmpty()) {
			int x = xList.remove(xList.size() - 1);
			int y = yList.remove(yList.size() - 1);

			boolean[] can = new boolean[5];
			can[0] = !(x == 0 || cells[x - 1][y].visited());
			can[1] = !(x == w - 1 || cells[x + 1][y].visited());
			can[2] = !(y == 0 || cells[x][y - 1].visited());
			can[3] = !(y == h - 1 || cells[x][y + 1].visited());
			can[4] = false;

			boolean none = true;
			for (int i = 0; i < 4; ++i) {
				if (can[i])
					none = false;
			}
			if (none)
				continue;

			int rand = 4;
			while (!can[rand]) {
				rand = (int) (Math.random() * 2);
				if (Math.random() < .3) {
					rand += 2;
				}
			}

			if (rand == 0) {
				cells[x][y].hasLeft = true;
				cells[x - 1][y].hasRight = true;
				xList.add(x);
				yList.add(y);
				xList.add(x - 1);
				yList.add(y);
			} else if (rand == 1) {
				cells[x][y].hasRight = true;
				cells[x + 1][y].hasLeft = true;
				xList.add(x);
				yList.add(y);
				xList.add(x + 1);
				yList.add(y);
			} else if (rand == 2) {
				cells[x][y].hasUp = true;
				cells[x][y - 1].hasDown = true;
				xList.add(x);
				yList.add(y);
				xList.add(x);
				yList.add(y - 1);
			} else {
				cells[x][y].hasDown = true;
				cells[x][y + 1].hasUp = true;
				xList.add(x);
				yList.add(y);
				xList.add(x);
				yList.add(y + 1);
			}
		}
		Room[][] temp = new Room[w+2][h+2];
		for (int i = 0; i < w+2; ++i) {
			for (int j = 0; j < h+2; ++j) {
				ArrayList<String> arr = null;
				try {
					if(i > 0 && i < w+1 && j > 0 && j < h+1) {
						arr = R.getPossibleRooms(cells[i-1][j-1].hasLeft, cells[i-1][j-1].hasRight, 
								cells[i-1][j-1].hasUp, cells[i-1][j-1].hasDown);
						int rand = (int) (Math.random() * arr.size());
						temp[i][j] = new Room(arr.get(rand));
					} else {
						if((i == 0 || i == w+1) && (j == 0 || j == h+1)) {
							temp[i][j] = new Room("sky_corner");
						} else {
							if(i == 0 || i == w+1) {
								temp[i][j] = new Room("sky_vert");
							} else {
								temp[i][j] = new Room("sky_horiz");
							}
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

		}
		return temp;
	}
}
