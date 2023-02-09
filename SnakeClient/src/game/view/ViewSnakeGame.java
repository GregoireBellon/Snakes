package game.view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import game.controller.AbstractController;
import game.core.FeaturesItem;
import game.core.FeaturesSnake;

public class ViewSnakeGame extends GameView {

	private PanelSnakeGame panel_game;
	
	public ViewSnakeGame(AbstractController controller, PanelSnakeGame panel_game) {
		super(controller);
		
		this.panel_game = panel_game;
		
		setFrame(new JFrame());
		getFrame().setTitle("Game");
		getFrame().setSize(new Dimension(700,700));
		Dimension windowSize = getFrame().getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point center_point = ge.getCenterPoint();
		
		int dx = center_point.x - windowSize.width / 2;
		int dy = center_point.y - windowSize.height / 2 - 350;
		
		getFrame().setLocation(dx,dy);
		getFrame().setVisible(true);
		
		if(panel_game != null) {			
			getFrame().add(this.panel_game);
			getFrame().setSize(panel_game.getSizeX() * 40 , panel_game.getSizeY()*40);
		}
		

	}
	
	public void setPannel(PanelSnakeGame panel_game) {
		getFrame().remove(this.panel_game);
		this.panel_game = panel_game;
		getFrame().add(panel_game);
		getFrame().revalidate();


	}
	
	
	public void updateInfoGame(List<FeaturesSnake> featuresSnakes , List<FeaturesItem> featuresItems) {
		this.panel_game.updateInfoGame(featuresSnakes, featuresItems);
	}
	
	public void update() {
		this.panel_game.repaint();
	}

}
