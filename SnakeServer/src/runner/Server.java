package runner;

import java.net.ServerSocket;

import controller.CustomRouter;
import core.InputMap;
import core.requests.RequestFactory;
import server_tools.CommunicationBridge;

public class Server {

	public static void main(String[] args) throws Exception {
		
		InputMap selected_map = new InputMap("./res/maps/online_aloneNoWall.lay");
		
//		ServerSnakeGame game = new ServerSnakeGame(selected_map);
		
		ServerSocket so = new ServerSocket(44_444);
		
		CustomRouter rev = new CustomRouter(new RequestFactory(), 2, 2);
		
		CommunicationBridge bridge = new CommunicationBridge(so, rev);
		
		bridge.listen();
		
//		System.out.println("creating controller");

//		RemoteServerController controller = new RemoteServerController(rev, game, so);
		
//		System.out.println("controller created");
		
//		controller.play();
				
	}
}