package utils.items;

import java.beans.PropertyChangeEvent;
import java.util.Arrays;
import java.util.List;

import behavior.searchPathAlgorithm.Target;
import core.LocalSnakeGame;
import core.event.handler.EventType;
import utils.Position;
import utils.Snake;

public abstract class Item implements Target {
	private FeaturesItem features; 
	protected LocalSnakeGame game;
	
	Item(FeaturesItem features, LocalSnakeGame game){
		this.game = game;
		this.features = features; 
	}
	
	public void effect(Snake s) {
		game.notifySubscribers(EventType.MAP_CHANGED, new PropertyChangeEvent(this, "apple", 0, 1));
	}
	
	
	public FeaturesItem getFeaturesItem(){
		return this.features;
	}
	
	@Override
	public Position getTargetPosition() {
		return new Position(this.features.getX(), this.features.getY());
	}
	
	public List<Position> getTargetPositions(){
		return Arrays.asList(this.getTargetPosition());
	}
}
