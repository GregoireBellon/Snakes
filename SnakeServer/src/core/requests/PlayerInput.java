package core.requests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import request_handling.Request;
import utils.AgentAction;

public class PlayerInput extends Request {

	public static final int ID = 4;
	
	private AgentAction action;
	
	public PlayerInput(JsonNode content){
		super(content);
	}
	
	public PlayerInput(AgentAction action){
		this.action = action;
	}
	
	@Override
	public int getID() {
		return PlayerInput.ID;
	}

	@Override
	protected void parseContent(JsonNode given_content) {
		
//		super.parseContent(given_content);
		
		try {
			this.action = AgentAction.values()[given_content.get("action").asInt()];			
		}catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Recieved unknown agent action");
		}
		
	}

	@Override
	protected ObjectNode encodeRequest(ObjectNode base) {

		if(base == null) {
			base = Request.mapper.createObjectNode();
		}

		base.put("action", this.action.ordinal());
		
		return super.encodeRequest(base);
		
	}

	public AgentAction getAction() {
		return this.action;
	}
	
}
