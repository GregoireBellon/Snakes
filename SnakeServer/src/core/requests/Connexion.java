package core.requests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import request_handling.MayBeResponse;

public class Connexion extends MayBeResponse{

	public static final int ID = 0;	

	private String username;
	private String password; 
	
	private String map;
	
	private boolean con_ok;
	

	public Connexion(JsonNode content){		
		super(content);	
	}
	
	public Connexion(String username, String password) {
		super(false);

		this.username = username; 
		this.password = password;		
	}
	
	public Connexion(boolean con_ok, String map) {
		super(true);
		this.con_ok = con_ok;
		this.map = map;
	}
	

	@Override
	public int getID() {
		return Connexion.ID;
	}
	
	@Override
	protected void parseContent(JsonNode given_content) {
				
		super.parseContent(given_content);
				
		if(!this.isResponse()) {
			this.username = given_content.get("username").asText();
			this.password = given_content.get("password").asText();
		}
		else {
			this.map = given_content.get("map").asText();
			this.con_ok = given_content.get("con_ok").asBoolean();
		}
		
	}
	
	@Override
	protected ObjectNode encodeRequest(ObjectNode base) {
		
		if(!this.isResponse()) {
//			byte[] body = new String(username+";"+password).getBytes();			
			base.put("username", this.username);
			base.put("password", this.password);
			return super.encodeRequest(base);
		}
		
		base.put("map", this.map);
		base.put("con_ok", this.con_ok);
		return super.encodeRequest(base);
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public boolean isConOk() {
		return con_ok;
	}
	
	public void setConOk(boolean con_ok) {
		this.con_ok = con_ok;
	}

	public String getMap() {
		return map;
	}
	
}
