package tests;

import game.controller.RemoteSnakeController;
import game.view.ConnexionView;

public class TestRun {
	
	public static void main(String[] args) throws Exception {
		
		
		RemoteSnakeController controller = new RemoteSnakeController();		

//		ConnexionView view = new ConnexionView(controller);
//
//		view.setVisible(true);
		
//		ConnexionView view = new ConnexionView(controller);

		controller.setConnexion("localhost:44444","laura_jones@mail.fr", "laulau");
//		controller.setConnexion("localhost:44444","jean_leroi@mail.fr", "jeanjean");
		
		controller.play();
		

//		view.setVisible(true);

		
		
	}

}
