package utils.items;

import core.LocalSnakeGame;

public class ItemFactory {

	private LocalSnakeGame game;
	
	public ItemFactory(LocalSnakeGame game) {
		this.game = game;
	}
	
	public Item createItem(FeaturesItem fi) {
		switch(fi.getItemType()){
		case APPLE : return new Apple(fi, game);
		case SICK_BALL : return new SickBall(fi, game);
		case INVINCIBILITY_BALL : return new InvincibilityBall(fi, game);
		default : return new Box(fi, game);
		}
	}
	
}
