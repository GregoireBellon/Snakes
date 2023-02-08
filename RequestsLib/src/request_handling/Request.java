package request_handling;

import java.nio.ByteBuffer;

public abstract class Request {

	private byte[] content;
	
	
	// [4 bytes header (lenght of content) ] [content]
	
	public Request(byte[] content_recieved){
		
		this.content = new byte[content_recieved.length -1];
		
		System.arraycopy(content_recieved, 1, this.content, 0, content_recieved.length -1);	
		
		this.parseContent();
	}
	
	public Request() {	
	}
	
	public abstract int getID();
	
	// Permet de récupérer le contenu de la requête, avec lazy loading + ajout de l'ID du type
	
	final public byte[] fetchContent() {
		
		byte[] content = getContent();
		
		if(content == null) {
			content = encodeRequest();	
			setContent(content);
		}
		
		byte[] returned = new byte[content.length + 1];
		
		System.arraycopy(content, 0, returned, 1, content.length);
		returned[0] = (byte) getID();
		
		return returned;
	}
		
	protected abstract void parseContent();
	
	protected abstract byte[] encodeRequest();			
	
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
				
}
