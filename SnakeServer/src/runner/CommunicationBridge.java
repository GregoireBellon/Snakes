package runner;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

import controller.Router;
import core.TempRoom;
import requestsLib.request_handling.Request;
import requestsLib.
request_handling.RequestFactory;

public class CommunicationBridge {

	private Router req_handler;
	private ServerSocket serv;
	
	CommunicationBridge(ServerSocket serv, Router req_handler){
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
			// TODO Auto-generated catch block

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
