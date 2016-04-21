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

import uk.ac.standrews.cs.cs4103.bully.fault.Crasher;
import uk.ac.standrews.cs.cs4103.bully.fault.Omission;
import uk.ac.standrews.cs.cs4103.bully.fault.Timing;

public class Bully {

	int timeout;
	boolean isInitiator = false;
	BufferedReader reader;
	PrintWriter writer;

	public static Node self;

	public static Logger logger;

	HashMap<Integer, Node> nodes;

	public static void main(String[] args) {
		System.out.println("Args: " + Arrays.toString(args));

		Bully bully = new Bully();
		bully.getArgs(args);
		logger = new Logger(Bully.self.getUuid());

		if (self.getType() == NodeType.Crash) {
			new Crasher().start();
		}

		bully.listen();
	}

	void getArgs(String[] args) {
		try {
			Bully.self = new Node(Integer.parseInt(args[0]), Integer.parseInt(args[1]), NodeType.valueOf(args[2]),
					this.timeout);
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
			newNode = new Node(Integer.parseInt(data[0]), Integer.parseInt(data[1]), NodeType.Normal, this.timeout);
			nodes.put(newNode.getUuid(), newNode);
			System.out.println(String.format("Loaded node: %d, port: %d", newNode.getUuid(), newNode.getPort()));
		}

		reader.close();
		return nodes;
	}

	void startElection() {
		logger.logInternal("Triggering an election.");

		boolean ok = false;
		Collection<Node> all = nodes.values();

		for (Node n : all) {
			if (n.getUuid() > self.getUuid()) {
				ok = n.elect() || ok;
			}
		}

		// No OK responses, become the new leader.
		if (ok == false) {
			logger.log("Timeout Triggered.");
			sendResult();
		}
	}

	void sendResult() {
		logger.logInternal("No response send out result.");

		Collection<Node> all = nodes.values();

		for (Node n : all) {
			// Send result to all except self
			if (n.getUuid() != self.getUuid()) {
				n.result();
			}
		}
	}

	void listen() {
		boolean listening = true;

		if (this.isInitiator) {
			startElection();
		}

		Socket client;
		try (ServerSocket serverSocket = new ServerSocket(self.getPort())) {
			while (listening) {
				client = serverSocket.accept();
				client.setSoTimeout(timeout);
				receive(client);
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + self.getPort());
			System.exit(-1);
		}
	}

	private void receive(Socket socket) {
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);

			int senderID = Integer.parseInt(reader.readLine());
			Message message = Message.valueOf(reader.readLine());

			if (message == Message.ELECT) {

				Timing.artificialDelay();

				if (!Omission.omit())
					writer.println(Message.OK);

				Bully.logger.log(String.format("Send OKAY to %d.", senderID));
				startElection();
			} else if (message == Message.RESULT) {
				Bully.logger.log(String.format("Received Result from %d.", senderID));
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		try {
			socket.close();
			reader = null;
			writer = null;

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
