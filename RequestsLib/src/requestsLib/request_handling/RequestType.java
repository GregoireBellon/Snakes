package requestsLib.request_handling;

public enum RequestType {
	Connexion,
	GameInfo,
	Response,
	Deconnexion;
	
	public static RequestType fromByte(byte val) throws NoSuchFieldException{
		try {
			return RequestType.values()[val];			
		}catch(ArrayIndexOutOfBoundsException e) {
			throw new NoSuchFieldException();
		}
	}
	
	public byte toByte() {
		return (byte) this.ordinal();
	}
}
