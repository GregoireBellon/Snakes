package core;

import java.net.Socket;

public class Player {

//	private Snake associated_snake;
	
	private String username;
	private Socket player_socket;
	
	Player(String username, Socket player_socket){
		this.username = username;
		this.player_socket = player_socket;
	}
	
}
