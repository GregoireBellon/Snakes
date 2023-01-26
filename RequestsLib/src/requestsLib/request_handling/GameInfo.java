package requestsLib.request_handling;

public class GameInfo extends Request{

	public GameInfo(byte[] content){
		super(content);
	}
	
	
	

	@Override
	public RequestType getType() {
		return RequestType.GameInfo;
	}

	@Override
	public byte[] getContent() {
		return this.content;
	}
	
}
