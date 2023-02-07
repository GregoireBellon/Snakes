package game.controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import game.core.Game;
import game.core.InputMap;
import game.core.RemoteGame;
import game.core.event.handler.EventType;
import game.core.event.handler.MapChangedHandler;
import game.core.event.handler.SnakeMovedHandler;
import game.view.PanelSnakeGame;
import game.view.ViewSnakeGame;
import requestsLib.Router;
import requestsLib.helpers.Sender;
import requestsLib.request_handling.game_infos.WhichMap;

public class RemoteSnakeController extends AbstractController {

	private ViewSnakeGame view_snake;
	private CustomClientRouter router;
	private Socket server_socket;
	
	private String server_url;
	private String username;
	private String password;
	

	public RemoteSnakeController(RemoteGame game, String server_url, String username, String password) throws NumberFormatException, UnknownHostException, IOException {
		super(game);
		
		this.server_url = server_url;
		this.username = username; 
		this.password = password;
		
		
		this.initialisationNetworkRoutine();
		
		InputMap map = game.getMap();
				
		
		
		
//		PanelSnakeGame panel = new PanelSnakeGame(map.getSizeX(), map.getSizeY(), map.get_walls(), map.getStart_snakes(), map.getStart_items());

//		this.view_snake = new ViewSnakeGame(this, panel);

		
		
		
		SnakeMovedHandler move_handler = new SnakeMovedHandler(view_snake);

		game.subscribe(EventType.MAP_CHANGED, new MapChangedHandler(view_snake, game.getMap()));
		game.subscribe(EventType.SNAKE_MOVED, move_handler);
	}
	
	private void initialisationNetworkRoutine() throws NumberFormatException, UnknownHostException, IOException {
		System.out.println("Socket is set up");

		String[] splitted = this.server_url.split(":");

		this.server_socket = new Socket(splitted[0], Integer.parseInt(splitted[1]));

		this.router = new CustomClientRouter();
		this.router.listenToSocket(this.server_socket);


		Sender.send(server_socket, new WhichMap(null));		
	}
	
	
	
	
}
