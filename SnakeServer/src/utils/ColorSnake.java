package utils;

public enum ColorSnake {
	Green,Rainbow, Fade, Ninja;

	public byte toByte(){
		return (byte) this.ordinal();
	}

	public static ColorSnake fromByte(byte b) {
		try {
			return ColorSnake.values()[(int) b];
		}catch(ArrayIndexOutOfBoundsException e) {
			return ColorSnake.Green;
		}
	}
		
	public static ColorSnake fromString(String filename){
		if(filename.equals("snake_base")) {
			return ColorSnake.Green;
		}else if(filename.equals("snake_rainbow")) {
			return ColorSnake.Rainbow;
		}else if(filename.equals("snake_fade")) {
			return ColorSnake.Fade;
		}else {
			return ColorSnake.Ninja;
		}
	}
	
	@Override
	public String toString() {
		switch(this) {
		case Fade : return "snake_fade";
		case Green : return "snake_base";
		case Rainbow : return "snake_rainbow";
		case Ninja : return "snake_ninja";
		default : return "snake_base";
		}
	}
}
