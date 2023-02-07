package game.core.event.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import game.core.InputMap;
import game.utils.FeaturesSnake;
import game.utils.items.FeaturesItem;
import game.view.ViewSnakeGame;

public class MapChangedHandler implements PropertyChangeListener {

	private InputMap map; 
	private ViewSnakeGame view;
	
	public MapChangedHandler(ViewSnakeGame view, InputMap map) {
		this.map = map; 
		this.view = view;
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
			
		ArrayList<FeaturesItem> new_fi = map.getStart_items();
		ArrayList<FeaturesSnake> new_fs = map.getStart_snakes();
			
		view.updateInfoGame(new_fs, new_fi);
		view.update();
	
	}
	
}
