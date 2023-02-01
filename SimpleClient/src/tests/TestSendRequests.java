package tests;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import requestsLib.request_handling.Connexion;
import requestsLib.request_handling.Deconnexion;

class TestSendRequests {

	Socket so; 
	BufferedOutputStream message_sender;

	@BeforeEach
	void setupSender() throws UnknownHostException, IOException {
		this.so = new Socket("localhost", 44444);
		this.message_sender = new BufferedOutputStream(so.getOutputStream());
	}
		
	@AfterEach
	void terminateSender() throws IOException, InterruptedException
	{
		this.message_sender.close();
		this.so.close();
		Thread.sleep(3000);
	}
	
	@Test
	void testConnexion() throws IOException {
		Connexion r = new Connexion("Jhon", "azerty");
		
		message_sender.write(r.getSendable());
		message_sender.flush();
	}
	
	@Test
	void testDeconnexion() throws IOException {
		
		Deconnexion r = new Deconnexion("Jhon");
		message_sender.write(r.getSendable());
		message_sender.flush();
		
	}
	
	@Test
	void testSeveralRequests() throws IOException, InterruptedException {
		Connexion r = new Connexion("Jhon", "azerty");
		Thread.sleep(200);
		Deconnexion r2 = new Deconnexion("Jhon");
		
		message_sender.write(r.getSendable());
		message_sender.write(r2.getSendable());
		message_sender.flush();
	}

}
