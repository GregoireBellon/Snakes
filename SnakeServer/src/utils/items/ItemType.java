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

	public static ItemType fromChar(char c) {
		switch(c) {
		case 'A' : return APPLE;
		case 'B' : return BOX ;
		case 'Y' : return SICK_BALL;
		default : return INVINCIBILITY_BALL;
		}

	}
}