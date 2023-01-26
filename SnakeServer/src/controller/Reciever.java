package controller;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import core.TempRoom;
import requestsLib.request_handling.Connexion;
import requestsLib.request_handling.Deconnexion;
import requestsLib.request_handling.GameInfo;
import requestsLib.request_handling.Request;
import requestsLib.request_handling.RequestFactory;
import requestsLib.request_handling.RequestType;
import requestsLib.request_handling.response.Response;

public class Reciever extends RequestHandler {

	private TempRoom rm;

	public Reciever(TempRoom rm){
		super();
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

	private FunctionRequest handle_game_info = (Request r, Socket soc) -> {
		GameInfo c = (GameInfo) r;
		System.out.println("Requête d'info de game");
	};
	
	private FunctionRequest handle_response = (Request r, Socket soc) -> {
		Response c = (Response) r;
		System.out.println("Requête de réponse");
	};


	@Override
	public Map<RequestType, FunctionRequest> requestRoutes() {
		Map<RequestType, FunctionRequest> routes = new HashMap<RequestType, FunctionRequest>();
		routes.put(RequestType.Connexion, this.handle_connexion);
		routes.put(RequestType.Deconnexion, this.handle_deconnexion);
		routes.put(RequestType.GameInfo, this.handle_game_info);
		routes.put(RequestType.Response, this.handle_response);
		return routes;
	}


}
