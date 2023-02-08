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
	
	protected void parseContent() {
		
		byte[] content = this.getContent();
		
		String transcripted_content = new String(content, StandardCharsets.UTF_8);		
				
		this.username = transcripted_content;
		
	}
	
	protected byte[] encodeRequest() {
						
		return this.username.getBytes();
				
	}
		
	public String getUsername() {
		return this.username;
	}
	
	
	
}
