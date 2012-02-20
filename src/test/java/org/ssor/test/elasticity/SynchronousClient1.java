package org.ssor.test.elasticity;

import org.ssor.test.ITest;
import org.ssor.test.Sharing;

public class SynchronousClient1 {

	private static final int numberOfReuqest = 50;

	private static int no = 0;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VisualizedChart.numberOfRound = numberOfReuqest;
		ITest test = Sharing.test;
		for (int i = 0; i < numberOfReuqest; i++) {
			no++;
			long s = System.currentTimeMillis();
			//System.out.print("sent request#" + (no-1) + "\n");
			test.count(100, 100);
			long end = System.currentTimeMillis() - s;
			
			VisualizedChart.record("Service#1 ", end, String.valueOf(no));
			//System.out.print("request#" + no + " time: " + end + " ms\n");
			//System.out.print("finish request#" + (no-1) + "\n");
		}

	}
}
