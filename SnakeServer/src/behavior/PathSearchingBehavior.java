package behavior;

import java.util.List;

import behavior.searchPathAlgorithm.SearchPathAlgorithm;
import core.InputMap;
import utils.AgentAction;
import utils.Snake;
import utils.items.Item;

public class PathSearchingBehavior implements AgentBehavior{

	
	private SearchPathAlgorithm selected_algorithm;
	private AgentBehavior backupBehavior;
	
	
	public PathSearchingBehavior(SearchPathAlgorithm selected_algorithm) {
		this.selected_algorithm = selected_algorithm;
		this.backupBehavior = new NotThatDumbBehavior();
	}
	
	
	public PathSearchingBehavior(SearchPathAlgorithm selected_algorithm, AgentBehavior backupBehavior) {
		this.selected_algorithm = selected_algorithm;
		this.backupBehavior = backupBehavior;
	}
	
	@Override
	public AgentAction playTurn(InputMap m, Snake s, List<Snake> agents, List<Item> items) {
		
		AgentAction action_selected = null;
		
		
		if(items.size() > 0) {			
			action_selected = selected_algorithm.optimumMove(m, s, items.get(0), agents);
			System.out.println("returned : " + action_selected);
		}
		if(action_selected == null) {
			action_selected = this.backupBehavior.playTurn(m, s, agents, items);
		}
		
		return action_selected;
	}
	
}