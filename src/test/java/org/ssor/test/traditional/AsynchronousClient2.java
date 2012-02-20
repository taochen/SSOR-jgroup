package org.ssor.test.traditional;
import org.ssor.gcm.GCMAdaptor;
import org.ssor.gcm.jgroup.JGroupAdaptor;
import org.ssor.gcm.jgroup.JGroupReceiver;
import org.ssor.test.ITest;
import org.ssor.test.Replica;
import org.ssor.test.TestService;
import org.ssor.test.UndService;
import org.ssor.test.VisualizedChart;
import org.ssor.util.Environment;

public class AsynchronousClient2 {
	
	private static int threads = 10;
	public static long ii = 0;
	public static Integer count = 0;
	public static Object lock = new Object();
	
	static ITest test = null;
	public static void run(Replica replica, int threads) {
		test = replica.getTest();
		AsynchronousClient2.threads = threads;
		count = 0;
		ii = 0;
		try{
		main(null);
		}catch(Exception e){
			
		}
	}
	
	public static void main(String[] arg) throws Exception{
		

		//ProxyFactory.get(gcm.getGroup(),UndService.class);
		//System.out
				//.print(new TestService().caculate(100, 0) + " result\n");
		//test = new TestService();
		try {

			
			long l = 5000;
			//Thread.sleep(l);
			
			//startTime = System.currentTimeMillis();
			for(int i = 0; i < threads; i++)
				new ThreadTest(test, i).start();
		} catch (Throwable e) {
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

			
			long s = 0;
			int result;
			if(i%3 == 0){
				s = System.currentTimeMillis();
				result = test.work(100, i);
			}else if(i%2 == 0) {
				s = System.currentTimeMillis();
				result = test.work(100, i);
			} else {
				s = System.currentTimeMillis();
				result = test.work(100, i);
			}
			//System.out.print("1 Result: " + result + "\n");
	
			// System.out.print(i + " time: "+ (System.currentTimeMillis() - s) + " ms\n");
			/*
			if((nu = test.caculate(100, i)) == SingleApplicationLevelServiceTest.threads){		 
			    System.out.print("result: " + nu + "***\n");
			    System.out.print("time: "+ (System.currentTimeMillis() - startTime) + " ms\n");
			}
			*/
			
				long end = System.currentTimeMillis() - s;
			synchronized(lock){
				count++;
				AsynchronousClient2.ii += end;
				if(AsynchronousClient2.threads == count){
					VisualizedChart.record("Service#2 ", AsynchronousClient2.ii
							/ AsynchronousClient2.threads, String.valueOf(threads));
					 System.out.print("node#2 time: "+ AsynchronousClient2.ii/AsynchronousClient2.threads+ " ms\n");
				}
			}
			
		}
	}
}
