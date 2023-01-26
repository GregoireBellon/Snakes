package requestsLib.request_handling.response;

public enum ResponseStatus {
	OK,
	KO,
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
