package game.view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.List;

import controller.AbstractController;
import utils.FeaturesSnake;
import utils.items.FeaturesItem;

public class ViewSnakeGame extends GameView {

	private PanelSnakeGame panel_game;
	
	public ViewSnakeGame(AbstractController controller, PanelSnakeGame panel_game) {
		super(controller);
		
		this.panel_game = panel_game;
		
		this.setTitle("Game");
		this.setSize(new Dimension(700,700));
		Dimension windowSize = this.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point center_point = ge.getCenterPoint();
		
		int dx = center_point.x - windowSize.width / 2;
		int dy = center_point.y - windowSize.height / 2 - 350;
		
		this.setLocation(dx,dy);
		
		if(panel_game != null) {			
			this.add(this.panel_game);
			this.setSize(panel_game.getSizeX() * 40 , panel_game.getSizeY()*40);
		}
		
	}
	
	public void setPannel(PanelSnakeGame panel_game) {
		this.remove(this.panel_game);
		this.panel_game = panel_game;
		this.add(panel_game);
		this.revalidate();


	}
	
	
	public void updateInfoGame(List<FeaturesSnake> featuresSnakes , List<FeaturesItem> featuresItems) {
		this.panel_game.updateInfoGame(featuresSnakes, featuresItems);
	}
	
	public void update() {
		this.panel_game.repaint();
		this.repaint();
	}

}
