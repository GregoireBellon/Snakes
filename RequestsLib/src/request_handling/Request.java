package request_handling;

import java.nio.ByteBuffer;
import java.util.Arrays;

public abstract class Request {

	private byte[] content;
	
	
	// [4 bytes header (lenght of content) ] [content : [1 byte type] [X bytes body] ]
	
	public Request(byte[] content_recieved){
		
		this.content = content_recieved;
				
		this.parseContent(this.content);
	}
	
	public Request() {	
	}
	
	public abstract int getID();
	
	// Permet de récupérer le contenu de la requête, avec lazy loading + ajout de l'ID du type
	
	final public byte[] fetchContent() {
		
		byte[] content = getContent();
		
		if(content == null) {
			content = encodeRequest(new byte[]{});	
			setContent(content);
		}
		
//		byte[] returned = new byte[content.length + 1];
//		
//		System.arraycopy(content, 0, returned, 1, content.length);
//		returned[0] = (byte) getID();
		
		return content;
	}
		
	protected byte[] parseContent(byte[] given_content) {
		
		byte[] resulting_content = new byte[given_content.length - 1];
		
		System.arraycopy(given_content, 1, resulting_content, 0, given_content.length -1);	
		
		return resulting_content;		
	}
	
	protected byte[] encodeRequest(byte[] base) {
		
	if(base == null) {
		base = new byte[0];
	}
	
	byte[] returned = new byte[base.length + 1];
	
	System.arraycopy(base, 0, returned, 1, base.length);
	
	returned[0] = (byte) getID();
	
	return returned;
	
	}		
	
	final protected byte[] getContent() {
		return this.content;
	}
	
	final protected void setContent(byte[] content) {
		this.content = content;
	}
	
	// Sendable is the content + the header with the lenght of the request
	public byte[] getSendable() {
		
		byte[] content = this.fetchContent();
		
		ByteBuffer wrapped = ByteBuffer.allocate(content.length + 4);
		wrapped.putInt(content.length);
		wrapped.put(content);
		
		return wrapped.array();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof Request)) {
			return false;
		}
		
		Request converted = (Request) obj;
		
		
		return Arrays.equals(this.fetchContent(), converted.getContent());
	}
				
}
