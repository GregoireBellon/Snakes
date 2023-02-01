package game.core;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.core.event.Observable;
import game.core.event.handler.EventType;

public abstract class Game implements Runnable, Observable{

	public final int BASE_SLEEP_TIME_MS =1000;
	private int SLEEP_TIME_MS;

	private Thread thread; 
	private int turn;
	private int max_turn;
	private boolean is_running;
	private Map<EventType, ArrayList<PropertyChangeListener>> listeners;
	


	public Game(int max_turn){
		this.max_turn = max_turn;
		this.SLEEP_TIME_MS = this.BASE_SLEEP_TIME_MS;
		this.listeners = new HashMap<EventType, ArrayList<PropertyChangeListener>>();
	}

	public void init() {
		turn = 0;
		initializeGame();
	}


	public void isRunning() {
		this.is_running = true;
	}


	public void launch() {
		this.is_running = true; 
		this.thread = new Thread(this);
		this.thread.run();
	}


	public void pause() {
		this.is_running = false;
	}


	public int getTurn() {
		return this.turn;
	}


	public boolean getIsRunning() {
		return this.is_running;
	}


	public void step() {

		this.turn++;

		notifySubscribers(EventType.STEP, new PropertyChangeEvent(this, EventType.STEP.toString(), this.turn-1, this.turn));

		takeTurn();


		if(!gameContinue() || this.turn>=this.max_turn) {
			System.out.println("Interruption");
			this.is_running = false;
			gameOver();
			this.thread.interrupt();
		}

	}


	public void run() {

		while(true) {
			if(is_running)
				step();
			try {
				Thread.sleep(SLEEP_TIME_MS);
			} catch (Exception e) {
				System.out.println("Erreur lors du sleep");
			}
		}

	}

	public void stop() {
		pause();
		this.thread.interrupt();
	}

	public void setSleepTimeMs(int SLEEP_TIME_MS) {
		this.SLEEP_TIME_MS = SLEEP_TIME_MS;
	}

	@Override
	public void subscribe(EventType et, PropertyChangeListener listener) {
		ArrayList<PropertyChangeListener> selected_listeners = this.listeners.get(et);
		if(selected_listeners == null) {
			selected_listeners = new ArrayList<PropertyChangeListener>();
			listeners.put(et, selected_listeners);
		}
		selected_listeners.add(listener);	
	
	}

	
	@Override
	public void removeSubscriber(EventType et, PropertyChangeListener listener) {
		ArrayList<PropertyChangeListener> selected_listeners = this.listeners.get(et);
		selected_listeners.remove(listener);
	}

	public void notifySubscribers(EventType et, PropertyChangeEvent e) {
		List<PropertyChangeListener> selected_listeners = this.listeners.get(et);

		if(selected_listeners != null)	{
			for(int i = 0 ; i < selected_listeners.size(); i++) {
				selected_listeners.get(i).propertyChange(e);
			}
		}
	}

	


	abstract public void initializeGame();

	abstract public void takeTurn();

	abstract public boolean gameContinue();

	abstract public void gameOver();
}
