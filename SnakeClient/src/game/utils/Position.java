package game.utils;

public class Position{

	private int x;
	private int y;


	public Position(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public Position(Position p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "X : "+getX() + "; Y : "+getY();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Position) {
			return ((Position) obj).getX() == this.getX() && ((Position) obj).getY() == this.getY();
		}
		return false; 
	}
}
