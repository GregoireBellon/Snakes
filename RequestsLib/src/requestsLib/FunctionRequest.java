package requestsLib;

import java.net.Socket;

import requestsLib.request_handling.Request;

public interface FunctionRequest {
	public void fun(Request req, Socket soc);
}
