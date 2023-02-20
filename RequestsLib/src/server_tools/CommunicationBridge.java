package server_tools;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import request_handling.Router;

public class CommunicationBridge implements Runnable {

	private Router req_handler;
	private ServerSocket serv;
	
	public CommunicationBridge(ServerSocket serv, Router req_handler){
		this.req_handler = req_handler;
		this.serv = serv;
	}

	public void listen(){
		try {
			Socket so; 
			BufferedInputStream data_in;

			System.out.println("Serveur lancé, entrée dans la boucle");

			while(true) {
				so = serv.accept();
			
//				data_in = new BufferedInputStream(so.getInputStream());
				req_handler.listenToSocket(so);
				
			}

		} catch (IOException e) {
			System.err.println("Erreur IO ");
			e.printStackTrace();

		} catch (SecurityException e) {
			System.err.println("Erreur de sécurité : ");
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		listen();
	}
}
