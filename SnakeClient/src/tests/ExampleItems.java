package tests;

import java.util.ArrayList;
import java.util.List;

import behavior.AgentBehavior;
import behavior.PlayerBehavior;
import game.controller.SnakeController;
import game.core.InputMap;
import game.core.SnakeGame;

public class ExampleItems {
	
	public static void main(String[] args){
		
		InputMap map;
		
		try {
			map = new InputMap("res/maps/alone.lay");
			
			List<AgentBehavior> behaviors = new ArrayList<AgentBehavior>();
			
			
			SnakeGame monjeu = new SnakeGame(Integer.MAX_VALUE, map);
			SnakeController controller = new SnakeController(monjeu);
			
			behaviors.add(new PlayerBehavior(controller.getGameFrame()));

			controller.setBehaviors(behaviors);
			controller.play();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

	}
	

}
