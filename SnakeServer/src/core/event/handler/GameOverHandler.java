package core.event.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import controller.CustomRouter;
import controller.RemoteServerController;
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
		for(int i =0;i<this.controller.getGame().getGraveyard().size();i++) {
			ids[i] = i+1;
			ranks[i] = (this.controller.getGame().getGraveyard().size())-i;
		}

		query.postQuery(ids,this.controller.getGame().getMap().getFilename(),(float)this.controller.getGame().getTurn(),ranks);

		
		System.out.println("Cleaning the instances of " + controller.getGame());
		this.router.getGame_affectations().replaceAll((socket, game) -> {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(game == controller.getGame()) return null;
		return game;});
		this.router.getGames().remove(controller);
				
	}
}
