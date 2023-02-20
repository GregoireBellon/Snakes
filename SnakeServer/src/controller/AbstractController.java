package controller;

import core.Game;

public abstract class AbstractController {
	private Game game;


	public AbstractController(Game game) {
		this.game = game;
	}
	
	public AbstractController() {}

	public void restart() {
		game.pause();
		game.init();
	}

	public void step() {
		game.step();
	}
	
	public void start() {
		game.isRunning();
	}
	
	public void stop() {
		game.stop();
	}

	public void play() {
		game.init();
		game.launch();
	}

	public void setSpeed(double speed) {
		game.setSleepTimeMs((int) (game.BASE_SLEEP_TIME_MS / speed));
	}

	public Game getGame(){
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	


}	

