package utils;



public enum AgentAction {
	
	MOVE_UP,MOVE_DOWN,MOVE_LEFT,MOVE_RIGHT;
	
	public byte toByte() {
		return (byte) this.ordinal();
	}
	
	public static AgentAction fromByte(byte b) {
		try {
			return AgentAction.values()[(int) b];
		}catch(ArrayIndexOutOfBoundsException e) {
			return AgentAction.MOVE_DOWN;
		}
	
	}

}
