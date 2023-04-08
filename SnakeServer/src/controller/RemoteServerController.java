package controller;

import java.net.ServerSocket;

import core.Game;
import core.InputMap;
import core.ServerSnakeGame;
import core.SnakeGame;
import core.event.handler.ClientStateRefresher;
import core.event.handler.EventType;
import request_handling.Router;
import server_tools.CommunicationBridge;

public class RemoteServerController extends AbstractController{

//	private InputMap map;
//	private CommunicationBridge bridge;
//	private Thread bridge_thread;	
	
	public RemoteServerController(ServerSnakeGame game) {
		super(game);
	
//		this.map = game.getMap();
		
//		CommunicationBridge bridge = new CommunicationBridge(socket, router);

//		this.bridge = bridge;
	
//		System.out.println("Launching bridge");
		
//		this.bridge_thread = new Thread(this.bridge);
//		this.bridge_thread.start();
		
		
		ClientStateRefresher refresher = new ClientStateRefresher(game.getOnlinePlayers(), game.getMap());
		
		
		game.subscribe(EventType.SNAKE_MOVED, refresher);
		game.subscribe(EventType.MAP_CHANGED, refresher);
		
		
	}
	
	@Override
		public ServerSnakeGame getGame() {
			// TODO Auto-generated method stub
			return (ServerSnakeGame) super.getGame();
		}	
}
