package game.controller;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import core.requests.PlayerInput;
import core.server_game.utils.AgentAction;
import helpers.Sender;

public class PlayerControl {
	
	AgentAction last_action = AgentAction.MOVE_DOWN;
	Socket server_socket;
	
	
	public PlayerControl(Frame f, Socket server_socket) {	
		
		HashMap<Integer, AgentAction> default_map = new HashMap<Integer, AgentAction>();
		default_map.put(38, AgentAction.MOVE_UP);
		default_map.put(39, AgentAction.MOVE_RIGHT);
		default_map.put(40, AgentAction.MOVE_DOWN);
		default_map.put(37, AgentAction.MOVE_LEFT);
		
		subscribeKeyListener(f, default_map);
		this.server_socket = server_socket;
		
	}
	
	public PlayerControl(Frame f, HashMap<Integer, AgentAction> keymap, Socket server_socket) {
		subscribeKeyListener(f, keymap);
		this.server_socket = server_socket;
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
					try {
						Sender.send(server_socket, new PlayerInput(pressed));		
					}catch(IOException err) {
						System.err.println("Error while trying to communicate the player input");
						err.printStackTrace();
					}
				}
			}
		};	
		f.addKeyListener(keypressed);

	}
	}
