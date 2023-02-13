package utils.items;

import java.util.Random;

import core.LocalSnakeGame;
import core.SnakeGame;
import utils.EffectTypes;
import utils.Snake;

public class Box extends Item {

	Box(FeaturesItem features, LocalSnakeGame game) {
		super(features, game);
	}

	@Override
	public void effect(Snake s) {
		Random rng = new Random();
		int selected_number = rng.nextInt(0, EffectTypes.values().length);
		EffectFactory ef = new EffectFactory(s, game);
		s.addEffect(ef.createEffect(EffectTypes.values()[selected_number]));
		super.effect(s);
	}

}
