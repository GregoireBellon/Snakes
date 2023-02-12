package core.requests.response;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import helpers.ByteConversion;
import request_handling.Request;

public class Response extends Request{

	public static final int ID = 2;	

	private ResponseStatus status;
	private String message;
	
	
	public Response(byte[] content){
		super(content);
	}
	
	public Response(ResponseStatus status, String message){
		super();
		
		this.status = status; 
		this.message = message;
	}
		
	@Override
	protected byte[] parseContent(byte[] given_content) {
		
		byte[] content = super.parseContent(given_content);
		
		try {
			this.status = ResponseStatus.fromByte(content[0]);
		} catch (NoSuchFieldException e) {
			this.status = ResponseStatus.Other;
		}
		
		byte[] curated_content = Arrays.copyOfRange(content, 1, content.length);
		this.message = new String(curated_content, StandardCharsets.UTF_8);
		
		return new byte[] {};						
	}
	
	@Override
	protected byte[] encodeRequest(byte[] base) {
		
		List<Byte> encoder = new ArrayList<Byte>();
				
		encoder.add(this.getStatus().toByte());
		
		encoder.addAll(ByteConversion.arrayToByteList(this.message.getBytes()));
		
		byte[] returned = new byte[encoder.size()];
		
		for(int i = 0; i < encoder.size(); i++) {
			returned[i] = encoder.get(i).byteValue();
		}
		
		return super.encodeRequest(returned);
	}
		
	public ResponseStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int getID() {
		return Response.ID;
	}
	
}
