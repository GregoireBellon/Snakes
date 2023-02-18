package core;

import java.util.ArrayList;
import java.util.List;

import utils.FeaturesSnake;
import utils.items.FeaturesItem;

public class Context {
	
	List<FeaturesSnake> snakes;
	List<FeaturesItem> items;
	
	public Context(List<FeaturesSnake> snakes, List<FeaturesItem> items){
		this.snakes = snakes;
		this.items = items;
	}

	public List<FeaturesSnake> getSnakes() {
		return snakes;
	}

	public List<FeaturesItem> getItems() {
		return items;
	}
	
	

}
