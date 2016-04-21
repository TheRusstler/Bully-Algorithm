package uk.ac.standrews.cs.cs4103.bully.fault;

public class Timing {

	final double maxDelay = 2000;
	
	public void artificialDelay() {
		try {
			Thread.sleep((long) (Math.random() * maxDelay));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
