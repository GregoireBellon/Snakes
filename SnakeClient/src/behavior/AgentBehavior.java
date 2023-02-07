package behavior;

import java.util.List;

import game.core.InputMap;
import game.utils.AgentAction;
import game.utils.Snake;
import game.utils.items.Item;

public interface AgentBehavior { //AgentBehavior est une strategie
	public AgentAction playTurn(InputMap m, Snake s, List<Snake> agents, List<Item> items);
}
