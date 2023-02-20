package core;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import utils.Snake;

public class ServerSnakeGame extends LocalSnakeGame {

	
	private Map<Socket, Snake> online_players;
	
	
	public ServerSnakeGame(int max_turn, InputMap map) {
		super(max_turn, map);
		
		this.online_players = new HashMap<Socket, Snake>();
	}
	
	public ServerSnakeGame(InputMap map) {
		this(Integer.MAX_VALUE, map);
	}
	
	@Override
	public void step() {
		System.out.println("Jeu tourne");
		super.step();
	}
	
	public Map<Socket, Snake> getOnlinePlayers() {
		return this.online_players;
	}
	
	public void addOnlinePlayer(Socket so, Snake snake) {
		this.getAgents().add(snake);
		
		this.getMap().getStart_snakes().add(snake.getFeaturesSnake());
		
		this.online_players.put(so, snake);
		System.out.println("Online player added");
	}
	
	public void removeOnlinePlayer(Socket so) {
		Snake player_snake = this.online_players.get(so);
		
		this.getMap().getStart_snakes().remove(player_snake.getFeaturesSnake());
		this.getAgents().remove(player_snake);
		
		this.online_players.remove(so);
	}

}
