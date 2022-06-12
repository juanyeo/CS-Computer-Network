package project2;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WebClient implements ActionListener {
	
	private JFrame frame10;
	private JPanel panel;
	private JTextField urlField, answerField;
	private JLabel urlLabel, answerLabel;
	private JButton getButton, postButton, getImageButton;
	
	public WebClient() {
		drawClientWindow();
	}
	
	public static void main(String[] args) throws IOException {
		new WebClient();
	}

	public void drawClientWindow() {
		frame10 = new JFrame();
		frame10.setSize(700, 400);
		frame10.setTitle("Project 2: Web Client");
		panel = new JPanel();
		
		urlField = new JTextField(50);
		answerField = new JTextField(4);
		urlLabel = new JLabel("URL :  ");
		answerLabel = new JLabel("Number of Images :  ");
		getButton = new JButton("GET");
		postButton = new JButton("POST");
		getImageButton = new JButton("GET (Image)");
		
		getButton.addActionListener(this);
		postButton.addActionListener(this);
		getImageButton.addActionListener(this);
		
		panel.add(urlLabel);
		panel.add(urlField);
		panel.add(answerLabel);
		panel.add(answerField);
		panel.add(getButton);
		panel.add(postButton);
		panel.add(getImageButton);
		
		frame10.add(panel);
		frame10.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String result = "";
		JButton source = (JButton) e.getSource();
		
		try {
			if(source == getButton) {
				result = getWebContentByGet(urlField.getText(), "UTF-8", 1000);
			} else if(source == postButton) {
				String answer = "2017027265/";
				if(answerField.getText() != null) answer += answerField.getText();
				
				result = getWebContentByPost(urlField.getText(), answer, "UTF-8", 1000);
			} else if(source == getImageButton) {
				getImageContentByGet(urlField.getText(), 1000);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println(result);
	}
	
	public static String getWebContentByGet(String urlString, final String charset, int timeout) throws IOException {
		if(urlString == null || urlString.length() == 0) return null;
		
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString : ("http://" + urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		conn.setRequestProperty("User-Agent", "2017027265/JUANYEO/WEBCLIENT/COMPUTERNETWORK");
		conn.setRequestProperty("Accept", "text/html");
		conn.setConnectTimeout(timeout);
		
		try {
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) return Integer.toString(conn.getResponseCode());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		InputStream input = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
		String line = null;
		StringBuffer sb = new StringBuffer();
		
		while((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		
		if (reader != null) reader.close();
		if (conn != null) conn.disconnect();
		
		return sb.toString();
	}
	
	public static void getImageContentByGet(String urlString, int timeout) throws IOException {
		if(urlString == null || urlString.length() == 0) return;
		
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString : ("http://" + urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		conn.setRequestProperty("User-Agent", "2017027265/JUANYEO/WEBCLIENT/COMPUTERNETWORK");
		conn.setRequestProperty("Accept", "text/html");
		conn.setConnectTimeout(timeout);
		
		try {
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		InputStream input = conn.getInputStream();
		Image image = ImageIO.read(input);
		
		JFrame frame = new JFrame();
		JLabel imageLabel = new JLabel(new ImageIcon(image));
		frame.getContentPane().add(imageLabel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static String getWebContentByPost(String urlString, String data, final String charset, int timeout) throws IOException {
		System.out.println("POST");
		if(urlString == null || urlString.length() == 0) return null;
		
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString : ("http://" + urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		connection.setRequestProperty("User-Agent", "2017027265/JUANYEO/WEBCLIENT/COMPUTERNETWORK");
		connection.setRequestProperty("Accept", "text/xml");
		
		connection.setConnectTimeout(timeout);
		connection.connect();
		
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		
		byte[] content = data.getBytes("UTF-8");
		
		out.write(content);
		out.flush();
		out.close();
		
		InputStream input = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
		String line = null;
		StringBuffer sb = new StringBuffer();
		
		while((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		
		if (reader != null) reader.close();
		if (connection != null) connection.disconnect();
		
		return sb.toString();
	}
	
}
