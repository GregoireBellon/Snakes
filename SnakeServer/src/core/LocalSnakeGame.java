package core;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import behavior.AgentBehavior;
import behavior.NotThatDumbBehavior;
import behavior.PlayerBehavior;
import core.event.handler.EventType;
import utils.MoveUtils;
import utils.AgentAction;
import utils.FeaturesSnake;
import utils.Position;
import utils.Snake;
import utils.items.Item;
import utils.items.ItemFactory;

public class LocalSnakeGame extends SnakeGame {

	private List<Snake> agents; 
	private List<AgentBehavior> behaviors; 
	
	private List<Item> items; 
	
	public LocalSnakeGame(int max_turn, InputMap map) {
		super(max_turn, map);
		this.behaviors = new ArrayList<AgentBehavior>();

		this.agents = new ArrayList<Snake>();
		this.items = new ArrayList<Item>();

//		this.items = new ArrayList<Item>();
//		this.agents = new ArrayList<Snake>();
	}

	@Override
	public void initializeGame() {

		ItemFactory item_factory = new ItemFactory(this);
		this.getMap().getStart_items().forEach(item -> {
			items.add(item_factory.createItem(item));
		});;

		List<FeaturesSnake> snakes = this.getMap().getStart_snakes();

		for(int i = 0; i < snakes.size(); i++) {
			
//			if(i < behaviors.size()) {
//				this.agents.add(new Snake(snakes.get(i), this.behaviors.get(i), this));
//			}else
//			{
				this.agents.add(new Snake(snakes.get(i), new NotThatDumbBehavior(), this));
//			}
		}

	}

	@Override
	public void takeTurn() {

		for(Snake agent : agents) {
			
			System.out.println(agent.getBehavior());
			
			AgentAction aa = agent.play(this.getMap());
			
			System.out.println("joué : "+aa.toString());
			
			if(!agent.getAlive()) continue; 			


			if(isLegalMove(agent.getFeaturesSnake().getPositions().get(0), aa) || agent.getFeaturesSnake().isInvincible()) {
				moveAgent(agent, aa);
			}
			else {
				agent.getFeaturesSnake().setSick(true);
				agent.setAlive(false);
							
			}

			caseEffects(agent);

		}
		notifySubscribers(EventType.SNAKE_MOVED, new PropertyChangeEvent(this.getMap(), EventType.SNAKE_MOVED.toString(), 0, 1));
	}

	//détecte la case sur laquelle est l'agent et applique un effet; 
	private void caseEffects(Snake agent) {
		Position agent_pos = agent.getFeaturesSnake().getPositions().get(0);
		Item item = items.stream().filter(i -> (i.getFeaturesItem().getX() == agent_pos.getX() && i.getFeaturesItem().getY() == agent_pos.getY())).findFirst().orElse(null);

		if(item != null) {
			items.remove(item);
			this.getMap().getStart_items().remove(item.getFeaturesItem());
			item.effect(agent);
		}
	}

	private boolean isLegalMove(Position current_pos, AgentAction move) {
		return MoveUtils.isLegalMove(current_pos, move, this.getMap(), this.agents);	
		}

	private boolean isLegalPos(Position pos) {
		return MoveUtils.isLegalPos(pos, this.getMap(), this.agents);
	}

	private Position calcNextPos(Position current_pos, AgentAction move) {
		return MoveUtils.calcNextPos(current_pos, move, this.getMap());
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
		this.getMap().getStart_items().add(it.getFeaturesItem());
		this.items.add(it);
	}

}
