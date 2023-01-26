package requestsLib.request_handling.response;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import requestsLib.helpers.ByteConversion;
import requestsLib.request_handling.RequestType;
import requestsLib.request_handling.Request;

public class Response extends Request{

	private ResponseStatus status;
	private String message;
	
	
	public Response(byte[] content){
		super(content);
		this.parseContent();
	}
	
	public Response(ResponseStatus status, String message){
		super();
		
		this.status = status; 
		this.message = message;
		
		this.parseContent();
	}
	
	@Override
	public RequestType getType() {
		return RequestType.Response;
	}

	@Override
	public byte[] getContent() {
		if(this.content == null)
			encodeRequest();
		
		return this.content;
	}
	
	private void parseContent() {
		
		
		try {
			this.status = ResponseStatus.fromByte(this.getContent()[1]);
		} catch (NoSuchFieldException e) {
			this.status = ResponseStatus.Other;
		}
		
		byte[] curated_content = Arrays.copyOfRange(this.getContent(), 2, this.getContent().length);
		this.message = new String(curated_content, StandardCharsets.UTF_8);						
	}
	
	private void encodeRequest() {
		
		List<Byte> encoder = new ArrayList<Byte>();
		
		encoder.add(this.getType().toByte());
		
		encoder.add(this.getStatus().toByte());
		
		encoder.addAll(ByteConversion.arrayToByteList(this.message.getBytes()));
		
		
		this.content = new byte[encoder.size()];
		
		for(int i = 0; i < encoder.size(); i++) {
			content[i] = encoder.get(i).byteValue();
		}
		
	}
	
	public ResponseStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	
}
