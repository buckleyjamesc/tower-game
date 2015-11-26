package tower;


public class Cell {
	public boolean hasLeft = false;
	public boolean hasUp = false;
	public boolean hasDown = false;
	public boolean hasRight = false;
	public boolean visited(){
		return hasLeft || hasRight || hasDown || hasUp;
	}
	
	
}
