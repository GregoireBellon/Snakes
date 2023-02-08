package request_handling;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public abstract class AbstractRequestFactory {

	//	Cette classe permet essentiellement de remplacer un switch case;

	public abstract Class<? extends Request>[] mapReqIdToClass();

	public abstract Request fromBytes(byte[] content) throws NoSuchFieldException;



	public abstract Request fromSendableStream(InputStream is) throws IOException, NoSuchFieldException ;
	
	public abstract Request fromSocket(Socket socket) throws IOException, NoSuchFieldException, InterruptedException;
}
