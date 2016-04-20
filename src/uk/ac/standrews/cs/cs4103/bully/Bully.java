package uk.ac.standrews.cs.cs4103.bully;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Bully {

	int timeout;
	boolean isInitiator = false;
	BufferedReader reader;
	PrintWriter writer;

	Node self;

	// Mapping of ports to nodes
	HashMap<Integer, Node> nodes;

	public static void main(String[] args) {
		System.out.println("Args: " + Arrays.toString(args));

		Bully bully = new Bully();
		bully.getArgs(args);
		bully.listen();
	}

	void getArgs(String[] args) {
		try {
			self = new Node(Integer.parseInt(args[0]), Integer.parseInt(args[1]), NodeType.valueOf(args[2]));
			timeout = Integer.parseInt(args[3]);
			nodes = getNodesConfiguration(args[4]);

			if (args.length > 5) {
				isInitiator = args[5].equalsIgnoreCase("Initiator");
			}

		} catch (Exception e) {
			System.err.println(e); // TODO: DEBUG

			System.err.println(new IncorrectArgumentsException().getMessage());
			System.exit(-1);
		}
	}

	HashMap<Integer, Node> getNodesConfiguration(String file) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();

		String line;
		String[] data;

		Node newNode;
		while ((line = reader.readLine()) != null) {
			data = line.split(",");
			newNode = new Node(Integer.parseInt(data[0]), Integer.parseInt(data[1]), NodeType.valueOf(data[2]));
			nodes.put(newNode.getUuid(), newNode);
			System.out.println(String.format("Loaded node: %d, port: %d", newNode.getUuid(), newNode.getPort()));
		}

		reader.close();
		return nodes;
	}

	void startElection() {
		Collection<Node> all = nodes.values();
		for (Node n : all) {
			if (n.getUuid() > self.getUuid()) {
				n.Elect();
			}
		}
	}

	void listen() {
		boolean listening = true;

		if (this.isInitiator) {
			startElection();
		}

		try (ServerSocket serverSocket = new ServerSocket(self.getPort())) {
			while (listening) {
				receive(serverSocket.accept());
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + self.getPort());
			System.exit(-1);
		}
	}

	private void receive(Socket socket) {
		try {

			System.out.println("Connection opened");

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String line = reader.readLine();
			System.out.println("Received " + line);

			System.out.println("Connection closed to node");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
