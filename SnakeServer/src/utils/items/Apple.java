package utils.items;

import java.util.Random;

import core.LocalSnakeGame;
import utils.MoveUtils;
import utils.Position;
import utils.Snake;

public class Apple extends Item {

	private LocalSnakeGame game;

	Apple(FeaturesItem features, LocalSnakeGame game) {
		super(features, game);
		this.game = game;
	}

	@Override
	public void effect(Snake s) {
		ItemFactory itf = new ItemFactory(game);


		if(s.getFeaturesSnake().isSick()) {
			game.getItems().add(itf.createItem(new FeaturesItem(this.getFeaturesItem().getX(), this.getFeaturesItem().getY(), ItemType.APPLE)));
		}
		else {		
			s.getFeaturesSnake().getPositions().add(s.getTailLastPos());



			Position pos = null;
			boolean valid_pos = false;
			Random randint = new Random();


			while(!valid_pos) {
				pos = new Position(randint.nextInt(0, game.getMap().getSizeX()), randint.nextInt(0, game.getMap().getSizeY()));
				valid_pos = MoveUtils.isLegalPos(pos, game.getMap(), game.getAgents());
			}

			game.getItems().add(itf.createItem(new FeaturesItem(pos.getX(), pos.getY(), ItemType.APPLE)));

			super.effect(s);

		}

	}
}
