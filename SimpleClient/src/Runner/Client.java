package Runner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import requestsLib.request_handling.Connexion;
import requestsLib.request_handling.Deconnexion;
import requestsLib.request_handling.Request;

public class Client {

	public static void main(String[] args) {

		Socket so; 
		
		byte[] send = {0, 2, 3 , 4};
		Request r = new Connexion("Jean", "De la fontaine");
		Request dc = new Deconnexion("Jean");
		
		try {
			
			so = new Socket("localhost", 44444);
			BufferedOutputStream message_sender = new BufferedOutputStream(so.getOutputStream());
			message_sender.write(r.getSendable());
			message_sender.flush();
			
			Thread.sleep(2000);
			
			message_sender = new BufferedOutputStream(so.getOutputStream());
			message_sender.write(dc.getSendable());
			message_sender.flush();
			so.close();			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
