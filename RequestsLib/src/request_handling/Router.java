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
				try {
					while(true) {

						try {							
						
						handle(factory.fromSocket(so), so);

						}catch(NullPointerException e) {
//							cette exception arrive lorsque l'objet json passe mal (par exemple lorsque le socket est rompu pendant la diffusion
							break;
						}
					}

				} catch (NoSuchFieldException | IOException e) {
					System.out.println("Erreur de lecture de la requête de : "+so.getInetAddress());
					e.printStackTrace();

				} catch (InterruptedException e) {
					System.out.println("Le socket a été rompu : connexion terminée !");

				}finally {
					try {
						so.close();
					}catch(IOException e) {}
				}
			}
	});  


		t1.start();

}

public abstract Map<Integer, FunctionRequest> requestRoutes();
}
