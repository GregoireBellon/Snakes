package game.controller;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import core.requests.response.Response;
import request_handling.AbstractRequestFactory;
import request_handling.FunctionRequest;
import request_handling.Request;
import request_handling.Router;


public class CustomClientRouter extends Router {

               
       public CustomClientRouter(AbstractRequestFactory factory) {
		super(factory);
	}



	private FunctionRequest handle_response = (Request r, Socket soc) -> {
               Response c = (Response) r;
               System.out.println("Réponse reçue : " + c.getMessage());
       };

               
       
       @Override
       public Map<Integer, FunctionRequest> requestRoutes() {
               
               Map<Integer, FunctionRequest> map = new HashMap<Integer, FunctionRequest>();
               
               map.put(Response.ID, handle_response);
               
               return map;
       }

}
