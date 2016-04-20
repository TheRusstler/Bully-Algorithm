package uk.ac.standrews.cs.cs4103.bully;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Node {

	private final int uuid;
	private final int port;
	private final NodeType type;

	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;

	public Node(int uuid, int port, NodeType type) {
		this.port = port;
		this.uuid = uuid;
		this.type = type;
	}

	public int getUuid() {
		return uuid;
	}

	public int getPort() {
		return port;
	}

	public NodeType getType() {
		return type;
	}

	public boolean Elect() {
		connect();

		boolean ok = false;

		writer.println(Message.ELECT);
		
		if (getMessage() == Message.OK)
			ok = true;

		disconnect();

		return ok;
	}

	private Message getMessage() {
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				return Message.valueOf(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void connect() {
		try {
			socket = new Socket("localhost", this.getPort());
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void disconnect() {
		try {
			socket.close();
			socket = null;
			writer = null;
			reader = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
