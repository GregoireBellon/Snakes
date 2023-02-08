package core.requests;

import java.nio.charset.StandardCharsets;
import request_handling.Request;

public class Connexion extends Request{

	public static final int ID = 0;	

	private String username;
	private String password; 
	
	public Connexion(byte[] content){		
		super(content);	
		
		this.parseContent();
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
	protected void parseContent() {
				
		byte[] content = getContent();
		
		String transcripted_content = new String(content, StandardCharsets.UTF_8);		
		
		String[] splited = transcripted_content.split(";");
		this.username = splited[0];
		this.password = splited[1];		
		
	}
	
	@Override
	protected byte[] encodeRequest() {
		
		byte[] body = new String(username+";"+password).getBytes();
		
		return body;
				
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	
}
