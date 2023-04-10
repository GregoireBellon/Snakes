package core.event.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import controller.CustomRouter;
import controller.RemoteServerController;
import utils.OnlineSnake;
import utils.EndgameQuery;

public class GameOverHandler implements PropertyChangeListener {

	private CustomRouter router;
	private RemoteServerController controller;


	public GameOverHandler(CustomRouter router, RemoteServerController controller) {
		this.router = router;
		this.controller = controller;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		
		System.out.println("Enregistrement des résultats :");
		EndgameQuery query = new EndgameQuery();
		int[] ids = new int[this.controller.getGame().getGraveyard().size()];
		int[] ranks = new int[this.controller.getGame().getGraveyard().size()];
		Float[] moneys = new Float[this.controller.getGame().getGraveyard().size()];
		for(int i =0;i<this.controller.getGame().getGraveyard().size();i++) {
			OnlineSnake snake = (OnlineSnake) this.controller.getGame().getGraveyard().get(i);
			System.out.println("Description du snake :" + snake.getDescription().getId());
			ids[i] = (int) snake.getDescription().getId();
			ranks[i] = (this.controller.getGame().getGraveyard().size())-i;
			moneys[i] = (float) (snake.getFeaturesSnake().getPositions().size()-1);
		}

		query.postQuery(ids,this.controller.getGame().getMap().getFilename(),
					   (float)this.controller.getGame().getTurn(),ranks,moneys);

		
		System.out.println("Cleaning the instances of " + controller.getGame());
		
		controller.getGame().stop();
		
		this.router.getGame_affectations().replaceAll((socket, game) -> {
			if(game == controller.getGame()) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return null;
			}
		return game;});
		this.router.getGames().remove(controller);
				
	}
}
