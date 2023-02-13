package utils.items;

import core.LocalSnakeGame;
import core.SnakeGame;
import core.event.handler.Sick;
import utils.Snake;

public class SickBall extends Item {

	SickBall(FeaturesItem features, LocalSnakeGame game) {
		super(features, game);
	}

	@Override
	public void effect(Snake s) {
		s.addEffect(new Sick(s, 20, game));
		super.effect(s);
	}

}
