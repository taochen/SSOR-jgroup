


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

public class TestJGroupAdaptor2 extends ReceiverAdapter {

	public JChannel channel;
	public RegionDistributionSynchronyManager viewSynchronizationManager;
	
	public TestJGroupAdaptor2() {
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
	
		if(msg.getSrc().equals(channel.getLocalAddress()))
		       return;
		System.out.print("receive\n");
		try {
			Message m = new Message(msg.getSrc());
			m.setObject(viewSynchronizationManager.getViewSeqno());
			channel.send(m);
			Thread.sleep((long)8000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.exit(0);
		
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
		System.out.print("View delivered\n");
	}
	
	public static void main(String[] args){
		try {
			TestJGroupAdaptor2 a = new TestJGroupAdaptor2();
			
			try {
				Thread.sleep((long)5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Message m = new Message();
			m.setObject(a.viewSynchronizationManager.getViewSeqno());
			a.channel.send(m);
		} catch (ChannelNotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChannelClosedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
