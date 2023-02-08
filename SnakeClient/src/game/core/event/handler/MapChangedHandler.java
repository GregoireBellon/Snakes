package game.core.event.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import game.core.FeaturesItem;
import game.core.FeaturesSnake;
import game.core.InputMap;
import game.core.SnakeGame;
import game.view.ViewSnakeGame;

public class MapChangedHandler implements PropertyChangeListener {

	private InputMap map;
	private SnakeGame game; 

	public MapChangedHandler(InputMap map) {
		this.map = map;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
			
		ArrayList<FeaturesItem> new_fi = new ArrayList<FeaturesItem>();
		ArrayList<FeaturesSnake> new_fs = new ArrayList<FeaturesSnake>();
			
		game.getItems().forEach(it -> new_fi.add(it.getFeaturesItem()));
		game.getAgents().forEach(ag -> new_fs.add(ag.getFeaturesSnake()));
			
			
		view.updateInfoGame(new_fs, new_fi);
		view.update();
	
		System.out.println("ITEM ADDED");
	}
	
}
