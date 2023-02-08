package runner;

import java.io.IOException;
import java.net.ServerSocket;

import controller.CustomRouter;
import core.TempRoom;
import core.requests.RequestFactory;
import server_tools.CommunicationBridge;

public class Server {

	public static void main(String[] args) throws IOException {
		
		TempRoom tr = new TempRoom();
		
		ServerSocket so = new ServerSocket(44_444);
		
		CustomRouter rev = new CustomRouter(tr, new RequestFactory());
		
		CommunicationBridge bridge = new CommunicationBridge(so, rev);
		
		bridge.listen();
	}
}