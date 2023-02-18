package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class FeaturesSnake implements Cloneable{

	List<Position> positions;
	
	
	private AgentAction lastAction;
	
	ColorSnake colorSnake;
	
	boolean isInvincible;
	boolean isSick;
	
	
	
	
	public FeaturesSnake(List<Position> positions, AgentAction lastAction, ColorSnake colorSnake, boolean isInvincible, boolean isSick) {
		
		this.positions = positions;
		this.colorSnake = colorSnake;
		this.lastAction = lastAction;
		
		this.isInvincible = isInvincible;
		
		this.isSick = isSick;
		
		
	}
	
	public FeaturesSnake(FeaturesSnake snake) {
		this.lastAction = snake.lastAction;
		this.colorSnake = snake.colorSnake;
		this.isInvincible = snake.isInvincible;
		this.isSick = snake.isSick;
		
		this.positions = new ArrayList<Position>();

		for(Position position : snake.positions) {
			this.positions.add(new Position(position));
		}
		
	}
		
	
	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}




	public ColorSnake getColorSnake() {
		return colorSnake;
	}


	public void setColorSnake(ColorSnake colorSnake) {
		this.colorSnake = colorSnake;
	}


	public boolean isInvincible() {
		return isInvincible;
	}


	public void setInvincible(boolean isInvincible) {
		this.isInvincible = isInvincible;
	}


	public boolean isSick() {
		return isSick;
	}


	public void setSick(boolean isSick) {
		this.isSick = isSick;
	}


	public AgentAction getLastAction() {
		return lastAction;
	}


	public void setLastAction(AgentAction lastAction) {
		this.lastAction = lastAction;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof FeaturesSnake)) {
			return false;
		}
		
		FeaturesSnake converted = (FeaturesSnake) obj;
		
		if(converted.isInvincible() != this.isInvincible())
			return false; 
		if(converted.isSick() != this.isSick())
			return false; 
		if(converted.getColorSnake() != this.getColorSnake())
			return false; 
		if(converted.getLastAction() != this.getLastAction())
			return false; 
		
		if(this.positions.size() != converted.positions.size())
			return false;
		
		ListIterator<Position> converted_it = converted.positions.listIterator();
		ListIterator<Position> pos_it = this.positions.listIterator();
		
		while(converted_it.hasNext() && pos_it.hasNext()) {
			if(!(converted_it.next().equals(pos_it.next()))){
				return false; 
			}
		}
		
		return true;
	}
	
	
}
