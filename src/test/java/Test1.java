import org.jgroups.ChannelException;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.util.Util;
import org.ssor.conf.ConfigurationParser;
import org.ssor.conf.ConfigurationParser.AdaptorDelegate;
import org.ssor.gcm.GCMAdaptor;
import org.ssor.gcm.jgroup.JGroupAdaptor;
import org.ssor.gcm.jgroup.JGroupReceiver;
import org.ssor.protocol.replication.RequestHeader;
import org.ssor.protocol.replication.ResponseHeader;
import org.ssor.test.ITest;
import org.ssor.test.SSORSampleTest;
import org.ssor.test.TestService;
import org.ssor.test.client1.AsynchronousClient;
import org.ssor.util.Group;



public class Test1 {
	
	private static final int threads = 100;
	public static long ii = 0;
	public static Integer count = 0;
	private static ThreadLocal<Boolean> isExecute = new ThreadLocal<Boolean>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		long s = System.currentTimeMillis();
		
		for(int i = 0;i < 1000;i++){
			new Thread(new Runnable(){

				@Override
				public void run() {
					isExecute.set(true);
					for(int i = 0;i < 1000000;i++){
						
					}
					isExecute.remove();
					
				}
				
			}).start();
		}
	
		System.out.print(System.currentTimeMillis() - s + "\n");
		/*
		ConfigurationParser.initialize(new AdaptorDelegate(){

			@Override
			public Group init(String name) {
				GCMAdaptor gcm = new JGroupAdaptor(name);
				gcm.setPath("D:/flush-udp.xml");
				new JGroupReceiver(gcm);
				return gcm.getGroup();
			}
			
		});
		
		/*
		long s = System.currentTimeMillis();
		ITest test = new TestService();
		for(int i = 0; i < 1; i++)
			test.caculate(100, i);
			
	    //count++;
		//Test1.ii += System.currentTimeMillis() - s;
		//if(Test1.threads == count){
			 System.out.print("time: "+ (System.currentTimeMillis() - s)+ " ms\n");
		//}*/
	}

	
}
