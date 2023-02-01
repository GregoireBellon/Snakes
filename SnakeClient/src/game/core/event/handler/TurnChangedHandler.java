package game.core.event.handler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import game.core.event.Observable;
import game.view.ViewCommand;

public class TurnChangedHandler implements PropertyChangeListener {

	private ViewCommand view;

	public TurnChangedHandler(Observable obs, ViewCommand view){
		this.view = view; 
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {		
		view.setTurn((int) evt.getNewValue());
	}


}
