package utils;

import behavior.AgentBehavior;
import core.LocalSnakeGame;

public class OnlineSnake extends Snake {
	
	private UserDescription description;

	public OnlineSnake(FeaturesSnake fs, AgentBehavior behavior, LocalSnakeGame game, UserDescription description) {
		super(fs, behavior, game);
		// TODO Auto-generated constructor stub
		this.description = description;
	}

	public UserDescription getDescription() {
		return description;
	}

	public void setDescription(UserDescription description) {
		this.description = description;
	}
	
	


}
