package tests;

import java.io.IOException;
import java.net.UnknownHostException;

import game.controller.RemoteSnakeController;
import game.core.RemoteClientSnakeGame;

public class TestRun {
	
	public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException {
		
		RemoteSnakeController controller = new RemoteSnakeController("localhost:44444", "jhon", "jhon");		
		
	}
	
}
