package game.core;

import game.controller.GameControllerSimple;

public class SimpleGame extends Game {

	private GameControllerSimple controller;
		
	public SimpleGame(int max_turn){
		super(max_turn);
		controller = new GameControllerSimple(this);
	}
	
	@Override
	public void initializeGame() {
		System.out.println("Initialisation du jeu");

	}

	@Override
	public void takeTurn() {
		System.out.println("Tour "+getTurn()+" du jeu en cours");
		
	}

	@Override
	public boolean gameContinue() {
		return true;
	}

	@Override
	public void gameOver() {
		System.out.println("Jeu terminé");
	}

	public GameControllerSimple getController() {
		return controller;
	}
}
