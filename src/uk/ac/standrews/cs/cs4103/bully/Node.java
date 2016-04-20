package uk.ac.standrews.cs.cs4103.bully;

import java.io.PrintWriter;
import java.net.Socket;

public class Node {

	private final int uuid;
	private final int port;
	private final NodeType type;

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

	public void SendMessage(Message message) {
		try {
			Socket soc = new Socket("localhost", this.getPort());
			PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
			out.println(message.toString());
			soc.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
