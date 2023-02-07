package behavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.core.InputMap;
import game.utils.AgentAction;
import game.utils.MoveUtils;
import game.utils.Snake;
import game.utils.items.Item;

/***
 * 
 * @author grego A random behavior that does not commit suicide
 */
public class NotThatDumbBehavior implements AgentBehavior {

	public NotThatDumbBehavior() {
	}

	@Override
	public AgentAction playTurn(InputMap m, Snake s, List<Snake> agents, List<Item> items) {
		ArrayList<AgentAction> availables_actions = new ArrayList<AgentAction>(Arrays.asList(AgentAction.values()));

		AgentAction action_selected = null;
		AgentAction action_tried;

		while (action_selected == null && availables_actions.size() > 0) {
			action_tried = availables_actions.get((int) (Math.random() * availables_actions.size()));
			System.out.println("action_tried : " + action_tried.toString());
			if (MoveUtils.isLegalMove(s.getFeaturesSnake().getPositions().get(0), action_tried, m, agents))
				action_selected = action_tried;

			availables_actions.remove(action_tried);

		}

		return action_selected == null ? s.getFeaturesSnake().getLastAction() : action_selected;
	}

}