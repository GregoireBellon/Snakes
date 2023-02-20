package tests;

import game.controller.RemoteSnakeController;
import game.core.RemoteClientSnakeGame;

public class TestRun {
	
	public static void main(String[] args) throws Exception {
		
		RemoteSnakeController controller = new RemoteSnakeController("localhost:44444", "jhon", "jhon");		
		
	}
	
}
