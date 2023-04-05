package utils.items;

import core.LocalSnakeGame;
import core.event.handler.Invincibility;
import utils.Snake;

public class InvincibilityBall extends Item {
	
	InvincibilityBall(FeaturesItem features, LocalSnakeGame game) {
		super(features, game);
	}

	@Override
	public void effect(Snake s) {
		s.addEffect(new Invincibility(s, 20, this.game));
		super.effect(s);
	}

}
