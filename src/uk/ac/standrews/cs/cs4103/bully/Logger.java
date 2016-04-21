package uk.ac.standrews.cs.cs4103.bully;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private BufferedWriter writer;
	private int uuid;

	public Logger(int uuid) {
		this.uuid = uuid;
		try {
			writer = new BufferedWriter(new FileWriter(String.format("Node_%d.txt", uuid), true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void log(String s) {
		append("Algorithm: " + s);
	}
	
	public void logInternal(String s) {
		append("Internal: " + s);
	}

	private void append(String message) {
		try {
//			long l = System.nanoTime();
			String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
			String line = String.format("[%s | %d ] %s", timestamp, this.uuid, message);
			
			writer.write(line);
			System.out.println(line);
			writer.newLine();
			writer.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
