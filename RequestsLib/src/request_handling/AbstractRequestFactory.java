package request_handling;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractRequestFactory {

	protected final static ObjectMapper mapper = new ObjectMapper();
	
	public abstract Class<? extends Request>[] mapReqIdToClass();

	public abstract Request fromJson(JsonNode content) throws NoSuchFieldException;



	public abstract Request fromSendableStream(InputStream is) throws IOException, NoSuchFieldException ;
	
	public abstract Request fromSocket(Socket socket) throws IOException, NoSuchFieldException, InterruptedException;
}
