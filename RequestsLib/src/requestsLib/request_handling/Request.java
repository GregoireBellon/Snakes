package requestsLib.request_handling;

import java.nio.ByteBuffer;

public abstract class Request {

	protected byte[] content;
	
	public Request(byte[] content){
		
		this.content = content;
		
	}
	
	public Request() {
		
	}
	
	public abstract RequestType getType();
	
	public byte[] getContent() {
		return new byte[] {this.getType().toByte()};
		
	}
	
	public byte[] getSendable() {
		
		byte[] content = this.getContent();
		
		ByteBuffer wrapped = ByteBuffer.allocate(content.length + 4);
		wrapped.putInt(content.length);
		wrapped.put(content);
		
		return wrapped.array();
		
	}
				
}
