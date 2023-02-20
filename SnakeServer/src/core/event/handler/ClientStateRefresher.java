package core.event.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import core.Context;
import core.InputMap;
import core.requests.MapState;
import helpers.Sender;
import utils.Snake;

public class ClientStateRefresher implements PropertyChangeListener {

	private Map<Socket, Snake> connected_players;
	private InputMap map;


	public ClientStateRefresher(Map<Socket, Snake> connected_players, InputMap map) {
		this.connected_players = connected_players;
		this.map = map;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		List<Socket> player_list = new ArrayList<Socket>(this.connected_players.keySet());
		
		System.out.println("Refreshing the context for " + player_list.size() + " players");
		
		Context context = new Context(this.map.getStart_snakes(), this.map.getStart_items());

		for(Socket player_socket : player_list) {
			try {
				Sender.send(player_socket, new MapState(context));
			} catch (IOException e) {

				System.err.println("Error while refreshing the state to all clients : ");
				e.printStackTrace();
				
			}
		}
	}
}
