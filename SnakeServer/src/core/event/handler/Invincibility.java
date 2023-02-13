package core.event.handler;

import java.beans.PropertyChangeEvent;

import core.event.Observable;
import utils.EffectTypes;
import utils.Snake;

public class Invincibility extends Effect {


	public Invincibility(Snake snake, int nb_tours, Observable obs){
		super(snake, nb_tours, obs);
		snake.getFeaturesSnake().setInvincible(true);
		System.out.println("snake invincible");
	}


	//réduit le temps restant d'invincibilité lorsqu'un tour passe
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(EventType.STEP.toString())) {
			if(this.getToursRestants()==0) {
				this.getSnake().getFeaturesSnake().setInvincible(false);
				System.out.println("Snake n'est plus invincible");
				this.dereference();
			}else {				
				this.setToursRestants(getToursRestants()-1);
			}
		}
	}
	


	@Override
	public EffectTypes getType() {
		return EffectTypes.INVINCIBLE;
	}

}
