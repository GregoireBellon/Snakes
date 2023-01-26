package controller;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import  requestsLib.request_handling.Request;
import requestsLib.request_handling.RequestFactory;
import requestsLib.request_handling.RequestType;

public abstract class RequestHandler implements Runnable{

	protected Socket so;

	public RequestHandler(Socket so) {
		this.so = so;
	}

	public void handle(Request r) {
		Map<RequestType, FunctionRequest> routes = this.requestRoutes();

		if(routes.get(r.getType())!=null) {
			// Appel de la route
			routes.get(r.getType()).fun(r);
		}
		else {
			System.out.println("Requête générique, ignorée");
		}
	}
	

	@Override
	public void run() {
		while(true) {
			try {
				handle(RequestFactory.fromSocket(so));
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

	public abstract Map<RequestType, FunctionRequest> requestRoutes();
}
