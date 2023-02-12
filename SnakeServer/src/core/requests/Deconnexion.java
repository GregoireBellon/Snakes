package core.requests;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import request_handling.Request;

public class Deconnexion extends Request{

	public static final int ID = 1;	
	
	private String username;
	
	public Deconnexion(byte[] content){		
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
	
	protected byte[] parseContent(byte[] given_content) {
		
		byte[] content = super.parseContent(given_content);
		
		String transcripted_content = new String(content, StandardCharsets.UTF_8);		
				
		this.username = transcripted_content;
		
		return new byte[] {};	
	}
	
	protected byte[] encodeRequest(byte[] base) {
						
		return super.encodeRequest(this.username.getBytes());
				
	}
		
	public String getUsername() {
		return this.username;
	}
	
	
	
}
