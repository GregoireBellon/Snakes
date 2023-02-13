package behavior.searchPathAlgorithm;

import java.util.List;

import core.InputMap;
import utils.AgentAction;

public interface SearchPathAlgorithm {

	public AgentAction optimumMove(InputMap inputMap, Target base_pos, Target objective_pos, List<? extends Target> collisions);
}
