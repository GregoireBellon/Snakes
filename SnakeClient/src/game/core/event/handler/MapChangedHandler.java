package game.core.event.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import game.core.FeaturesItem;
import game.core.FeaturesSnake;
import game.core.InputMap;
import game.core.SnakeGame;
import game.view.ViewSnakeGame;

public class MapChangedHandler implements PropertyChangeListener {

	private InputMap map;
	private ViewSnakeGame view;

	public MapChangedHandler(InputMap map, ViewSnakeGame view) {
		this.map = map;
		this.view = view;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
			
		List<FeaturesItem> new_fi = map.getStart_items();
		List<FeaturesSnake> new_fs = map.getStart_snakes();
			
		view.updateInfoGame(new_fs, new_fi);
		view.update();
	
		System.out.println("ITEM ADDED");
	}
	
}
