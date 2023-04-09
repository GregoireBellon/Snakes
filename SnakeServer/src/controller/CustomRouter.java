package controller;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import behavior.PlayerBehavior;
import core.Context;
import core.Game;
import core.InputMap;
import core.ServerSnakeGame;
import core.SnakeGame;
import core.event.handler.EventType;
import core.event.handler.GameOverHandler;
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
import utils.AuthQuery;
import utils.ColorSnake;
import utils.FeaturesSnake;
import utils.Position;
import utils.Snake;


public class CustomRouter extends Router {

	
	private Map<Socket, ServerSnakeGame> game_affectations;

	private List<RemoteServerController> games; 

	public CustomRouter(AbstractRequestFactory req_fac){
		super(req_fac);
//		this.game = game;
		this.games = new ArrayList<RemoteServerController>();
		this.game_affectations = new HashMap<Socket, ServerSnakeGame>();
		
	}

	private FunctionRequest handle_connexion = (Request r, Socket soc) -> {
		Connexion c = (Connexion) r;
		List<Position> positions = new ArrayList<Position>();
		
		positions.add(new Position(5,5));
		System.out.println("Identifiants : " + c.getUsername() + " " + c.getPassword());
		AuthQuery authquery= new AuthQuery(c.getUsername(), c.getPassword());
		
		Connexion con_respon = null;
//		SI TOUT EST OK
		if(authquery.getAuth()) {
			
			RemoteServerController controller = null;
			
			if(this.games.size()==0 || this.games.get(this.games.size()-1).getGame().getOnlinePlayers().size() > 2) {
				
				InputMap new_im;
				
				try {
					new_im = new InputMap("./res/maps/online_aloneNoWall.lay");
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
				ServerSnakeGame new_game = new ServerSnakeGame(new_im);
				
				controller = new RemoteServerController(new_game);
				
				new_game.subscribe(EventType.GAME_OVER, new GameOverHandler(this, controller));
				
				this.games.add(controller);								
				
			}
			else {
				
				System.out.println("Connexion à une game déja existante");
				
				controller = this.games.get(this.games.size() - 1);
				
			}
			
			this.game_affectations.put(soc, controller.getGame());

			
			
			ColorSnake skin = ColorSnake.fromString(authquery.getResponse().getCurrent_skin());
			
			System.out.println("SKIN : " + authquery.getResponse().getCurrent_skin());
			
			controller.getGame().addOnlinePlayer(soc, new Snake(new FeaturesSnake(positions, AgentAction.MOVE_DOWN, skin, false, false), new PlayerBehavior(), controller.getGame()));
			
			
			if((!controller.getGame().getIsRunning()) && (controller.getGame().getOnlinePlayers().size() == 2)) {
				controller.play();				
			}
			
			
			String splitted[] = controller.getGame().getMap().getFilename().split("/");
			String map_name = splitted[splitted.length - 1];

			con_respon = new Connexion(true, map_name);

		}
		
//		SINON
		else {
			con_respon = new Connexion(false, "");
		}
		
		try {
			System.out.println("ENVOI DE LA REPONSE");
			Sender.send(soc, con_respon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	};

	private FunctionRequest handle_deconnexion = (Request r, Socket soc) -> {
		Deconnexion c = (Deconnexion) r;
		
		game_affectations.get(soc).removeOnlinePlayer(soc);
	};
	
	private FunctionRequest handle_response = (Request r, Socket soc) -> {
		Response c = (Response) r;
		System.out.println("Requête de réponse");
	};
	
	private FunctionRequest handle_whichmap = (Request r, Socket soc) -> {
		
		WhichMap w = (WhichMap) r;
		System.out.println("Requête whichmap reçue");
		
//		if(!w.isResponse()) {
			
//			SnakeGame new_game = new SnakeGame("./res/maps/online_aloneNoWall.lay");
//			RemoteServerController new_controller = new RemoteServerController(new_game);
//			
//			this.games.add(new_controller.getGame().addOnlinePlayer(null, null));
//	
//			String splitted[] = game.getMap().getFilename().split("/");
//			String map_name = splitted[splitted.length - 1];
			
//			try {
//				System.out.println("Taille dans le jeu AVANT SEND: " + this.game.getOnlinePlayers().size());
//				Sender.send(soc, new WhichMap(map_name));
//				System.out.println("Taille dans le jeu Apres SEND: " + this.game.getOnlinePlayers().size());
//			} catch (IOException e) {
//
//				System.err.println("Counldn't send WichMap request to " + soc.getInetAddress().toString() + " : ");
//				e.printStackTrace();
//
//			}
//		}
	};
	
	private FunctionRequest handle_playerinput = (Request r, Socket soc) -> {
		PlayerInput w = (PlayerInput) r;
		System.out.println("Input from " + soc.getInetAddress() + " : " + w.getAction());
		PlayerBehavior behavior = (PlayerBehavior) this.game_affectations.get(soc).getOnlinePlayers().get(soc).getBehavior();
		behavior.setLastAction(w.getAction());
	};
	
	
	private FunctionRequest handle_mapstate = (Request r, Socket soc) -> {
		
		System.out.println("Requête MapState reçue");
		ServerSnakeGame game = this.game_affectations.get(soc);
		
		try {
		Sender.send(soc, new MapState(new Context(game.getMap().getStart_snakes(), game.getMap().getStart_items())));
		} catch (IOException e) {

			System.err.println("Counldn't send MapState request to " + soc.getInetAddress().toString() + " : ");
			e.printStackTrace();

		}

		
	};
	
	
	public Map<Socket, ServerSnakeGame> getGame_affectations() {
		return game_affectations;
	}



	public List<RemoteServerController> getGames() {
		return games;
	}

	


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