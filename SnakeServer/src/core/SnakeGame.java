package core;

public abstract class SnakeGame extends Game {

	private InputMap map; 
	private InputMap start_map; 

	public SnakeGame(int max_turn, InputMap map) {
		super(max_turn);

		this.start_map = new InputMap(map);
		this.map = new InputMap(map);

	}
	
	public SnakeGame(InputMap map) {
		this(Integer.MAX_VALUE, map);
	}

	public void resetMap() {
			this.map = new InputMap(start_map);
	}

//	@Override
//	public void initializeGame();


	@Override
	public void gameOver() {
		System.out.println("Fin du jeu");
	}

	public InputMap getMap() {
		return this.map;
	}

}
