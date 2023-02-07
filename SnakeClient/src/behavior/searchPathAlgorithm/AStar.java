package behavior.searchPathAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import game.core.InputMap;
import game.utils.AgentAction;
import game.utils.MoveUtils;
import game.utils.Position;

public class AStar implements SearchPathAlgorithm {

	private boolean[][] exploredNodes;
	private Target objective_pos;
	private Target base_pos;
	private InputMap map;
	
	private List<? extends Target> collisions;
	private List<Node> sorted_nodes; // la meilleure est à l'index 0

	public AStar() {
	}

	@Override
	public AgentAction optimumMove(InputMap inputMap, Target base_pos, Target objective_pos, List<? extends Target> collisions) {

		
		System.out.println("Looking for optimum move : ");
		System.out.println("base_pos, :" + base_pos.getTargetPosition());
		System.out.println("objective_pos, " + objective_pos.getTargetPosition());
		
		this.collisions = collisions;
		
		this.base_pos = base_pos;
		
		this.sorted_nodes = new ArrayList<Node>();
		
		this.exploredNodes = new boolean[inputMap.getSizeX()][inputMap.getSizeY()];

		this.objective_pos = objective_pos;
		
		this.map = inputMap;
		
		for(boolean[] a : exploredNodes) {
			Arrays.fill(a, false);
		}

		
		Node current = new Node(base_pos.getTargetPosition(), 0, 0);
		this.sorted_nodes.add(current);
		
		return findRoute(current);
		
	}

	private AgentAction findRoute(Node base_node) {
		
		boolean objective_found = false;
		
		
		Node current_node = base_node;
		
		
		while(!objective_found) {
			
			this.exploredNodes[current_node.pos.getX()][current_node.pos.getY()] = true;

			
			// Cas d'arrêt
			if(current_node.pos.equals(this.objective_pos.getTargetPosition())) {
				System.out.println("on objective");
				objective_found = true;
				break;
			}
			
			
			// On explore chaque sens			
			for(AgentAction move : AgentAction.values()) {
				Position possible_next_pos = MoveUtils.calcNextPos(current_node.pos, move, this.map);
				Node possible_node = new Node(possible_next_pos,current_node ,MoveUtils.manhattanDistance(possible_next_pos, this.objective_pos.getTargetPosition()), current_node.t_dist+1);
				possible_node.setLastMove(move);
				this.addNode(possible_node);
			}
			
//			System.out.println("Etat des nodes : ");
//			
//			this.sorted_nodes.forEach(n -> {System.out.println("Pos : "+n.pos + ", score : " + (n.h_cost + n.t_dist));});
			
			current_node = getFirstUnexploredNode();
			
			if(current_node == null) {
				return null;
			}
		}
		
		return traceFirstMove(current_node);
	}
	
	
	
	private Node getFirstUnexploredNode() {
		ListIterator<Node> it = this.sorted_nodes.listIterator();
		while(it.hasNext()) {
			Node n =  it.next();
			if(!this.exploredNodes[n.pos.getX()][n.pos.getY()]) {
				return n;
			}
		}
		return null;
	}
	
	
	private AgentAction traceFirstMove(Node node) {
		Node traced_node = node;
		while(traced_node.getParent() != null && !traced_node.getParent().pos.equals(this.base_pos.getTargetPosition())){
			traced_node = traced_node.getParent();
		}
		
		return traced_node.last_move;
	}
	
	
//	Ajoute un noeud en gardant la liste triée
	private boolean addNode(Node added_node) {
		System.out.println("Tying to add the position " + added_node.pos);
		System.out.println("Explored ? : " + this.exploredNodes[added_node.pos.getX()][added_node.pos.getY()]);
		System.out.println("Legal ? : ");
		
		if(!this.exploredNodes[added_node.pos.getX()][added_node.pos.getY()] && MoveUtils.isLegalPos(added_node.pos, this.map, collisions)) {
			for(int i = 0; i < this.sorted_nodes.size(); i++) {
				
				Node item_getted = this.sorted_nodes.get(i);
				
				if(((added_node.h_cost + added_node.t_dist) <  (item_getted.h_cost + item_getted.t_dist)) 
						|| i == this.sorted_nodes.size()-1) {
					
					this.sorted_nodes.add(i, added_node);		
					return true;
				}
				
			}
			
		}
		return false;
	}
	

}
