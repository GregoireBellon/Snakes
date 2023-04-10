package core.requests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import request_handling.Request;

public class Deconnexion extends Request{

	public static final int ID = 1;	
	
	private String username;
	
	public Deconnexion(JsonNode content){		
		super(content);			
	}
	
	public Deconnexion(String username) {
		super();
		this.username = username; 
	}
	

	@Override
	public int getID() {
		return Deconnexion.ID;
	}
	
	protected void parseContent(JsonNode given_content) {
		
//		super.parseContent(given_content);
		
		this.username = given_content.get("username").asText();
		
	}
		                
	
	protected ObjectNode encodeRequest(ObjectNode base) {
		
		if(base == null) {
			base = Request.mapper.createObjectNode();
		}
		
		base.put("username", username);
		
		return super.encodeRequest(base);
				
	}
		
	public String getUsername() {
		return this.username;
	}
	
	
	
}
