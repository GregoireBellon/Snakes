package utils.items;

import core.Game;
import core.LocalSnakeGame;
import core.SnakeGame;
import core.event.handler.Effect;
import core.event.handler.Invincibility;
import core.event.handler.Sick;
import utils.EffectTypes;
import utils.Snake;

public class EffectFactory {
	private LocalSnakeGame game; 
	private Snake snake; 
	
	public EffectFactory(Snake snake, LocalSnakeGame game) {
		this.game = game; 
		this.snake = snake;
	}
	
	public Effect createEffect(EffectTypes et) {
		switch(et) {
		case SICK : return new Sick(this.snake, 20, game);
		default : return new Invincibility(snake, 20, game);
		}
	}
}
