package core.requests;

import java.nio.charset.StandardCharsets;

import request_handling.MayBeResponse;

public class Connexion extends MayBeResponse{

	public static final int ID = 0;	

	private String username;
	private String password; 
	
	private boolean con_ok;
	

	public Connexion(byte[] content){		
		super(content);	
	}
	
	public Connexion(String username, String password) {
		super(false);

		this.username = username; 
		this.password = password;		
	}
	
	public Connexion(boolean con_ok) {
		super(true);
		this.con_ok = con_ok;
	}
	

	@Override
	public int getID() {
		return Connexion.ID;
	}
	
	@Override
	protected byte[] parseContent(byte[] given_content) {
				
		byte[] content = super.parseContent(given_content);
		
		String transcripted_content = new String(content, StandardCharsets.UTF_8);		
		
		if(!this.isResponse()) {
			String[] splited = transcripted_content.split(";");
			this.username = splited[0];
			this.password = splited[1];			
		}
		else {
			this.con_ok = transcripted_content.equals("ok");
		}
		
		return new byte[] {};		
	}
	
	@Override
	protected byte[] encodeRequest(byte[] base) {
		
		if(!this.isResponse()) {
			byte[] body = new String(username+";"+password).getBytes();			
			return super.encodeRequest(body);
		}
		byte[] body = new String(this.con_ok ? "ok" : "no").getBytes();
		return super.encodeRequest(body);
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
	
}
