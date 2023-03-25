package core;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.EndgameQuery;
import utils.Snake;

public class ServerSnakeGame extends LocalSnakeGame {

	
	private Map<Socket, Snake> online_players;
	private List<Snake> graveyard;
	
	
	public ServerSnakeGame(int max_turn, InputMap map) {
		super(max_turn, map);
		
		this.online_players = new HashMap<Socket, Snake>();
		this.graveyard = new ArrayList<Snake>();
		
	}
	
	public ServerSnakeGame(InputMap map) {
		this(Integer.MAX_VALUE, map);
	}
	
	@Override
	public void step() {
		System.out.println("Jeu tourne");
		super.step();
	}
	
	
	@Override
	public void takeTurn() {
		// TODO Auto-generated method stub
		super.takeTurn();
		for(Snake s : this.getAgents()) {
			if(!s.getAlive() && !this.graveyard.contains(s)) {
				this.graveyard.add(s);
			}
		}
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		//Replace the ids value by the actual ids of the players once the function is implemented
		super.gameOver();
		System.out.println("Enregistrement des résultats :");
		EndgameQuery query = new EndgameQuery();
		int[] ids = new int[this.graveyard.size()];
		int[] ranks = new int[this.graveyard.size()];
		for(int i =0;i<this.graveyard.size();i++) {
			ids[i] = i+1;
			ranks[i] = (this.graveyard.size())-i;
		}
		
		query.postQuery(ids,this.getMap().getFilename(),(float)this.getTurn(),ranks);
		
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
