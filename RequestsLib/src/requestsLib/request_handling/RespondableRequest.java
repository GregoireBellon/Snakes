package requestsLib.request_handling;

public abstract class RespondableRequest extends Request{
	
	public abstract boolean isResponse();
	
	public boolean isRequest() {
		return !this.isResponse();
	}
	
	@Override
	public byte[] getContent() {
		byte[] base = super.getContent();
		
		byte[] with_isResponse = new byte[base.length + 1];
		
		with_isResponse[base.length] = (byte) (this.isResponse()?1:0);
		
		return with_isResponse;
	}
	
}
