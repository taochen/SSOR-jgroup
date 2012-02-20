package org.ssor.test;

import org.ssor.test.traditional.AsynchronousClient1;

public class CentralizedTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long s = System.currentTimeMillis();
		int result;
		///if(i%2 == 0)
			//result = test.caculate(100, i);
		//else
		ITest test = new TestService();
			result = test.count(100, 1);
			long end = System.currentTimeMillis() - s;
			 System.out.print("time: "+ end + " ms\n");

	}

}
