package request_handling;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class Request {

	
	protected static final ObjectMapper mapper = new ObjectMapper();
	
	ObjectNode content;


	// [4 bytes header (lenght of content) ] [content : [1 byte type] [X bytes body] ]

	public Request(JsonNode content_recieved){

		this.content = (ObjectNode) content_recieved;

		this.parseContent(this.content);
	}

	public Request() {	
	}

	
	public abstract int getID();

	// Permet de récupérer le contenu de la requête, avec lazy loading + ajout de l'ID du type

	final public ObjectNode fetchContent() {

		ObjectNode content = getContent();

		if(content == null) {
			content = encodeRequest(this.mapper.createObjectNode());	
			setContent(content);
		}

		//		byte[] returned = new byte[content.length + 1];
		//		
		//		System.arraycopy(content, 0, returned, 1, content.length);
		//		returned[0] = (byte) getID();

		return content;
	}
	
	protected abstract void parseContent(JsonNode given_content);
	
	protected ObjectNode encodeRequest(ObjectNode base) {

		if(base == null) {
			base = this.mapper.createObjectNode();
		}		
		
		base.put("id", this.getID());

		return base;

	}		

	final protected ObjectNode getContent() {
		return this.content;
	}

	final protected void setContent(ObjectNode content) {
		this.content = content;
	}

	// Sendable is the content + the header with the lenght of the request
	public String getSendable() {

		ObjectNode content = this.fetchContent();

		return content.toString() + "\n";
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Request)) {
			return false;
		}

		Request converted = (Request) obj;

		this.content = null;

		return this.fetchContent().equals(converted.fetchContent());
	}

}
