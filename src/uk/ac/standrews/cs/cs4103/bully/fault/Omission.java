package uk.ac.standrews.cs.cs4103.bully.fault;

import uk.ac.standrews.cs.cs4103.bully.Bully;
import uk.ac.standrews.cs.cs4103.bully.NodeType;

public class Omission extends Thread {
	
	static final double threashold = 0.2;

	public static boolean omit() {
		
		if(Bully.self.getType() != NodeType.Omission) {
			return false;
		}
		
		
		boolean result = Math.random() < threashold;
		
		if(result) {
			Bully.logger.logInternal("Omission.");
		}
		return result;
	}
}
