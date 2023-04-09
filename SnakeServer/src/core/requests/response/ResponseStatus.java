package core.requests.response;

public enum ResponseStatus {
	OK,
	KO,
	END, 
	Other;
	
	public static ResponseStatus fromByte(byte val) throws NoSuchFieldException{
		try {
			return ResponseStatus.values()[val];			
		}catch(ArrayIndexOutOfBoundsException e) {
			throw new NoSuchFieldException();
		}
	}
	
	public byte toByte() {
		return (byte) this.ordinal();
	}

}
