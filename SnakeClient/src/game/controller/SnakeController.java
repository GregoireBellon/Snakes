package game.controller;

import java.awt.Frame;
import java.util.List;

import game.core.InputMap;
import game.core.SnakeGame;
import game.core.event.handler.EventType;
import game.core.event.handler.MapChangedHandler;
import game.core.event.handler.SnakeMovedHandler;
import game.core.event.handler.TurnChangedHandler;
import game.view.PanelSnakeGame;
import game.view.ViewSnakeGame;

public class SnakeController extends AbstractController {

	private ViewSnakeGame view_snake;

	private InputMap map;


	public SnakeController(SnakeGame game) {
		super(game);

		this.map = game.getMap();

		PanelSnakeGame panel = new PanelSnakeGame(map.getSizeX(), map.getSizeY(), map.get_walls(), map.getStart_snakes(), map.getStart_items());

		this.view_snake = new ViewSnakeGame(this, panel);

		SnakeMovedHandler move_handler = new SnakeMovedHandler(view_snake);

//		game.subscribe(EventType.MAP_CHANGED, new MapChangedHandler(view_snake, game));
//		game.subscribe(EventType.SNAKE_MOVED, move_handler);
//		game.subscribe(EventType.STEP, new TurnChangedHandler(game, view_command));
		
	}

	@Override
	public void restart() {
		((SnakeGame) this.getGame()).resetMap();
		this.map = ((SnakeGame) this.getGame()).getMap();
		
		System.out.println("taille du snake de la map : "+this.map.getStart_snakes().get(0).getPositions().size());
		
		
		super.restart();
		System.out.println("Map changée");

		PanelSnakeGame panel = new PanelSnakeGame(map.getSizeX(), map.getSizeY(), map.get_walls(), map.getStart_snakes(), map.getStart_items());
//		view_snake.
		view_snake.setPannel(panel);

	}


}
