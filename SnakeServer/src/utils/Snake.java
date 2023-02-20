package utils;

import java.util.HashMap;
import java.util.List;

import behavior.AgentBehavior;
import behavior.searchPathAlgorithm.Target;
import core.InputMap;
import core.LocalSnakeGame;
import core.event.handler.Effect;
import core.event.handler.EventType;

public class Snake implements Target{

	private AgentBehavior behavior;	

	private boolean alive;

	private Position tail_last_pos; 

	private FeaturesSnake features; 

	HashMap<EffectTypes, Effect> current_effects;

	private LocalSnakeGame game;


	public Snake(FeaturesSnake fs, AgentBehavior behavior, LocalSnakeGame game){
		this.features = fs; 
		this.behavior = behavior;
		this.alive = true; 
		this.tail_last_pos = fs.positions.get(fs.positions.size()-1); 
		this.current_effects = new HashMap<EffectTypes, Effect>();
		this.game = game; 

	}

	public FeaturesSnake getFeaturesSnake() {
		return this.features;
	}


	public AgentAction play(InputMap map) {
		return behavior.playTurn(map,this, game.getAgents(), game.getItems());

	}

	public boolean getAlive() {
		return alive;
	}


	public void setAlive(boolean a) {
		alive = a; 
	}

	public void move(AgentAction move, Position next_pos) {
		features.setLastAction(move);


		List<Position> pos =features.getPositions();

		this.tail_last_pos = pos.get(pos.size()-1); 

//		Position ancienne_pos; 
		for(int i = pos.size()-1; i > 0; i--) {
			pos.set(i, pos.get(i-1));
		}

		System.out.println("Etat serpent : ");
		features.getPositions().forEach(p -> {System.out.println("X : "+p.getX() + "; Y : "+p.getY());});
		System.out.println("Last tail :  X : "+tail_last_pos.getX() + ", Y : "+tail_last_pos.getY());
		System.out.println("snake size : " + features.getPositions().size());
		System.out.println("Invincible : "+features.isInvincible());


		pos.set(0, next_pos);		
	}

	public Position getTailLastPos(){
		return this.tail_last_pos;
	}

	public void addEffect(Effect e) {
		if(current_effects.containsKey(e.getType())) {
			current_effects.get(e.getType()).setToursRestants(e.getToursRestants());
		}
		else {
			game.subscribe(EventType.STEP, e);
			this.current_effects.put(e.getType(), e);
		}
	}

	public void removeEffect(Effect e) {
		this.current_effects.remove(e.getType());
		this.game.removeSubscriber(EventType.STEP, e);
	}

	@Override
	public Position getTargetPosition() {
		return getFeaturesSnake().getPositions().get(0);
	}

	@Override
	public List<Position> getTargetPositions() {
		return this.getFeaturesSnake().getPositions();
	}
	
	public AgentBehavior getBehavior() {
		return this.behavior;
	}
}