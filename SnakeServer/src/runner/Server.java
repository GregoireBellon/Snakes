package runner;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import controller.Reciever;
import core.TempRoom;

public class Server {

	public static void main(String[] args) {
		
		TempRoom tr = new TempRoom();
						
		CommunicationBridge bridge = new CommunicationBridge(44_444, Reciever.class);
		
		bridge.listen();
		
	}

}
