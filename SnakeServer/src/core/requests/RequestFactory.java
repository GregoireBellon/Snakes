package core.requests;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.BufferUnderflowException;
import com.fasterxml.jackson.databind.JsonNode;

import core.requests.response.Response;
import request_handling.AbstractRequestFactory;
import request_handling.Request;

public class RequestFactory extends AbstractRequestFactory {

	//	Cette classe permet essentiellement de remplacer un switch case;

	@SuppressWarnings("unchecked")
	public Class<? extends Request>[] mapReqIdToClass(){
		return new Class[] {Connexion.class, Deconnexion.class, Response.class, WhichMap.class, PlayerInput.class, MapState.class};
	}

	public Request fromJson(JsonNode content) throws NoSuchFieldException{

		Class<? extends Request>[] association_map = mapReqIdToClass();

		try {
			Class<? extends Request> request_class = association_map[content.get("id").asInt()];

			try {
				
				// association du type de requête à sa classe, et création de l'objet à partie du constructeur
				Constructor<? extends Request> constructor = request_class.getConstructor(JsonNode.class);		
				return constructor.newInstance(content);

				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException  e) {

				System.err.println("Erreur lors de création de la requête de type "+request_class.getName() + " vérifiez que l'object associé au bien un "
						+ "constructeur prenant des byte[] en paramètre");
				e.printStackTrace();

				// cette exception est l'exception générique qui permet d'ignorer les requêtes non valides.
				throw new NoSuchFieldException();
				
			}
		}catch(ArrayIndexOutOfBoundsException e) {
			
			System.err.println("La requête reçue ne contient pas un id valide");	
			throw new NoSuchFieldException();
		}
	}


	public Request fromSendableStream(InputStream is) throws IOException, NoSuchFieldException {
				
		StringBuilder line  = new StringBuilder();
		
		while(true) {
			
			int c = is.read();
						
			if((char) c=='\n' || c < 0) {
				break;
			}else {
				line.append((char) c);				
			}
		}
				
//		System.out.println("Content : " + line.toString());
		
		JsonNode node = RequestFactory.mapper.readTree(line.toString());
	
		return this.fromJson(node);		
	}
	

	public Request fromSocket(Socket socket) throws IOException, NoSuchFieldException, InterruptedException {

		try {
			return this.fromSendableStream(socket.getInputStream());

		}catch(BufferUnderflowException | SocketException e) {
			//			Si le buffer ne peux plus lire de données, c'est que le socket a été fermé
			throw new InterruptedException();
		}

	}
}
