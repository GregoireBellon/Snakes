package game.utils;

import java.util.ArrayList;


public class FeaturesSnake implements Cloneable{



	ArrayList<Position> positions;
	
	
	private AgentAction lastAction;
	
	ColorSnake colorSnake;
	
	boolean isInvincible;
	boolean isSick;
	
	
	
	
	public FeaturesSnake(ArrayList<Position> positions, AgentAction lastAction, ColorSnake colorSnake, boolean isInvincible, boolean isSick) {
		
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
		
	
	public ArrayList<Position> getPositions() {
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
	
	
	
}
