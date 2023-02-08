package request_handling;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public abstract class Router{

	
	private AbstractRequestFactory factory;
	

	public Router(AbstractRequestFactory factory) {
		this.factory = factory;
	}
	
	
	public void handle(Request r, Socket so) {
		
		Map<Integer, FunctionRequest> routes = this.requestRoutes();

		if(routes.get(r.getID())!=null) {
			// Appel de la route
			routes.get(r.getID()).fun(r, so);
		}
		else {
			System.out.println("Requête générique, ignorée");
		}
	}


	public void listenToSocket(Socket so) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {

						handle(factory.fromSocket(so), so);
						
					} catch (NoSuchFieldException | IOException e) {
						System.out.println("Erreur de lecture de la requête de : "+so.getInetAddress());
						e.printStackTrace();
						break;

					} catch (InterruptedException e) {
						System.out.println("Le socket a été rompu : connexion terminée !");
						try {
							so.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						return;
					}
				}
			}
		});  
		
	
		t1.start();

	}

	public abstract Map<Integer, FunctionRequest> requestRoutes();
}
