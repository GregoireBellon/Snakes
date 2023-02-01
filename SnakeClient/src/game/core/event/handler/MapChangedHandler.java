package game.core.event.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import game.core.SnakeGame;
import game.utils.FeaturesSnake;
import game.utils.items.FeaturesItem;
import game.view.ViewSnakeGame;

public class MapChangedHandler implements PropertyChangeListener {

	private ViewSnakeGame view;
	private SnakeGame game; 

	public MapChangedHandler(ViewSnakeGame view, SnakeGame game) {
		this.view = view;
		this.game = game;
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
