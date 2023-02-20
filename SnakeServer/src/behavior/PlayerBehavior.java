package behavior;

import java.util.List;

import core.InputMap;
import utils.AgentAction;
import utils.Snake;
import utils.items.Item;

public class PlayerBehavior implements AgentBehavior{

	AgentAction last_action = AgentAction.MOVE_DOWN;

	@Override
	public AgentAction playTurn(InputMap m, Snake s, List<Snake> agents, List<Item> items) {
		return last_action;
	}

	public void setLastAction(AgentAction last_action) {
		this.last_action = last_action;
	}
	
	public AgentAction getLastAction() {
		return this.last_action;
	}
}
