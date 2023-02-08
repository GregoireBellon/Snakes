package controller;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import core.TempRoom;
import core.requests.Connexion;
import core.requests.Deconnexion;
import core.requests.response.Response;
import request_handling.AbstractRequestFactory;
import request_handling.FunctionRequest;
import request_handling.Request;
import request_handling.Router;


public class CustomRouter extends Router {

	private TempRoom rm;

	public CustomRouter(TempRoom rm, AbstractRequestFactory req_fac){
		super(req_fac);
		this.rm = rm;
	}

	private FunctionRequest handle_connexion = (Request r, Socket soc) -> {
		Connexion c = (Connexion) r;
		rm.connexion(c.getUsername(), soc);
	};

	private FunctionRequest handle_deconnexion = (Request r, Socket soc) -> {
		Deconnexion c = (Deconnexion) r;
		rm.deconnexion(c.getUsername());
	};
	
	private FunctionRequest handle_response = (Request r, Socket soc) -> {
		Response c = (Response) r;
		System.out.println("Requête de réponse");
	};


	@Override
	public Map<Integer, FunctionRequest> requestRoutes() {
		Map<Integer, FunctionRequest> routes = new HashMap<Integer, FunctionRequest>();
		routes.put(Connexion.ID, this.handle_connexion);
		routes.put(Deconnexion.ID, this.handle_deconnexion);
		routes.put(Response.ID, this.handle_response);
		return routes;
	}


}
