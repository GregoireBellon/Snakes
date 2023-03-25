package controller;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import behavior.PlayerBehavior;
import core.Context;
import core.ServerSnakeGame;
import core.SnakeGame;
import core.requests.Connexion;
import core.requests.Deconnexion;
import core.requests.MapState;
import core.requests.PlayerInput;
import core.requests.WhichMap;
import core.requests.response.Response;
import helpers.Sender;
import request_handling.AbstractRequestFactory;
import request_handling.FunctionRequest;
import request_handling.Request;
import request_handling.Router;
import utils.AgentAction;
import utils.ColorSnake;
import utils.FeaturesSnake;
import utils.Position;
import utils.Snake;


public class CustomRouter extends Router {

	private ServerSnakeGame game;

	public CustomRouter(ServerSnakeGame game, AbstractRequestFactory req_fac){
		super(req_fac);
		this.game = game;
		
	}

	private FunctionRequest handle_connexion = (Request r, Socket soc) -> {
		Connexion c = (Connexion) r;
		
		List<Position> positions = new ArrayList<Position>();
		
		positions.add(new Position(5,5));
		
		game.addOnlinePlayer(soc, new Snake(new FeaturesSnake(positions, AgentAction.MOVE_DOWN, ColorSnake.Green, false, false), new PlayerBehavior(), game));

		
//		SI TOUT EST OK
		Connexion con_respon = new Connexion(true);
		
		
//		SINON
		Connexion con_respon = new Connexion(false);
		
		
		Sender.send(soc, con_respon);

		//		game.getOnlinePlayers().put(soc, null);
	};

	private FunctionRequest handle_deconnexion = (Request r, Socket soc) -> {
		Deconnexion c = (Deconnexion) r;
		game.removeOnlinePlayer(soc);
	};
	
	private FunctionRequest handle_response = (Request r, Socket soc) -> {
		Response c = (Response) r;
		System.out.println("Requête de réponse");
	};
	
	private FunctionRequest handle_whichmap = (Request r, Socket soc) -> {
		
		WhichMap w = (WhichMap) r;
		System.out.println("Requête whichmap reçue");
		
		if(!w.isResponse()) {
	
			String splitted[] = game.getMap().getFilename().split("/");
			String map_name = splitted[splitted.length - 1];
			
			try {
				Sender.send(soc, new WhichMap(map_name));
			} catch (IOException e) {

				System.err.println("Counldn't send WichMap request to " + soc.getInetAddress().toString() + " : ");
				e.printStackTrace();

			}
		}
	};
	
	private FunctionRequest handle_playerinput = (Request r, Socket soc) -> {
		PlayerInput w = (PlayerInput) r;
		System.out.println("Input from " + soc.getInetAddress() + " : " + w.getAction());
		PlayerBehavior behavior = (PlayerBehavior) this.game.getOnlinePlayers().get(soc).getBehavior();
		behavior.setLastAction(w.getAction());
	};
	
	
	private FunctionRequest handle_mapstate = (Request r, Socket soc) -> {
		
		System.out.println("Requête MapState reçue");

		try {
			
		Sender.send(soc, new MapState(new Context(this.game.getMap().getStart_snakes(), this.game.getMap().getStart_items())));

		} catch (IOException e) {

			System.err.println("Counldn't send MapState request to " + soc.getInetAddress().toString() + " : ");
			e.printStackTrace();

		}

		
	};
	


	@Override
	public Map<Integer, FunctionRequest> requestRoutes() {
		Map<Integer, FunctionRequest> routes = new HashMap<Integer, FunctionRequest>();
		routes.put(Connexion.ID, this.handle_connexion);
		routes.put(Deconnexion.ID, this.handle_deconnexion);
		routes.put(Response.ID, this.handle_response);
		routes.put(WhichMap.ID, this.handle_whichmap);
		routes.put(PlayerInput.ID, handle_playerinput);
		routes.put(MapState.ID, handle_mapstate);
		return routes;
	}


}
