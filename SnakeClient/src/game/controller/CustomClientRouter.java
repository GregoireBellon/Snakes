package game.controller;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import requestsLib.FunctionRequest;
import requestsLib.Router;
import requestsLib.request_handling.Request;
import requestsLib.request_handling.RequestType;
import requestsLib.request_handling.response.Response;

public class CustomClientRouter extends Router {

		
	private FunctionRequest handle_response = (Request r, Socket soc) -> {
		Response c = (Response) r;
		System.out.println("Réponse reçue : " + c.getMessage());
	};

		
	
	@Override
	public Map<RequestType, FunctionRequest> requestRoutes() {
		
		Map<RequestType, FunctionRequest> map = new HashMap<RequestType, FunctionRequest>();
		
		map.put(RequestType.Response, handle_response);
		
		return map;
	}

}
