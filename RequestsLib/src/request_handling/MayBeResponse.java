package request_handling;

public abstract class MayBeResponse extends Request {

	private boolean is_response;	
	
	public MayBeResponse(byte[] content_recieved) {
		super(content_recieved);		
	}
	
	public MayBeResponse(boolean is_response) {
		this.is_response = is_response;
//		System.out.println("Maybe response constructor called");
	}
	
	@Override
	protected byte[] parseContent(byte[] given_content) {
		
		byte[] content = super.parseContent(given_content);

		this.is_response =  ((int) content[0]) == 1;
		
//		System.out.println("content[0] : " + content[0]);		
//		System.out.println("Is response : " + isResponse());
//		System.out.println("given_content size : " + given_content.length);
		
		byte[] resulting_content = new byte[content.length - 1];
		
		System.arraycopy(content, 1, resulting_content, 0, content.length -1);	
		
		return resulting_content;		
		
	}
	
	@Override
	protected byte[] encodeRequest(byte [] base) {
		
		if(base == null) {
			base = new byte[0];
		}
		
//		System.out.println("base size : " + base.length);
		
		byte[] returned = new byte[base.length + 1];
		
		System.arraycopy(base, 0, returned, 1, base.length);
		
//		System.out.println("is response : " + this.isResponse());
		returned[0] = (byte) (this.isResponse() ?  1 : 0);
	
//		System.out.println("Returned[0] : " + returned[0]);
		
//		System.out.println("returned size : " + returned.length);

		return super.encodeRequest(returned);
	}

	public boolean isResponse() {
		return this.is_response;
	}
	

}
