package test_launch;

import java.io.IOException;
import java.net.UnknownHostException;

import game.controller.RemoteSnakeController;
import game.core.RemoteGame;

public class Tester {

	public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException {
		
		RemoteSnakeController controller = new RemoteSnakeController(new RemoteGame(), "localhost:44444", "Jhon", "azerty");
	}

}
