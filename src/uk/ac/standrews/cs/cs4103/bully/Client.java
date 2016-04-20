package uk.ac.standrews.cs.cs4103.bully;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Client extends Thread {

	private Bully bully;
	private PrintWriter out;
	private BufferedReader in;

	public Client(Bully bully) {
	}

	public void run() {
		try {
			boolean listening = true;

			try (ServerSocket serverSocket = new ServerSocket(bully.self.getPort())) {
				while (listening) {
					receive(serverSocket.accept());
				}
			} catch (IOException e) {
				System.err.println("Could not listen on port " + bully.self.getPort());
				System.exit(-1);
			}
		} catch (Exception e) {

		}
	}
	
	private void receive(Socket socket) {
		
	}
}

// System.out.println("Connection opened");
//
// String inputLine, outputLine;
//
// out = new PrintWriter(socket.getOutputStream(), true);
// in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
// while ((inputLine = in.readLine()) != null) {
// System.out.println("Received " + inputLine);
// }
//
// System.out.println("Connection closed");
// socket.close();
//
// } catch (IOException e) {
// e.printStackTrace();
// }
