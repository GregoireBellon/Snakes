package core.event;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import core.event.handler.EventType;

public interface Observable {
	
	public void subscribe(EventType et, PropertyChangeListener listener);
	public void removeSubscriber(EventType et, PropertyChangeListener listener);
	public void notifySubscribers(EventType et, PropertyChangeEvent e);
}
