package game.core.event.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import game.view.ViewSnakeGame;

public class SnakeMovedHandler implements PropertyChangeListener {

	private ViewSnakeGame view;

	public SnakeMovedHandler(ViewSnakeGame view) {
		this.view = view;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(EventType.SNAKE_MOVED.toString())) {
			view.update();
		}
	}

}
