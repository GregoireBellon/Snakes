package core.requests.response;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.ByteConversion;
import request_handling.Request;

public class Response extends Request{

	public static final int ID = 2;	

	private ResponseStatus status;
	private String message;
	
	
	public Response(JsonNode content){
		super(content);
	}
	
	public Response(ResponseStatus status, String message){
		super();
		
		this.status = status; 
		this.message = message;
	}
		
	@Override
	protected void parseContent(JsonNode given_content) {
		
//		super.parseContent(given_content);
		
		try {
			this.status = ResponseStatus.fromByte((byte) given_content.get("status").asInt());
		} catch (NoSuchFieldException e) {
			this.status = ResponseStatus.Other;
		}
		
		this.message = given_content.get("message").asText();

	}
	
	@Override
	protected ObjectNode encodeRequest(ObjectNode base) {
		
		if(base == null) {
			base = this.mapper.createObjectNode();
		}

//		List<Byte> encoder = new ArrayList<Byte>();
				
		base.put("status", (int) this.getStatus().toByte());
		base.put("message", this.message);
		
		return super.encodeRequest(base);
	}
		
	public ResponseStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int getID() {
		return Response.ID;
	}
	
}
