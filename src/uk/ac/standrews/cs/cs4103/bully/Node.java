package uk.ac.standrews.cs.cs4103.bully;

public class Node {
	
	private final int uuid;
	private final int port;
	
	public Node(int uuid, int port) {
		this.port = port;
		this.uuid = uuid;
	}

	public int getUuid() {
		return uuid;
	}

	public int getPort() {
		return port;
	}
}
