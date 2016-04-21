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
	private final int timeout;

	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;

	public Node(int uuid, int port, NodeType type, int timeout) {
		this.port = port;
		this.uuid = uuid;
		this.type = type;
		this.timeout = timeout;
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

	public boolean elect() {
		connect();

		boolean ok = false;
		
		Bully.logger.log(String.format("Send Elect %d to %d.", Bully.self.getUuid(), getUuid()));

		writer.println(Bully.self.getUuid());
		writer.println(Message.ELECT);
		
		if(getMessage() == Message.OK) {
			ok = true;
			Bully.logger.logInternal(String.format("Received OKAY from %d.", getUuid()));
		}

		disconnect();

		return ok;
	}
	
	public void result() {
		connect();

		Bully.logger.log(String.format("Send Result %d to %d.", Bully.self.getUuid(), getUuid()));

		writer.println(Bully.self.getUuid());
		writer.println(Message.RESULT);

		disconnect();
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
			socket.setSoTimeout(timeout);
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
