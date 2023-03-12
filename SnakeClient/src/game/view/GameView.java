package game.view;

import javax.swing.JFrame;

import controller.AbstractController;

public abstract class GameView extends JFrame{
	private AbstractController controller;

	public GameView(AbstractController controller){
		this.controller = controller;
	}

	public AbstractController getController() {
		return this.controller;
	}
}
