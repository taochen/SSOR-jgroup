package org.ssor.test.client2;

import java.util.UUID;

import org.ssor.gcm.GCMAdaptor;
import org.ssor.gcm.jgroup.JGroupAdaptor;
import org.ssor.gcm.jgroup.JGroupReceiver;
import org.ssor.invocation.ProxyFactory;
import org.ssor.invocation.inteceptor.Interceptor;
import org.ssor.test.ITest;
import org.ssor.test.Replica;
import org.ssor.test.Sharing;
import org.ssor.test.TestService;
import org.ssor.test.UndService;
import org.ssor.test.VisualizedChart;
import org.ssor.util.Environment;

public class AsynchronousClient {

	private static int threads = 51;
	public static long ii = 0;
	public static Integer count = 0;
	public static Object lock = new Object();
	
	static ITest test = null;

	public static void run(Replica replica, int threads) {
		
		test = replica.getTest();
		AsynchronousClient.threads = threads;
		count = 0;
		ii = 0;
		main(null);
	}

	public static void main(String[] arg) {
		//test = new Replica(true).getTest();
		//test = Sharing.test;
		// System.out
		// .print(new TestService().caculate(100, 0) + " result\n");
		// test = new TestService();
		try {

			long l = 10000;
			//Thread.sleep(l);

			// startTime = System.currentTimeMillis();
			for (int i = 0; i < threads; i++)
				new ThreadTest(test, i).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static class ThreadTest extends Thread {

		private int i;

		private ITest test;

		public ThreadTest(ITest test, int i) {
			this.i = i;
			this.test = test;
		}

		public void run() {
			
		    Interceptor.set(UUID.randomUUID().toString());
			//Interceptor.set("4cc6a198-78f5-4a56-af84-202ede18ef90");
			long s = 0;
			int result;
			try{
			if(i%3 == 0){
				s = System.currentTimeMillis();
				result = test.work(1000000000, i);
			}else if(i%2 == 0) {
				s = System.currentTimeMillis();
				result = test.work(1000000000, i);
			} else {
				s = System.currentTimeMillis();
				result = test.work(1000000000, i);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			// else
			// result = test.count(100, i);
			// System.out.print("1 Result: " + result + "\n");

			// System.out.print(i + " time: "+ (System.currentTimeMillis() - s)
			// + " ms\n");
			/*
			 * if((nu = test.caculate(100, i)) ==
			 * SingleApplicationLevelServiceTest.threads){
			 * System.out.print("result: " + nu + "***\n");
			 * System.out.print("time: "+ (System.currentTimeMillis() -
			 * startTime) + " ms\n"); }
			 */
			long end = System.currentTimeMillis() - s;

			synchronized (lock) {
				// System.out.print("node2 " + count + "\n");
				count++;
				AsynchronousClient.ii += end;
				if (AsynchronousClient.threads == count) {
					VisualizedChart.record("Service#2 ", AsynchronousClient.ii
							/ AsynchronousClient.threads, String.valueOf(threads));
					System.out.print("node#2 time: " + AsynchronousClient.ii
							/ AsynchronousClient.threads + " ms\n");
				}
			}

		}
	}
}
