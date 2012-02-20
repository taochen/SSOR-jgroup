

import java.util.Iterator;
import java.util.Map;

import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.Header;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.protocols.SEQUENCER.SequencerHeader;
import org.ssor.RegionDistributionSynchronyManager;
import org.ssor.protocol.replication.RequestHeader;
import org.ssor.util.Util;

public class TestJGroupAdaptor1 extends ReceiverAdapter {

	private JChannel channel;
	int i = 0;
	private RegionDistributionSynchronyManager viewSynchronizationManager;
	
	public TestJGroupAdaptor1() {
		super();
		viewSynchronizationManager = new RegionDistributionSynchronyManager(null);
		try {
			channel = new JChannel("D:/flush-udp.xml");
			channel.setReceiver(this);
			channel.connect("test_cluster");
		} catch (ChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void receive(final Message msg) {
		
		System.out.print(msg.getObject() + "receive\n");
		//viewSynchronizationManager.cacheMsgOnView((Long)msg.getObject());
		try {
			if(i==1){
				
				Thread.sleep((long)5000);
			}
			channel.send(new Message(msg.getSrc()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.print("finish\n");
		//viewSynchronizationManager.releaseMsgOnView();
		i++;
		
	}
	
	public void send(org.ssor.protocol.Message message){
		
		org.jgroups.Message msg = new org.jgroups.Message(null);
	
		try {
			//msg.setBuffer(Util.objectToByteBuffer(message));
			//channel.send(null, null, Util.objectToByteBuffer(message));
			channel.send(msg);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	

	@Override
	public void viewAccepted(View view) {
		viewSynchronizationManager.isViewDeliverable();
		System.out.print(view.getViewId() + "View delivered\n");
	}
	
	public static void main(String[] a){
		new TestJGroupAdaptor1();
	}
}
