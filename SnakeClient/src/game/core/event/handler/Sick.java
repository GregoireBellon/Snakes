package game.core.event.handler;

import java.beans.PropertyChangeEvent;

import game.core.event.Observable;
import game.utils.EffectTypes;
import game.utils.Snake;

public class Sick extends Effect {


	public Sick(Snake snake, int nb_tours, Observable obs){
		super(snake, nb_tours, obs);
		snake.getFeaturesSnake().setSick(true);
		System.out.println("snake sick");
	}


	//réduit le temps restant d'invincibilité lorsqu'un tour passe
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(EventType.STEP.toString())) {
			if(this.getToursRestants()==0) {
				this.getSnake().getFeaturesSnake().setSick(false);
				System.out.println("Snake n'est plus sick");
				this.dereference();
			}else {				
				this.setToursRestants(getToursRestants()-1);
			}
		}
	}
	


	@Override
	public EffectTypes getType() {
		return EffectTypes.SICK;
	}

}
