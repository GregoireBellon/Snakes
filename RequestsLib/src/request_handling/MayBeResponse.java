package request_handling;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class MayBeResponse extends Request {

	private boolean is_response;	
	
	public MayBeResponse(JsonNode content_recieved) {
		super(content_recieved);		
	}
	
	public MayBeResponse(boolean is_response) {
		this.is_response = is_response;
//		System.out.println("Maybe response constructor called");
	}
	
	@Override
	protected void  parseContent(JsonNode given_content) {
		
		
		this.is_response =  given_content.get("is_response").asBoolean();
		
	}
	
	@Override
	protected ObjectNode encodeRequest(ObjectNode base) {
		
		if(base == null) {
			base = Request.mapper.createObjectNode();
		}
		
		base.put("is_response", this.is_response);
	
		return super.encodeRequest(base);
	}

	public boolean isResponse() {
		return this.is_response;
	}
	

}
