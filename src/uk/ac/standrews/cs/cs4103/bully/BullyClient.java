package uk.ac.standrews.cs.cs4103.bully;

import java.io.*;
import java.net.Socket;

public class BullyClient extends Thread {
	
	private Socket socket = null;
	
	private PrintWriter out;
	private BufferedReader in;

	public BullyClient(Socket socket) {
		super("BullyClientThread");
		this.socket = socket;
	}

	public void run() {
		
		try {
			
			System.out.println("Connection opened");
			
			String inputLine, outputLine;
			
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Received " + inputLine);
			}
			
			System.out.println("Connection closed");
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
