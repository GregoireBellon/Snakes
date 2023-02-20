package game.view;

import javax.swing.JFrame;

import controller.AbstractController;

public abstract class GameView {
	private JFrame frame;
	private AbstractController controller;

	public GameView(AbstractController controller){
		this.controller = controller;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}


	public AbstractController getController() {
		return this.controller;
	}
}
