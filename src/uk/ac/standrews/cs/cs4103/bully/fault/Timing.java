package uk.ac.standrews.cs.cs4103.bully.fault;

public class Timing {

	static final double maxDelay = 2000;
	
	public static void artificialDelay() {
		try {
			Thread.sleep((long) (Math.random() * maxDelay));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
