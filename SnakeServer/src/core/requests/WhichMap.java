package core.requests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import request_handling.MayBeResponse;

public class WhichMap extends MayBeResponse {
	
	public static final int ID = 3;

	private String map_selected; 
	
	public WhichMap(JsonNode content) {
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
	protected void parseContent(JsonNode given_content) {
		
		super.parseContent(given_content);
		
		if(isResponse()) {
			this.map_selected = given_content.get("map_selected").asText();
		}
		
	}

	@Override
	protected ObjectNode encodeRequest(ObjectNode base) {
		
		if(base == null) {
			base = this.mapper.createObjectNode();
		}
		
		if(this.isResponse()) {
			base.put("map_selected", this.getMapSelected());			
		}
				
		return super.encodeRequest(base);
	}
	
	public String getMapSelected() {
		return this.map_selected;
	}
	
}
