package game.utils.items;

public class FeaturesItem implements Cloneable{

	
	private int x;
	private int y;
	private ItemType itemType;
	




	public FeaturesItem(int x, int y, ItemType itemType) {
		
		this.x = x;
		this.y = y;
		this.itemType = itemType;
	
	}
	
	public FeaturesItem(FeaturesItem it) {
		this.x = it.x;
		this.y = it.y;
		this.itemType = it.itemType;
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


	public ItemType getItemType() {
		return itemType;
	}


	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	
}
