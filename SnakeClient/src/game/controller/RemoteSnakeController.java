package game.controller;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import controller.AbstractController;
import core.InputMap;
import core.event.handler.EventType;
import core.requests.Connexion;
import core.requests.MapState;
import core.requests.RequestFactory;
import core.requests.WhichMap;
import core.requests.response.Response;
import core.requests.response.ResponseStatus;
import game.core.RemoteClientSnakeGame;
import game.core.event.handler.MapChangedHandler;
import game.core.event.handler.SnakeMovedHandler;
import game.view.PanelSnakeGame;
import game.view.ViewSnakeGame;
import helpers.Sender;
import utils.FeaturesSnake;
import utils.items.FeaturesItem;

public class RemoteSnakeController extends AbstractController {

       private ViewSnakeGame view_snake;
       private CustomClientRouter router;
       private Socket server_socket;
       
       private String server_url;
       private String username;
       private String password;
       
       public boolean map_known = false;
       private boolean auth;
       public String map_name;
       
       private InputMap map;
       
       
       
       public RemoteSnakeController() {
    	   this.auth = false;
       }
       
       public RemoteSnakeController(String server_url, String username, String password) throws Exception {
               
    	   this.setConnexion(server_url, username, password);
               
       }
       
       public boolean setConnexion(String server_url, String username, String password) {
           
    	   this.server_url = server_url;
           this.username = username; 
           this.password = password;

	   		String[] splitted = this.server_url.split(":");

            try {
				
            	this.server_socket = new Socket(splitted[0], Integer.parseInt(splitted[1]));
				
			} catch (NumberFormatException | IOException e) {
				System.err.println("Serveur inaccessible");
				e.printStackTrace();
				return false;
			}

            if(!testConnexion()) {
            	return false;
            }

            this.router = new CustomClientRouter(new RequestFactory(), this);
            this.router.listenToSocket(this.server_socket);
            
			try {
				this.initialisationNetworkRoutine();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

            return true;
            
       }
       
       public boolean testConnexion() {
    	   try {
    		   Sender.send(this.server_socket, new Response(ResponseStatus.Other, "CONN TEST"));
    		   return true;
    	   }catch(IOException e) {
    		   return false;
    	   }
       }
       
       private void initialisationNetworkRoutine() throws Exception {
    	   

               Sender.send(server_socket, new WhichMap());   
               
               while(!this.map_known) {
            	   Thread.sleep(500);    
               }
               
               this.map = new InputMap("res/maps/" + this.map_name);
               
              
               this.map.setStart_items(new ArrayList<FeaturesItem>());
               this.map.setStart_snakes(new ArrayList<FeaturesSnake>());
               
               super.setGame(new RemoteClientSnakeGame(this.map));      
               
               Sender.send(server_socket, new Connexion(username, password));
               
       }
       
       public InputMap getMap() {
		return this.map;
	}
       
       @Override
    public void play() {
           
    	   try {
    		   
           
//         InputMap map = game.getMap();
                                        
          PanelSnakeGame panel = new PanelSnakeGame(map.getSizeX(), map.getSizeY(), map.get_walls(), map.getStart_snakes(), map.getStart_items());

          this.view_snake = new ViewSnakeGame(this, panel);
          
          PlayerControl ctrl = new PlayerControl(view_snake, this.server_socket);
                    
          SnakeMovedHandler move_handler = new SnakeMovedHandler(view_snake);
          
          super.getGame().subscribe(EventType.MAP_CHANGED, new MapChangedHandler(this.map, view_snake));
          super.getGame().subscribe(EventType.SNAKE_MOVED, move_handler);
          
          Sender.send(this.server_socket, new MapState());
          System.out.println("url: " + this.server_url);
          System.out.println("username: "+this.username);
          System.out.println("pass: "+this.password);
    	   } catch (Exception e) {
    		   e.printStackTrace();
    		   return;
    	   }
    	   
    	   this.view_snake.setVisible(true);

    	   super.play();
    }
       
       
}

