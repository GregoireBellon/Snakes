package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import game.controller.SnakeController;
import game.core.InputMap;
import game.core.SnakeGame;
import game.utils.AgentAction;

public class ExampleTwoPlayers {

	public static void main(String[] args) {
		
		InputMap map;
		
		try {
			map = new InputMap("res/maps/arenaNoWall.lay");
			
			List<AgentBehavior> behaviors = new ArrayList<AgentBehavior>();
			
			
			SnakeGame monjeu = new SnakeGame(Integer.MAX_VALUE, map);
			SnakeController controller = new SnakeController(monjeu);

			HashMap<Integer, AgentAction> alt_keyset = new HashMap<Integer, AgentAction>();
			alt_keyset.put(90, AgentAction.MOVE_UP);
			alt_keyset.put(68, AgentAction.MOVE_RIGHT);
			alt_keyset.put(83, AgentAction.MOVE_DOWN);
			alt_keyset.put(81, AgentAction.MOVE_LEFT);
			
			behaviors.add(new PlayerBehavior(controller.getGameFrame()));
			behaviors.add(new PlayerBehavior(controller.getGameFrame(), alt_keyset));
			controller.setBehaviors(behaviors);
			
			controller.play();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		

	}

}
