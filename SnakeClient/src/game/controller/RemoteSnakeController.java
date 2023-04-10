package game.controller;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import controller.AbstractController;
import core.InputMap;
import core.event.handler.EventType;
import core.requests.Connexion;
import core.requests.MapState;
import core.requests.RequestFactory;
import core.requests.response.Response;
import core.requests.response.ResponseStatus;
import game.core.RemoteClientSnakeGame;
import game.core.event.handler.MapChangedHandler;
import game.core.event.handler.SnakeMovedHandler;
import game.view.PanelSnakeGame;
import game.view.ViewSnakeGame;
import helpers.Sender;
import request_handling.EndRoutine;
import utils.FeaturesSnake;
import utils.items.FeaturesItem;

public class RemoteSnakeController extends AbstractController {

       private ViewSnakeGame view_snake;

	private CustomClientRouter router;
       private Socket server_socket;
       
       private String server_url;
       private String username;
       private String password;

       private InputMap map;
       
       private boolean client_ready;
             
       public boolean map_known;
       
//       C'est la façon la plus simple de gérer l'erreur sans casser tout le système
       private boolean error;
	private String error_message;
      
       
       
       
       public String map_name;
       
       
       
       
       
       public RemoteSnakeController() {
    	   
    	   this.client_ready = false;
    	   this.map_known = false;
    	   this.error = false;
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
				
			} catch (IOException e) {
				this.error_message = "Serveur inaccessible"; 
				return false;
			} catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
				this.error_message = "Adresse non valide"; 
				return false;
			}

            if(!testConnexion()) {
            	return false;
            }
            
            EndRoutine end = new EndRoutine() {
				@Override
				public void end() {
	            	  getGame().stop();
	            	  getViewSnake().dispose();

	            	  System.exit(0);
				}
			};

            this.router = new CustomClientRouter(new RequestFactory(), this, end);
            this.router.listenToSocket(this.server_socket);
            
			try {
				this.initialisationNetworkRoutine();
			} catch (Exception e) {
				this.error_message = e.getMessage();
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
       
//       Input map renvoit une exception de type Exception....
       private void initialisationNetworkRoutine() throws Exception {
    	   

//               Sender.send(server_socket, new WhichMap());   
               Sender.send(server_socket, new Connexion(username, password));
           
               while(!this.map_known) {
            	   
//            	   System.out.println("Waiting to know the map...");
            	   if(error) {
            		   throw new ConnectException(this.error_message);
            	   }
            	   try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					break;
				}    
               }
               
               this.map = new InputMap("res/maps/" + this.map_name);
               
              
               this.map.setStart_items(new ArrayList<FeaturesItem>());
               this.map.setStart_snakes(new ArrayList<FeaturesSnake>());
               
               super.setGame(new RemoteClientSnakeGame(this.map));      
               
               this.client_ready = true;
               
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
          
    	   } catch (Exception e) {
    		   e.printStackTrace();
    		   return;
    	   }
    	   
    	   this.view_snake.setVisible(true);
    	   
    	   System.out.println("VIEW SNAKE SHOULD BE VISIBLE");

    	   super.play();
    }

	public boolean isClientReady() {
		return client_ready;
	}
       
    public void setError(boolean error) {
		this.error = error;
	}

	public void setErrorMessage(String error_message) {
		this.error_message = error_message;
	}
	
	public String getErrorMessage() {
		return this.error_message;
	}

    public ViewSnakeGame getViewSnake() {
		return view_snake;
	}

}

