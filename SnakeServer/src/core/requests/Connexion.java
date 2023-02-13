package core.requests;

import java.nio.charset.StandardCharsets;
import request_handling.Request;

public class Connexion extends Request{

	public static final int ID = 0;	

	private String username;
	private String password; 
	
	public Connexion(byte[] content){		
		super(content);	
	}
	
	public Connexion(String username, String password) {
		super();

		this.username = username; 
		this.password = password;		
	}
	

	@Override
	public int getID() {
		return Connexion.ID;
	}
	
	@Override
	protected byte[] parseContent(byte[] given_content) {
				
		byte[] content = super.parseContent(given_content);
		
		String transcripted_content = new String(content, StandardCharsets.UTF_8);		
		
		String[] splited = transcripted_content.split(";");
		this.username = splited[0];
		this.password = splited[1];
		
		return new byte[] {};		
	}
	
	@Override
	protected byte[] encodeRequest(byte[] base) {
		
		byte[] body = new String(username+";"+password).getBytes();
		
		return super.encodeRequest(body);
				
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	
}
