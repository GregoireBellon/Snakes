package core.requests;

import request_handling.Request;

public class WhichMap extends Request {
	
	public static final int ID = 3;
	
	public WhichMap(byte [] content) {
		super(content);
	}
	
	
	public WhichMap() {
		super();
	}
	
	@Override
	public int getID() {
		return WhichMap.ID;
	}

	@Override
	protected void parseContent() {
	}

	@Override
	protected byte[] encodeRequest() {
		return new byte[] {};
	}

}
