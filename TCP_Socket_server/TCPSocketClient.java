package TCPNetwork;

import java.io.*;
import java.net.*;

/**
 * TCP Client Class
 * @author juan
 */
public class TCPSocketClient {
	public static void main(String[] args) throws Exception {
		String inputText;
		String modifiedText;
		
		// create input stream, read input text from the user
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
		inputText = userInput.readLine();
		
		// create client-side socket at port 7878
		// connect to server
		// local host 가 아니라면 "" 대신 서버의 IP 주소를 입력
		Socket socket = new Socket("", 7878);
		
		// create output stream, send input text to server
		DataOutputStream clientOutput = new DataOutputStream(socket.getOutputStream());
		clientOutput.writeBytes(inputText + '\n');
		// print input text
		System.out.println("(Client) TEXT TO SERVER: " + inputText);
		
		// create input stream, read the output from server
		BufferedReader serverOutput = 
				new BufferedReader(new InputStreamReader(socket.getInputStream()));
		modifiedText = serverOutput.readLine();
		// print the capitalized text from server
		System.out.println("(Client) TEXT FROM SERVER: " + modifiedText);
		
		// close socket and finish communication
		socket.close();
	}
}
