package behavior;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;

import core.InputMap;
import utils.AgentAction;
import utils.Snake;
import utils.items.Item;

public class PlayerBehavior implements AgentBehavior{

	AgentAction last_action = AgentAction.MOVE_DOWN;

	public PlayerBehavior(Frame f) {	
		HashMap<Integer, AgentAction> default_map = new HashMap<Integer, AgentAction>();
		default_map.put(38, AgentAction.MOVE_UP);
		default_map.put(39, AgentAction.MOVE_RIGHT);
		default_map.put(40, AgentAction.MOVE_DOWN);
		default_map.put(37, AgentAction.MOVE_LEFT);
		
		subscribeKeyListener(f, default_map);
	}
	
	public PlayerBehavior(Frame f, HashMap<Integer, AgentAction> keymap) {
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
					last_action = pressed;
				}
			}
		};	
		f.addKeyListener(keypressed);

	}
	
	@Override
	public AgentAction playTurn(InputMap m, Snake s, List<Snake> agents, List<Item> items) {
		return last_action;
	}

}
