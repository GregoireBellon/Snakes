package requestsLib.request_handling.game_infos;


import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.List;

import requestsLib.request_handling.GameInfo;
import requestsLib.request_handling.Request;
import requestsLib.request_handling.RequestType;

public class WhichMap extends Request{

	public WhichMap(byte[] content) {
		super(content);
	}
	
	public WhichMap() {
		
	}

	@Override
	public RequestType getType() {
		return RequestType.WhichMap;
	}

	@Override
	public byte[] getContent() {
	
	if(this.content != null) {
		return this.content;
	}
	
	ByteBuffer encoder = ByteBuffer.wrap(new byte[] {this.getType().toByte()}); 
		
	return encoder.array();
	}

}
