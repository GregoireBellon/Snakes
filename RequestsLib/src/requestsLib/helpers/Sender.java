package requestsLib.helpers;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import requestsLib.request_handling.Request;

public class Sender {
	public static void send(Socket so, Request r) throws IOException {
		
		OutputStream message_sender = so.getOutputStream();
		message_sender.write(r.getSendable());
		message_sender.flush();
//		message_sender.close();
	}
}
