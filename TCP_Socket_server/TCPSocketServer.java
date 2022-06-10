package TCPNetwork;

import java.io.*;
import java.net.*;

/**
 * TCP Server Class
 * @author juan
 */
public class TCPSocketServer {
	public static void main(String[] args) throws Exception {
		String clientText;
		String capitalizedText;
		// create server-side socket at port 7878
		ServerSocket serverSocket = new ServerSocket(7878);
		// print server start
		System.out.println("(Server) SERVER START");
		
		// while loop - wait for clients' request 
		int clientNumber = 1;
		while(true) {
			// wait and accept socket for contact by client
			Socket socket = serverSocket.accept();
			// print connected client number
			System.out.println("(Server) CLIENT " + clientNumber + " CONNECTED");
			
			// create input stream, read and save data from socket
			BufferedReader clientInput = 
					new BufferedReader(new InputStreamReader(socket.getInputStream()));
			clientText = clientInput.readLine();
			
			// capitalize input text
			capitalizedText = clientText.toUpperCase() + '\n';
			
			// create output stream, write capitalized text to socket
			DataOutputStream serverOutput = 
					new DataOutputStream(socket.getOutputStream());
			serverOutput.writeBytes(capitalizedText);
			
			clientNumber += 1;
		}
	}
}
