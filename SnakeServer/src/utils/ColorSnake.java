package utils;

public enum ColorSnake {
	Green,Red;

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
}
