package utils;

import java.util.List;
import behavior.searchPathAlgorithm.Target;
import core.InputMap;

public class MoveUtils {
	
public static boolean isLegalMove(Position current_pos, AgentAction move, InputMap map, List<? extends Target> agents) {
	Position next_pos = MoveUtils.calcNextPos(current_pos, move, map);
	return MoveUtils.isLegalPos(next_pos, map, agents);

}

public static Position calcNextPos(Position current_pos, AgentAction move, InputMap map) {
	Position next_pos; 
	switch(move) {
	case MOVE_UP : next_pos =  new Position(current_pos.getX(), (current_pos.getY()-1)%map.getSizeY()); break;
	case MOVE_DOWN : next_pos =  new Position(current_pos.getX(), (current_pos.getY()+1)%map.getSizeY() ); break;
	case MOVE_RIGHT : next_pos =  new Position((current_pos.getX()+1)%map.getSizeX(), current_pos.getY()); break;
	case MOVE_LEFT : next_pos =  new Position((current_pos.getX()-1)%map.getSizeX(), current_pos.getY()); break;
	default :
		next_pos = new Position(current_pos.getX(), current_pos.getY());
		break;
	}

	if(next_pos.getY() == -1 ) next_pos.setY(map.getSizeY()-1);
	if(next_pos.getX() == -1 ) next_pos.setX(map.getSizeX()-1);

	//		System.out.println("Current pos : "+current_pos + "\n"
	//				+ "Move : "+move.toString()+ "\n"
	//						+ "Next pos : " + next_pos.toString());

	return next_pos; 

}


public static Position calcNextPos(Position current_pos, AgentAction move, boolean[][] legal_pos) {
	Position next_pos; 

	int map_size_y = legal_pos[0].length;
	int map_size_x = legal_pos.length;

	switch(move) {
	case MOVE_UP : next_pos =  new Position(current_pos.getX(), (current_pos.getY()-1)%map_size_y); break;
	case MOVE_DOWN : next_pos =  new Position(current_pos.getX(), (current_pos.getY()+1)%map_size_y ); break;
	case MOVE_RIGHT : next_pos =  new Position((current_pos.getX()+1)%map_size_x, current_pos.getY()); break;
	case MOVE_LEFT : next_pos =  new Position((current_pos.getX()-1)%map_size_x, current_pos.getY()); break;
	default :
		next_pos = new Position(current_pos.getX(), current_pos.getY());
		break;
	}

	if(next_pos.getY() == -1 ) next_pos.setY(map_size_y-1);
	if(next_pos.getX() == -1 ) next_pos.setX(map_size_y-1);

	return next_pos; 
} 


public static boolean isLegalPos(Position pos, InputMap map, List<? extends Target> agents) {
	return !((map.get_walls()[pos.getX()][pos.getY()]) || (MoveUtils.isColliding(pos, map, agents)));
}


public static boolean isColliding(Position pos, InputMap map, List<? extends Target> agents) {
	for(Target  snake : agents) {
		for(Position p : snake.getTargetPositions()) {			
			if(p.equals(pos)) {
				return true;
			}
		}
	}
	return false;
}

public static int manhattanDistance(Position a, Position b) {
	return Math.abs(b.getX() - a.getX()) + Math.abs(b.getY() - a.getY());
}


}
