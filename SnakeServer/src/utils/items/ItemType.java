package utils.items;


public enum ItemType {
	APPLE,BOX,SICK_BALL,INVINCIBILITY_BALL;


	public char toChar() {
		switch(this) {
		case APPLE : return 'A';
		case BOX : return 'B';
		case SICK_BALL : return 'Y';
		default : return 'M';
		}
	}
}