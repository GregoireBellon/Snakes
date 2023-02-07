package behavior;

import java.util.List;
import java.util.Random;

import game.core.InputMap;
import game.utils.AgentAction;
import game.utils.Snake;
import game.utils.items.Item;

public class RandomBehavior implements AgentBehavior{

	
	public RandomBehavior() {}
	
	@Override
	public AgentAction playTurn(InputMap m, Snake s, List<Snake> agents, List<Item> items) {
		Random rn = new Random();
		return AgentAction.values()[rn.nextInt(0, AgentAction.values().length)];
	}
	
}