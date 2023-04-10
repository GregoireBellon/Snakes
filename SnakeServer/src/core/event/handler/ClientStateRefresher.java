package core.event.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import core.Context;
import core.InputMap;
import core.ServerSnakeGame;
import core.requests.MapState;
import helpers.Sender;

public class ClientStateRefresher implements PropertyChangeListener {

	private ServerSnakeGame game;
	private InputMap map;


	public ClientStateRefresher(ServerSnakeGame game, InputMap map) {
		this.game = game;
		this.map = map;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		List<Socket> player_list = new ArrayList<Socket>(this.game.getOnlinePlayers().keySet());
		
		System.out.println("Refreshing the context for " + player_list.size() + " players");
		
		Context context = new Context(this.map.getStart_snakes(), this.map.getStart_items());

		for(Socket player_socket : player_list) {
			try {
				Sender.send(player_socket, new MapState(context));
			} catch (IOException e) {

				System.err.println("Erreur lors du rafraichissement du client : " + e.getMessage());
				System.err.println("Nous allons éjecter " + player_socket);
				this.game.removeOnlinePlayer(player_socket);
				
			}
		}
	}
}
