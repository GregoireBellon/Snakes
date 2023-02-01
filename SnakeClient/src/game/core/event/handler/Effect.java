package game.core.event.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import game.core.event.Observable;
import game.utils.EffectTypes;
import game.utils.Snake;

public abstract class Effect implements PropertyChangeListener{
	
	private int tours_restants;
	private Snake snake;
	private Observable obs; 
	
	public Effect(Snake snake, int tour_restants, Observable game) {
		this.snake = snake; 
		this.tours_restants = tour_restants;
	}
	
	public int getToursRestants() {
		return this.tours_restants;
	}
	
	public void setToursRestants(int tours_restants) {
		this.tours_restants = tours_restants;
	}
	public abstract EffectTypes getType();
	public abstract void propertyChange(PropertyChangeEvent evt);
	
	public Snake getSnake() {
		return this.snake;
	}
	
	public void dereference() {
		this.snake.removeEffect(this);
	}
}
