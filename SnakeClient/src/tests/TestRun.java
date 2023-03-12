package tests;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import game.controller.RemoteSnakeController;
import game.core.RemoteClientSnakeGame;
import game.view.ConnexionView;

public class TestRun {
	
	public static void main(String[] args) throws Exception {
		
		
		RemoteSnakeController controller = new RemoteSnakeController();		
//		ConnexionView view = new ConnexionView(controller);
	
		controller.setConnexion("localhost:44444", "jhon", "jhon");
		
		controller.play();
		
//		view.setVisible(true);
		
		
	}

}
