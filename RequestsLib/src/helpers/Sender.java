package helpers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import request_handling.Request;

public class Sender {
	
       public static void send(Socket so, Request r) throws IOException {
    	   
               OutputStream message_sender = so.getOutputStream();
               message_sender.write(r.getSendable().getBytes());
               message_sender.flush();
       }
}
