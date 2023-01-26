package runner;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

import controller.RequestHandler;
import core.TempRoom;
import requestsLib.request_handling.Request;
import requestsLib.
request_handling.RequestFactory;

public class CommunicationBridge {

	private int port; 
	private Class<? extends RequestHandler> req_handler;
	
	CommunicationBridge(int port, Class<? extends RequestHandler> req_handler){
		this.port = port; 
		this.req_handler = req_handler;
	}

	public void listen(){
		try {
			ServerSocket serv = new ServerSocket(this.port);
			Socket so; 
			BufferedInputStream data_in;

			System.out.println("Serveur lancé, entrée dans la boucle");

			while(true) {
				so = serv.accept();
				data_in = new BufferedInputStream(so.getInputStream());
				
				RequestHandler handler_object = this.req_handler.getConstructor(TempRoom.class, Socket.class).newInstance(new TempRoom(), so);

				
				Thread t = new Thread(handler_object);
				t.run();
				
//				try {
//					Request r = RequestFactory.fromBytes(data_in.readAllBytes());
//					this.cb.handle(r, so); 
//
//				} catch (NoSuchFieldException e) {
//
//					System.out.println("Recieved request of unknown type");
//
//				}
				
//				so.close();			
			}

			//			serv.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
