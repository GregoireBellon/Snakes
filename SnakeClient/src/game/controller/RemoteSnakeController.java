package game.controller;

import java.net.Socket;
import java.util.ArrayList;

import controller.AbstractController;
import core.InputMap;
import core.event.handler.EventType;
import core.requests.Connexion;
import core.requests.MapState;
import core.requests.RequestFactory;
import core.requests.WhichMap;
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
       public String map_name;
       
       private InputMap map;
       
       
       public RemoteSnakeController(String server_url, String username, String password) throws Exception {
               
               this.server_url = server_url;
               this.username = username; 
               this.password = password;
               
               
               this.initialisationNetworkRoutine();
               
//              InputMap map = game.getMap();
                                          
                              
               PanelSnakeGame panel = new PanelSnakeGame(map.getSizeX(), map.getSizeY(), map.get_walls(), map.getStart_snakes(), map.getStart_items());

               this.view_snake = new ViewSnakeGame(this, panel);
               
               PlayerControl ctrl = new PlayerControl(view_snake.getFrame(), this.server_socket);
               
               
               SnakeMovedHandler move_handler = new SnakeMovedHandler(view_snake);
               
               
               super.getGame().subscribe(EventType.MAP_CHANGED, new MapChangedHandler(this.map, view_snake));
               super.getGame().subscribe(EventType.SNAKE_MOVED, move_handler);
               
               Sender.send(server_socket, new MapState());

       }
       
       private void initialisationNetworkRoutine() throws Exception {
               System.out.println("Socket is set up");

               String[] splitted = this.server_url.split(":");

               this.server_socket = new Socket(splitted[0], Integer.parseInt(splitted[1]));

               this.router = new CustomClientRouter(new RequestFactory(), this);
               this.router.listenToSocket(this.server_socket);

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
       
       
}

