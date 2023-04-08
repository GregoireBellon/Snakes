package game.view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import game.controller.RemoteSnakeController;

public class ConnexionView extends JFrame {

	private static final long serialVersionUID = -4886360637794094501L;
	

	private JLabel err_message;
	private JTextField serv_url;
	private JTextField username;
	private JTextField password;
	private RemoteSnakeController controller;
	
	public ConnexionView(RemoteSnakeController controller){
		
		super("Connexion");
		
		this.controller =controller;
		
		int windowWidth = 700;
		int windowHeight = 700;
		
		this.setSize(new Dimension(windowWidth, windowHeight));
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point center_point = ge.getCenterPoint();
		
		int dx = center_point.x - windowWidth / 2;
		int dy = center_point.y - windowHeight / 2 - 350;

		JPanel content_panel = new JPanel();
		
		this.setContentPane(content_panel);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		
		this.err_message = new JLabel("");
		
		this.serv_url = new JTextField();
		this.username = new JTextField();
		this.password = new JPasswordField();
		
		this.username.setMaximumSize(new Dimension(300, 20));
		this.password.setMaximumSize(new Dimension(300, 20));
		this.serv_url.setMaximumSize(new Dimension(300, 20));
		
		JPanel form = new JPanel();
				
		form.setLayout(new BoxLayout(form, BoxLayout.PAGE_AXIS));
		
		form.add(err_message);

		form.add(new JLabel("URL du serveur (ip:port)"));
		form.add(serv_url);

		form.add(new JLabel("Username"));
		form.add(username);
		form.add(new JLabel("Password"));
		form.add(password);
		
		
		
		JButton submit = new JButton("Jouer");
		
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
					
				if(controller.setConnexion(serv_url.getText() ,username.getText(), password.getText())) {
					controller.play();					
					setVisible(false);
				}
				else{
					err_message.setText(controller.getErrorMessage());
				}
				
			}
		});		
		form.add(submit);
				
		
		panel.add(new JLabel(new ImageIcon(new ImageIcon("./res/images/greeting.png").getImage().getScaledInstance(windowHeight/2, windowHeight / 2, Image.SCALE_DEFAULT))));

		panel.add(form);
		
		content_panel.add(panel);
		
	}
	
	
	
	
	
}
