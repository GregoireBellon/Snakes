package behavior;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import utils.AgentAction;

public class LocalPlayerBehavior extends PlayerBehavior{

	public LocalPlayerBehavior(Frame f) {	
		HashMap<Integer, AgentAction> default_map = new HashMap<Integer, AgentAction>();
		default_map.put(38, AgentAction.MOVE_UP);
		default_map.put(39, AgentAction.MOVE_RIGHT);
		default_map.put(40, AgentAction.MOVE_DOWN);
		default_map.put(37, AgentAction.MOVE_LEFT);
		
		subscribeKeyListener(f, default_map);
	}
	
	public LocalPlayerBehavior(Frame f, HashMap<Integer, AgentAction> keymap) {
		subscribeKeyListener(f, keymap);
	}

	
	private void subscribeKeyListener(Frame f, HashMap<Integer, AgentAction> keymap) {
		KeyListener keypressed = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				
				AgentAction pressed = keymap.get(e.getKeyCode());
				
				if(pressed != null) {
					setLastAction(pressed);;
				}
			}
		};	
		f.addKeyListener(keypressed);

	}
	
}
