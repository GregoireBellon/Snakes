package core.requests;

import core.server_game.utils.AgentAction;
import request_handling.Request;

public class PlayerInput extends Request {

	public static final int ID = 4;
	
	private AgentAction action;
	
	public PlayerInput(byte[] content){
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
	protected byte[] parseContent(byte[] given_content) {
		
		byte[] content = super.parseContent(given_content);
		
		try {
			this.action = AgentAction.values()[content[0]];			
		}catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Recieved unknown agent action");
		}
		
		return new byte[] {};
	}

	@Override
	protected byte[] encodeRequest(byte[] base) {
		
		return super.encodeRequest(new byte[] {(byte)this.action.ordinal()});
		
	}

	public AgentAction getAction() {
		return this.action;
	}
	
}
