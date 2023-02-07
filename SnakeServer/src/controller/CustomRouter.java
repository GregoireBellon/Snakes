package controller;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import core.TempRoom;
import requestsLib.FunctionRequest;
import requestsLib.Router;
import requestsLib.helpers.Sender;
import requestsLib.request_handling.Connexion;
import requestsLib.request_handling.Deconnexion;
import requestsLib.request_handling.GameInfo;
import requestsLib.request_handling.Request;
import requestsLib.request_handling.RequestFactory;
import requestsLib.request_handling.RequestType;
import requestsLib.request_handling.response.Response;
import requestsLib.request_handling.response.ResponseStatus;

public class CustomRouter extends Router {

	private TempRoom rm;

	public CustomRouter(TempRoom rm){
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
	
	private FunctionRequest handle_whichmap = (Request r, Socket soc) -> {
		
		System.out.println("Client "+soc.getInetAddress() + " asks for the map");
		
		try {
			Sender.send(soc, new Response(ResponseStatus.Other, "Server is specifying the map"));
		} catch (IOException e) {
		
			
			System.err.println("Couldn't awnser to WhichMap Request");
		
		}
		
	};


	@Override
	public Map<RequestType, FunctionRequest> requestRoutes() {
		Map<RequestType, FunctionRequest> routes = new HashMap<RequestType, FunctionRequest>();
		routes.put(RequestType.Connexion, this.handle_connexion);
		routes.put(RequestType.Deconnexion, this.handle_deconnexion);
		routes.put(RequestType.GameInfo, this.handle_game_info);
		routes.put(RequestType.Response, this.handle_response);
		routes.put(RequestType.WhichMap, this.handle_whichmap);
		return routes;
	}


}
