package behavior;

import java.util.List;

import core.InputMap;
import utils.AgentAction;
import utils.Snake;
import utils.items.Item;

public interface AgentBehavior { //AgentBehavior est une strategie
	public AgentAction playTurn(InputMap m, Snake s, List<Snake> agents, List<Item> items);
}
