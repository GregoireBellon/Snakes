package server_unit_tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;

import core.Context;
import core.requests.Connexion;
import core.requests.Deconnexion;
import core.requests.MapState;
import core.requests.PlayerInput;
import core.requests.RequestFactory;
import core.requests.WhichMap;
import core.requests.response.Response;
import core.requests.response.ResponseStatus;
import utils.AgentAction;
import utils.ColorSnake;
import utils.FeaturesSnake;
import utils.Position;
import utils.items.FeaturesItem;
import utils.items.ItemType;

class RequestsTest {

	@Test
	void testConnexion() throws NoSuchFieldException {

		Connexion co = new Connexion("Jhon", "Azerty");
		
		JsonNode content = co.fetchContent();

		Connexion retour = (Connexion) new RequestFactory().fromJson(content);

		assert retour.getUsername().equals(co.getUsername());
		assert retour.getPassword().equals(co.getPassword());		
	}


	@Test
	void testDeconnexion() throws NoSuchFieldException {
		Deconnexion co = new Deconnexion("Jhon");
		JsonNode content = co.fetchContent();
		
		Deconnexion retour = (Deconnexion) new RequestFactory().fromJson(content);

		assert retour.getUsername().equals(co.getUsername());
	}

	@Test
	void testResponse() throws NoSuchFieldException {

		Response res = new Response(ResponseStatus.OK, "C ok, tout va bien $^^' / \\");

		JsonNode content = res.fetchContent();
		
//		System.out.println("Content length : " + content.length);
		
		Response retour = (Response) new RequestFactory().fromJson(content);
		
		assert retour.getStatus() == res.getStatus();
		assert retour.getMessage().equals(res.getMessage());
	}

	
	@Test
	void testPlayerInput() throws NoSuchFieldException {
		PlayerInput p = new PlayerInput(AgentAction.MOVE_LEFT);
		
		JsonNode content = p.fetchContent();
		PlayerInput new_p = (PlayerInput) new RequestFactory().fromJson(content);
		
		assert new_p.getAction() == p.getAction();
	}
	
	
	@Test
	void testWhichMapRequest() throws NoSuchFieldException {
		WhichMap m = new WhichMap();
		
		JsonNode content = m.fetchContent();
				
		WhichMap new_m = (WhichMap) new RequestFactory().fromJson(content);
		assert !new_m.isResponse();
	}
	
	@Test
	void testWhichMapResponse() throws NoSuchFieldException {
		WhichMap m = new WhichMap("Super_map");
		
		JsonNode content = m.fetchContent();
				
		WhichMap new_m = (WhichMap) new RequestFactory().fromJson(content);
		
		assert new_m.isResponse();
		assert new_m.getMapSelected().equals("Super_map");
	}
	
	@Test
	void testMapStateRequest() throws NoSuchFieldException {
		
		
		System.out.println("Hello");
		
		List<FeaturesSnake> mock_snakes = new ArrayList<FeaturesSnake>();
		List<Position> pos = new ArrayList<Position>();
		
		pos.add(new Position(10, 10));
		pos.add(new Position(10, 11));
		pos.add(new Position(10, 12));
		
		mock_snakes.add(new FeaturesSnake(pos, AgentAction.MOVE_UP, ColorSnake.Red, false, false));
		mock_snakes.add(new FeaturesSnake(pos, AgentAction.MOVE_DOWN, ColorSnake.Green, false, false));
		
		List<FeaturesItem> mock_items = new ArrayList<FeaturesItem>();
		
		
		mock_items.add(new FeaturesItem(15, 5, ItemType.BOX));
		mock_items.add(new FeaturesItem(12, 2, ItemType.APPLE));
		
		MapState ms = new MapState(new Context(mock_snakes, mock_items));
		
		JsonNode content = ms.fetchContent();
//				
		MapState new_ms = (MapState)  new RequestFactory().fromJson(content);
		
//		System.out.println(new_ms.getContext().getItems().size());
		
		assert new_ms.equals(ms);
	}
	
	@Test
	void testSeveralRequestsInOneConnexion() throws NoSuchFieldException, IOException {
		Connexion co = new Connexion("Jhon", "Azerty");
		Response res = new Response(ResponseStatus.OK, "C'est un test");
		Deconnexion dec = new Deconnexion("Jhon");


		StringBuffer buf = new StringBuffer(
				co.getSendable().length() 
				+ res.getSendable().length() 
				+ dec.getSendable().length());
		
		System.out.println(res.getSendable());

		buf.append(co.getSendable());

		System.out.println(buf.length());
		
		buf.append(res.getSendable());
		
		System.out.println(buf.length());

		buf.append(dec.getSendable());
		
		System.out.println(buf.length());

		
		RequestFactory req = new RequestFactory();
		
		InputStream is = new ByteArrayInputStream(buf.toString().getBytes());
		
		Connexion co_retour = (Connexion) req.fromSendableStream(is);

		Response res_retour = (Response) req.fromSendableStream(is);

		Deconnexion dec_retour = (Deconnexion) req.fromSendableStream(is);


		assert (co.getUsername().equals(co_retour.getUsername()) && co.getPassword().equals(co_retour.getPassword()));
		assert (res.getStatus() == res_retour.getStatus() && res.getMessage().equals(res_retour.getMessage()));
		assert (dec.getUsername().equals(dec_retour.getUsername()));
	}


	
}
