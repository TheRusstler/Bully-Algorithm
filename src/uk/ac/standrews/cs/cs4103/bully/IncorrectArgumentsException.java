package uk.ac.standrews.cs.cs4103.bully;

public class IncorrectArgumentsException extends Exception {
	private static final long serialVersionUID = 3582791246080108540L;

	public IncorrectArgumentsException() {
		super("Incorrect arguments.\nUsage: java -jar bully.jar uuid port timeout configuration-file");
	}
	
	public IncorrectArgumentsException(String message) {
		super(message + "\nUsage: java -jar bully.jar timeout configuration-file");
	}
}
