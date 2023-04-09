package request_handling;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public abstract class Router{


	private AbstractRequestFactory factory;
	private boolean stop;
	private EndRoutine end;

	
	public Router(AbstractRequestFactory factory, EndRoutine end) {
		this.factory = factory;
		this.stop = false;
		this.end = end;

	}
	
	public Router(AbstractRequestFactory factory) {
	this(factory, null);
	
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
					while(!stop) {

						try {							
						
						handle(factory.fromSocket(so), so);

						}catch(NullPointerException e) {
//							cette exception arrive lorsque l'objet json passe mal (par exemple lorsque le socket est rompu pendant la diffusion
							System.err.println("Sortie du router");
							stop();
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
				
				if(end!=null) {
					end.end();
				}
			}
			
	}, "ROUTER");  


		t1.start();

}
	
public void stop() {
	this.stop = true;
}

public abstract Map<Integer, FunctionRequest> requestRoutes();
}
