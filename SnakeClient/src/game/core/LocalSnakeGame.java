package game.core;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import core.server_game.utils.AgentAction;
import game.core.event.handler.EventType;
import game.utils.MoveUtils;
import game.utils.Position;

public class LocalSnakeGame extends SnakeGame {

	private List<Snake> agents; 
	private List<AgentBehavior> behaviors; 

	private InputMap map; 
	private InputMap start_map; 

	private List<Item> items; 

	public LocalSnakeGame(int max_turn, InputMap map) {
		super(max_turn);

		this.start_map = new InputMap(map);
		this.map = new InputMap(map);

		this.items = new ArrayList<Item>();

		this.agents = new ArrayList<Snake>();
		this.behaviors = new ArrayList<AgentBehavior>();
	}

	public void resetMap() {
			this.map = new InputMap(start_map);
	}

	@Override
	public void initializeGame() {
		this.agents = new ArrayList<Snake>();
		this.items = new ArrayList<Item>();

		ItemFactory item_factory = new ItemFactory(this);
		this.map.getStart_items().forEach(item -> {
			items.add(item_factory.createItem(item));
		});;

		ArrayList<FeaturesSnake> snakes = this.map.getStart_snakes();

		for(int i = 0; i < snakes.size(); i++) {

			if(i < behaviors.size()) {
				this.agents.add(new Snake(snakes.get(i), this.behaviors.get(i), this));
			}else
			{
				this.agents.add(new Snake(snakes.get(i), new RandomBehavior(), this));
			}
		}
		System.out.println("Game initialized");
	}

	@Override
	public void takeTurn() {

		for(Snake agent : agents) {
			AgentAction aa = agent.play(map);
			//			System.out.println("joué : "+aa.toString());
			if(!agent.getAlive()) continue; 			


			if(isLegalMove(agent.getFeaturesSnake().getPositions().get(0), aa) || agent.getFeaturesSnake().isInvincible()) {
				moveAgent(agent, aa);
			}
			else {
				agent.getFeaturesSnake().setSick(true);
				agent.setAlive(false);
				System.out.println("illegal move");
			}

			caseEffects(agent);

		}
		notifySubscribers(EventType.SNAKE_MOVED, new PropertyChangeEvent(map, EventType.SNAKE_MOVED.toString(), 0, 1));

	}

	//détecte la case sur laquelle est l'agent et applique un effet; 
	private void caseEffects(Snake agent) {
		Position agent_pos = agent.getFeaturesSnake().getPositions().get(0);
		Item item = items.stream().filter(i -> (i.getFeaturesItem().getX() == agent_pos.getX() && i.getFeaturesItem().getY() == agent_pos.getY())).findFirst().orElse(null);

		if(item != null) {
			items.remove(item);
			map.getStart_items().remove(item.getFeaturesItem());
			item.effect(agent);
		}
	}

	private boolean isLegalMove(Position current_pos, AgentAction move) {
		return MoveUtils.isLegalMove(current_pos, move, this.map, this.agents);	
		}

	private boolean isLegalPos(Position pos) {
		return MoveUtils.isLegalPos(pos, this.map, this.agents);
	}

	private Position calcNextPos(Position current_pos, AgentAction move) {
		return MoveUtils.calcNextPos(current_pos, move, this.map);
	}

	private void moveAgent(Snake agent, AgentAction move) {
		agent.move(move, calcNextPos(agent.getFeaturesSnake().getPositions().get(0), move));
	}

	@Override
	public boolean gameContinue() {
		for(Snake agent : this.agents) {
			if(agent.getAlive()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void gameOver() {
		System.out.println("Fin du jeu");
	}

	public InputMap getMap() {
		return this.map;
	}

	public List<AgentBehavior> getBehaviors() {
		return this.behaviors;
	}
	
	public void setBehaviors(List<AgentBehavior> behaviors) {
		this.behaviors = behaviors;
	}
	
	public List<Snake> getAgents() {
		return agents;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public void addItem(Item it) {
		this.map.getStart_items().add(it.getFeaturesItem());
		this.items.add(it);
	}

}
