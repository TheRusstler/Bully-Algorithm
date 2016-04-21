package uk.ac.standrews.cs.cs4103.bully.fault;

import uk.ac.standrews.cs.cs4103.bully.Bully;
import uk.ac.standrews.cs.cs4103.bully.NodeType;

public class Timing {

	static final double maxDelay = 2000;

	public static void artificialDelay() {
		
		if(Bully.self.getType() != NodeType.Timing) {
			return;
		}
		
		try {
			long delay = (long) (Math.random() * maxDelay);
			Bully.logger.logInternal("Artificial delay: " + delay);
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
