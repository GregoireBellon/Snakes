package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import requestsLib.request_handling.Connexion;
import requestsLib.request_handling.Deconnexion;
import requestsLib.request_handling.Request;
import requestsLib.request_handling.RequestFactory;
import requestsLib.request_handling.RequestType;
import requestsLib.request_handling.game_infos.WhichMap;
import requestsLib.request_handling.response.Response;
import requestsLib.request_handling.response.ResponseStatus;

class RequestsTest {

	@Test
	void testConnexion() throws NoSuchFieldException {

		Connexion co = new Connexion("Jhon", "Azerty");
		byte[] content = co.getContent();

		Connexion retour = (Connexion) RequestFactory.fromBytes(content);

		assert retour.getUsername().equals(co.getUsername());
		assert retour.getPassword().equals(co.getPassword());		
	}


	@Test
	void testDeconnexion() throws NoSuchFieldException {
		Deconnexion co = new Deconnexion("Jhon");
		byte[] content = co.getContent();

		Deconnexion retour = (Deconnexion) RequestFactory.fromBytes(content);

		assert retour.getUsername().equals(co.getUsername());
	}


	@Test
	void testResponse() throws NoSuchFieldException {

		Response res = new Response(ResponseStatus.OK, "C ok, tout va bien $^^' / \\");

		byte[] content = res.getContent();
		Response retour = (Response) RequestFactory.fromBytes(content);

		assert retour.getStatus() == res.getStatus();
		assert retour.getMessage().equals(res.getMessage());
	}
	
	@Test
	void testWhichMap() throws NoSuchFieldException {
		WhichMap which = new WhichMap(null);
		

		byte[] content = which.getContent();
		Request retour =  RequestFactory.fromBytes(content);

		assert retour.getType() == RequestType.WhichMap;
	}
	
	@Test
	void testSeveralRequestsInOneConnexion() throws NoSuchFieldException, IOException {
		Connexion co = new Connexion("Jhon", "Azerty");
		Response res = new Response(ResponseStatus.OK, "C'est un test");
		Deconnexion dec = new Deconnexion("Jhon");


		ByteBuffer byte_buf = ByteBuffer.allocate(
				co.getSendable().length 
				+ res.getSendable().length 
				+ dec.getSendable().length);

		byte_buf.put(co.getSendable());

		byte_buf.put(res.getSendable());

		byte_buf.put(dec.getSendable());

		InputStream is = new ByteArrayInputStream(byte_buf.array());

		Connexion co_retour = (Connexion) RequestFactory.fromSendableStream(is);

		Response res_retour = (Response) RequestFactory.fromSendableStream(is);

		Deconnexion dec_retour = (Deconnexion) RequestFactory.fromSendableStream(is);


		assert (co.getUsername().equals(co_retour.getUsername()) && co.getPassword().equals(co_retour.getPassword()));
		assert (res.getStatus() == res_retour.getStatus() && res.getMessage().equals(res_retour.getMessage()));
		assert (dec.getUsername().equals(dec_retour.getUsername()));
	}

}
