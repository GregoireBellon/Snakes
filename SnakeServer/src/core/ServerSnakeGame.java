package core;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.EndgameQuery;
import utils.MoveUtils;
import utils.Position;
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
		super.step();
	}
	
	@Override
	public void takeTurn() {
		super.takeTurn();
		
		for(Snake s : this.getAgents()) {
			if(!s.getAlive() && !this.graveyard.contains(s)) {

				this.graveyard.add(s);

				System.out.println("Dead player, adding to the graveyard");
				System.out.println(this.graveyard.size() + "/" + this.getOnlinePlayers().size() +" dead players");


			}
		}

	}
	
	@Override
	public boolean gameContinue() {
	
		boolean online_condition = graveyard.size() != this.getOnlinePlayers().size();		
		
		return super.gameContinue() && online_condition;
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

		if(this.getIsRunning()) {
			this.getAgents().add(snake);
		}
		
	}

	public void removeOnlinePlayer(Socket so) {
		Snake player_snake = this.online_players.get(so);

		this.getMap().getStart_snakes().remove(player_snake.getFeaturesSnake());
		this.getAgents().remove(player_snake);

		this.online_players.remove(so);
	}
	
	@Override
	public void initializeGame() {
		super.initializeGame();
		
		for(Snake online : online_players.values()) {
			
			Position spawn = generateSpawnPoint();
			List<Position> pos = online.getFeaturesSnake().getPositions();
			pos.clear();
			pos.add(spawn);
			
			this.getAgents().add(online);
			
		}

	}
	
	
	private int getMinDistanceFromAgent(Position pos) {
		
		int min = 1000;
		
		for(Snake agent : this.getAgents()) {
					
			int res_man = MoveUtils.manhattanDistance(pos, agent.getFeaturesSnake().getPositions().get(0));
		
			if(res_man < min) {
				min = res_man;
			}
		
		}
		
		return min;		
	}
	
	private Position generateSpawnPoint() {
		
		
		if(this.getAgents().size() == 0) {

			return new Position(2,2);

		}
		
		Position[] points = {new Position(2,2), new Position(this.getMap().getSizeX() -2, 2), new Position(2, this.getMap().getSizeY() -2), new Position(this.getMap().getSizeX() -2, this.getMap().getSizeY() -2)};
			
		int min_id = 0;
		int min_dist = 10000;
		
		for(int i = 0; i < points.length; i++) {
			
			int dist_res = getMinDistanceFromAgent(points[i]);
			
			if(dist_res < min_dist) {
				min_dist = dist_res;
				min_id = i;
			}
			
		}
		
		return points[min_id];
		
	}

}
