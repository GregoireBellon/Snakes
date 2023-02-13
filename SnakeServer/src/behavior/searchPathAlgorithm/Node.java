package behavior.searchPathAlgorithm;

import utils.AgentAction;
import utils.Position;

public class Node {
	public Position pos;

	public Node parent;
//	Node child;

	public int h_cost;
	public int t_dist;

	public AgentAction last_move;

	public Node(Position pos, int h_cost, int t_dist) {
		this.pos = pos;
		this.parent = null;
		this.h_cost = h_cost;
		this.t_dist = t_dist;
//		this.child
	}

	public Node(Position pos, Node parent, int h_cost, int t_dist) {
		this.pos = pos;
		this.parent = parent;

		this.h_cost = h_cost;
		this.t_dist = t_dist;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return this.parent;
	}

	
	public void setLastMove(AgentAction last_move) {
		this.last_move = last_move;
	}
//	public void setChuld(Node child) {
//		this.child = child;
//	}

}
