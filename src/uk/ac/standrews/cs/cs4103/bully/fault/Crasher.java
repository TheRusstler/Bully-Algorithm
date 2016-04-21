package uk.ac.standrews.cs.cs4103.bully.fault;

import uk.ac.standrews.cs.cs4103.bully.Bully;

public class Crasher extends Thread {

	final double threashold = 0.2;
	final long sleep = 50;
	
	public void run() {
		while(Math.random() > threashold) {
			try {
				Thread.sleep(sleep);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Bully.logger.logInternal("Process crashed");
		System.exit(-1);
	}
}
