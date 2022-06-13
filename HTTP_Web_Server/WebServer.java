package project1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(7010);
			
			while (true) {
				Socket connectionSocket = serverSocket.accept();
				
				// Construct an object to process the HTTP request message
				HttpRequest request = new HttpRequest(connectionSocket);
				
				// Create a new thread to process the request
				Thread thread = new Thread(request);
				thread.start();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
