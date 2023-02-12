package core.requests;

import java.nio.charset.StandardCharsets;

import request_handling.MayBeResponse;
import request_handling.Request;

public class WhichMap extends MayBeResponse {
	
	public static final int ID = 3;

	private String map_selected; 
	
	public WhichMap(byte [] content) {
		super(content);
	}
	
	
	public WhichMap(String map_selected) {
		super(true);
		this.map_selected = map_selected;
	}
	
	public WhichMap() {
		super(false);
		this.map_selected = "";
	}
	
	
	@Override
	public int getID() {
		return WhichMap.ID;
	}

	@Override
	protected byte[] parseContent(byte[] given_content) {
		
		byte[] content = super.parseContent(given_content);
		
		if(isResponse()) {
			this.map_selected = new String(content, StandardCharsets.UTF_8);
		}
		
		return content;
	}

	@Override
	protected byte[] encodeRequest(byte[] base) {
		
		byte[] returned = super.isResponse() ? this.getMapSelected().getBytes() : new byte[] {};
		
		return super.encodeRequest(returned);
	}
	
	public String getMapSelected() {
		return this.map_selected;
	}
	
}
