package game.core.event.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import core.InputMap;
import game.view.ViewSnakeGame;
import utils.FeaturesSnake;
import utils.items.FeaturesItem;

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
		
		System.out.println("Nb of items : " + new_fi.size());
		System.out.println("Nb of snakes : " + new_fs.size());
			
		view.updateInfoGame(new_fs, new_fi);
		view.update();
	
		System.out.println("ITEM ADDED");
	}
}
