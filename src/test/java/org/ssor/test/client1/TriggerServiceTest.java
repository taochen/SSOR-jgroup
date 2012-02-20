package org.ssor.test.client1;

import org.ssor.gcm.GCMAdaptor;
import org.ssor.gcm.jgroup.JGroupAdaptor;
import org.ssor.gcm.jgroup.JGroupReceiver;
import org.ssor.invocation.ProxyFactory;
import org.ssor.test.ITest;
import org.ssor.test.SSORSampleTest;
import org.ssor.test.Sharing;
import org.ssor.test.TestService;
import org.ssor.test.UndService;
import org.ssor.util.Environment;
public class TriggerServiceTest {
	
	private static final int threads = 100;
	public static long ii = 0;
	public static Integer count = 0;
	public static Object lock = new Object();
	public static ITest test;

	public static void main(String[] arg) {

		Environment.ENABLE_CHANGE_FIFO = true;
		GCMAdaptor gcm = null;


		gcm = new JGroupAdaptor("__test_cluster", "org.ssor.test.node1.UnConfigurator", SSORSampleTest.initMethod);

		gcm.setPath("D:/flush-udp.xml");
		new JGroupReceiver(gcm);
		

		test = (ITest) gcm.getGroup().getProxyFactory().get(TestService.class);
		gcm.getGroup().getProxyFactory().get(UndService.class);
		Sharing.test = test;

		//test = new TestService();
		try {

			long l = 8000;
			Thread.sleep(l);
			
			for(int i = 0; i < threads; i++)
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

			
			long s = System.currentTimeMillis();
			
		    test.countAndCaculate(100, i);;
			
			
			synchronized(lock){
				count++;
				TriggerServiceTest.ii += System.currentTimeMillis() - s;
				if(TriggerServiceTest.threads == count){
					 System.out.print("time: "+ TriggerServiceTest.ii/TriggerServiceTest.threads+ " ms\n");
				}
			}
			
		}
	}
}