package core;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TempRoom {

	Map<String, Socket> userList;
	
	public TempRoom(){
		
		this.userList = new HashMap<String, Socket>();
		
	}
	
	public void connexion(String username, Socket so) {
		System.out.println("Connexion de "+ username);
		this.userList.put(username, so);
		System.out.println("Il y a maintenant " + this.userList.size() + " joueurs");		
	}
	
	public void deconnexion(String username) {
		System.out.println("Déonnexion de "+ username);
		this.userList.remove(username);
		System.out.println("Il y a maintenant " + this.userList.size() + " joueurs");		
	}
	
}
