package uk.ac.standrews.cs.cs4103.bully;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Bully {
	
	int timeout;
	String config;
	
	Node self;
	ArrayList<Node> nodes;
	
	public static void main(String[] args) {
		System.out.println("Args: " + Arrays.toString(args));
		
		Bully bully = new Bully();
		bully.getArgs(args);
	}

	void getArgs(String[] args) {
		try {
			this.self = new Node(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			this.timeout = Integer.parseInt(args[2]);
			this.config = args[3];
			
			// Load node information from configuration file
			nodes = getNodesConfiguration(args[3]);
			
		} catch (Exception e) {
			System.err.println(e); // TODO: DEBUG
			
			System.err.println(new IncorrectArgumentsException().getMessage());
			System.exit(-1);
		}
		
		System.out.println(String.format("\nt/o: %d, config:%s", this.timeout, this.config));
	}

	ArrayList<Node> getNodesConfiguration(String file) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		String line;
		String[] data;
		
		Node newNode;
		while((line = reader.readLine()) != null) {
			data = line.split(",");
			newNode = new Node(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
			nodes.add(newNode);
			System.out.println(String.format("Loaded node: %d, port: %d", newNode.getUuid(), newNode.getPort()));
		}
		
		reader.close();
		return nodes;
	}

}
