package tests;

import java.util.ArrayList;
import java.util.List;

import game.controller.SnakeController;
import game.core.InputMap;
import game.core.SnakeGame;

public class ExampleAI {
	
	public static void main(String[] args){
		
		InputMap map;
		
		try {
			map = new InputMap("res/maps/AI_SHOWOFF.lay");
			
			List<AgentBehavior> behaviors = new ArrayList<AgentBehavior>();
			
			behaviors.add(new PathSearchingBehavior(new AStar()));
			
			SnakeGame monjeu = new SnakeGame(Integer.MAX_VALUE, map);
			SnakeController controller = new SnakeController(monjeu);
			controller.setBehaviors(behaviors);
			controller.play();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}
	

}
