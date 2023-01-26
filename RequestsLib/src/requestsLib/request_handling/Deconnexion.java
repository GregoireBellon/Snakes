package requestsLib.request_handling;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import requestsLib.helpers.ByteConversion;

public class Deconnexion extends Request{
	
	private String username;
	
	public Deconnexion(byte[] content){		
		super(content);	
		
		this.parseContent();
		
	}
	
	public Deconnexion(String username) {
		super();
		this.username = username; 
	}
	

	@Override
	public RequestType getType() {
		return RequestType.Deconnexion;
	}
	
	private void parseContent() {
		
		byte[] curated_content = Arrays.copyOfRange(this.content, 1, this.content.length);
		
		String transcripted_content = new String(curated_content, StandardCharsets.UTF_8);		
				
		this.username = transcripted_content;
		
	}
	
	private void encodeRequest() {
		
		List<Byte> encoder = new ArrayList<Byte>();
		
		encoder.add(this.getType().toByte());
		
		encoder.addAll(ByteConversion.arrayToByteList(new String(username).getBytes()));
		
		
		this.content = new byte[encoder.size()];
		
		for(int i = 0; i < encoder.size(); i++) {
			content[i] = encoder.get(i).byteValue();
		}
				
	}
	
	@Override
	public byte[] getContent() {
		if(content != null) {
			return this.content;
		}
		
		encodeRequest();
		return this.content;
		
	}
	
	
	public String getUsername() {
		return this.username;
	}
	
	
	
}
