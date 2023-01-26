package requestsLib.request_handling;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import requestsLib.request_handling.response.Response;

public class RequestFactory {

	//	Cette classe permet essentiellement de remplacer un switch case;
	public static Map<RequestType, Class<? extends Request>> mapTypeToClass(){

		Map<RequestType, Class<? extends Request>> map = new HashMap<RequestType, Class<? extends Request>>();

		map.put(RequestType.Connexion, Connexion.class);
		map.put(RequestType.GameInfo, GameInfo.class);
		map.put(RequestType.Response, Response.class);
		map.put(RequestType.Deconnexion, Deconnexion.class);

		return map;

	}


	public static Request fromBytes(byte[] content) throws NoSuchFieldException{

		RequestType type = RequestType.fromByte(content[0]);

		Map<RequestType, Class<? extends Request>> association_map = mapTypeToClass();

		try {
			
			// association du type de requête à sa classe, et création de l'objet à partie du constructeur
			
			if(association_map.containsKey(type)) {
				Constructor<? extends Request> constructor = association_map.get(type).getConstructor(byte[].class);		
				return constructor.newInstance(content);
			}
			else {
				throw new NoSuchFieldException();
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException e) {
			
			System.out.println("Erreur lors de création de la requête de type "+type + " vérifiez que l'object associé au bien un "
					+ "constructeur prenant des byte[] en paramètre");
			e.printStackTrace();
			
			
			// cette exception est l'exception générique qui permet d'ignorer les requêtes non valides.
			throw new NoSuchFieldException();
		
		}
	}


	public static Request fromSendableStream(InputStream is) throws IOException, NoSuchFieldException {

		byte[] request_size =  is.readNBytes(4);

		ByteBuffer wrapped = ByteBuffer.wrap(request_size);

		int num = wrapped.getInt();

		wrapped = ByteBuffer.allocate(num);
		wrapped.put(is.readNBytes(num));

		return RequestFactory.fromBytes(wrapped.array());		
	}

	public static Request fromSocket(Socket socket) throws IOException, NoSuchFieldException, InterruptedException {

		try {
			return RequestFactory.fromSendableStream(socket.getInputStream());

		}catch(BufferUnderflowException e) {
			//			Si le buffer ne peux plus lire de données, c'est que le socket a été fermé
			throw new InterruptedException();
		}

	}
}
