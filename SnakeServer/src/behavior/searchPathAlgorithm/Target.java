package behavior.searchPathAlgorithm;

import java.util.List;

import utils.Position;

public interface Target {
	public Position getTargetPosition();
	public List<Position> getTargetPositions();
}
