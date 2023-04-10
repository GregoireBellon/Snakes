package game.view;

import javax.swing.JFrame;

import controller.AbstractController;

public abstract class GameView extends JFrame{
	
	private static final long serialVersionUID = -1372072069890851460L;
	
	private AbstractController controller;

	public GameView(AbstractController controller){
		this.controller = controller;
	}

	public AbstractController getController() {
		return this.controller;
	}
}
