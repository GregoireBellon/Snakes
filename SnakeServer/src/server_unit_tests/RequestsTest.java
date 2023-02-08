package server_unit_tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

import core.requests.Connexion;
import core.requests.Deconnexion;
import core.requests.RequestFactory;
import core.requests.response.Response;
import core.requests.response.ResponseStatus;

class RequestsTest {

	@Test
	void testConnexion() throws NoSuchFieldException {

		Connexion co = new Connexion("Jhon", "Azerty");
		byte[] content = co.fetchContent();

		Connexion retour = (Connexion) new RequestFactory().fromBytes(content);

		assert retour.getUsername().equals(co.getUsername());
		assert retour.getPassword().equals(co.getPassword());		
	}


	@Test
	void testDeconnexion() throws NoSuchFieldException {
		Deconnexion co = new Deconnexion("Jhon");
		byte[] content = co.fetchContent();

		Deconnexion retour = (Deconnexion) new RequestFactory().fromBytes(content);

		assert retour.getUsername().equals(co.getUsername());
	}

	@Test
	void testResponse() throws NoSuchFieldException {

		Response res = new Response(ResponseStatus.OK, "C ok, tout va bien $^^' / \\");

		byte[] content = res.fetchContent();
		Response retour = (Response) new RequestFactory().fromBytes(content);

		assert retour.getStatus() == res.getStatus();
		assert retour.getMessage().equals(res.getMessage());
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

		RequestFactory req = new RequestFactory();
		
		InputStream is = new ByteArrayInputStream(byte_buf.array());

		
		
		Connexion co_retour = (Connexion) req.fromSendableStream(is);

		Response res_retour = (Response) req.fromSendableStream(is);

		Deconnexion dec_retour = (Deconnexion) req.fromSendableStream(is);


		assert (co.getUsername().equals(co_retour.getUsername()) && co.getPassword().equals(co_retour.getPassword()));
		assert (res.getStatus() == res_retour.getStatus() && res.getMessage().equals(res_retour.getMessage()));
		assert (dec.getUsername().equals(dec_retour.getUsername()));

	}

}
