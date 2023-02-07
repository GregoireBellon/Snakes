package game.core;

import game.core.event.handler.EventType;
import game.core.event.handler.MapChangedHandler;
import game.core.event.handler.SnakeMovedHandler;
import game.view.PanelSnakeGame;
import game.view.ViewSnakeGame;

public class RemoteGame extends Game {
	
	private InputMap map; 
	
	
	public RemoteGame() {
		super();
				
	}

	@Override
	public void initializeGame() {
		// TODO Auto-generated method stub
	}

	@Override
	public void takeTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean gameContinue() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub

	}
	
	public void setMap(InputMap map) {
		this.map = map;
	}
	
	public InputMap getMap() {
		return this.map;
	}
	
//	public updateItems(start_items) {
//	}

}
