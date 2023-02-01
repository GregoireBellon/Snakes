package requestsLib.request_handling;

import java.nio.ByteBuffer;

public abstract class Request {

	protected byte[] content;
	
	
	// [4 bytes header (lenght of content) ] [content]
	
	public Request(byte[] content){
		this.content = content; 
	}
	
	public Request() {
		
	}
	
	public abstract RequestType getType();
	
	public abstract byte[] getContent();
	
	
	// Sendable is the content + the header with the 
	
	public byte[] getSendable() {
		
		byte[] content = this.getContent();
		
		ByteBuffer wrapped = ByteBuffer.allocate(content.length + 4);
		wrapped.putInt(content.length);
		wrapped.put(content);
		
		return wrapped.array();
		
	}
	
	public void send() {
		
	};
			
}
