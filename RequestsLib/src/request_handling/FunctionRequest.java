package request_handling;

import java.net.Socket;

public interface FunctionRequest {
	public void fun(Request req, Socket soc);
}
