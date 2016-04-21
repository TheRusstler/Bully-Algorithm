package uk.ac.standrews.cs.cs4103.bully.fault;

public class Omission extends Thread {
	
	final double threashold = 0.2;

	public boolean omit() {
		return Math.random() > threashold;
	}
}
