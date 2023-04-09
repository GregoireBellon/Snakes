package game.controller;

import java.net.ConnectException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import core.Context;
import core.event.handler.EventType;
import core.requests.Connexion;
import core.requests.MapState;
import core.requests.WhichMap;
import core.requests.response.Response;
import core.requests.response.ResponseStatus;
import request_handling.AbstractRequestFactory;
import request_handling.EndRoutine;
import request_handling.FunctionRequest;
import request_handling.Request;
import request_handling.Router;


public class CustomClientRouter extends Router {

	
	RemoteSnakeController controller;
               
       public CustomClientRouter(AbstractRequestFactory factory, RemoteSnakeController controller, EndRoutine end) {
		super(factory, end);
		this.controller = controller;
	}

       private FunctionRequest handle_connexion = (Request r, Socket soc) ->{
    	   Connexion c = (Connexion) r;
    	   
    	   System.out.println("REQUETE DE CONNEXION REÇUE");
    	   
    	   if(c.isResponse()) {
    		   
    		   if(c.isConOk()) {
    			    controller.map_name = c.getMap();
    	        	controller.map_known = true;
    		   }
    		   else {
    			   this.controller.setError(true);
    			   this.controller.setErrorMessage("Erreur de connexion : mauvais combo login / password");
    		   }

    	   }    	   
       };
       
   	private FunctionRequest handle_whichmap = (Request r, Socket soc) -> {
};

	private FunctionRequest handle_mapstate =(Request r, Socket soc) -> {
		MapState state = (MapState) r;
		
		if(!controller.isClientReady()) {
			return;
		}
		
		if(state.isResponse()) {
			
//			System.out.println("Map state : " + state);
			Context context = state.getContext();
//			System.out.println("Context recieved, size of items : " + context.getItems().size());
			
			this.controller.getMap().setStart_items(context.getItems());
			this.controller.getMap().setStart_snakes(context.getSnakes());
			
			this.controller.getGame().notifySubscribers(EventType.MAP_CHANGED, null);
			
		}
	};


	private FunctionRequest handle_response = (Request r, Socket soc) -> {
               Response res = (Response) r;
               
	};

               
       
       @Override
       public Map<Integer, FunctionRequest> requestRoutes() {
               
               Map<Integer, FunctionRequest> map = new HashMap<Integer, FunctionRequest>();
               
               map.put(Response.ID, handle_response);
               map.put(WhichMap.ID, handle_whichmap);
               map.put(MapState.ID, handle_mapstate);
               map.put(Connexion.ID, handle_connexion);
               
               return map;
       }

}
