package requestsLib.request_handling.game_infos;

import requestsLib.request_handling.RequestType;
import requestsLib.request_handling.RespondableRequest;

public class PlayerList extends RespondableRequest{

	
	
	private List<Player> players; 
	
	private boolean is_response;
	
	public PlayerList(byte[] content) {
		
	}

	public PlayerList(List<Player> players) {
			this.players = players;
	}
	
	@Override
	public boolean isResponse() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RequestType getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
