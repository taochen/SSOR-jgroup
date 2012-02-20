package org.ssor.test.traditional;

import java.util.Iterator;
import java.util.Map;

import org.jgroups.ChannelException;
import org.jgroups.Header;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.protocols.SEQUENCER.SequencerHeader;
import org.ssor.protocol.replication.RequestHeader;
import org.ssor.util.Util;

public class TestJGroupAdaptor extends ReceiverAdapter {

	private JChannel channel;
	ProxyFactory pf;
	public static void main(String[] args){
		new TestJGroupAdaptor(null);
	}
	
	public TestJGroupAdaptor(ProxyFactory pf) {
		super();
		try {
			channel = new JChannel("D:/sequencer.xml");
			channel.setReceiver(this);
			channel.connect("test_cluster");
		} catch (ChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.pf=pf;
	}


	@Override
	public void receive(final Message msg) {
		/*
		Iterator<Map.Entry<Short,Header>> itr = msg.getHeaders().entrySet().iterator();
		itr.next();
		itr.next();
		System.out.print(((SequencerHeader)itr.next().getValue()).getSeqno() + "\n");
		*/
		//synchronized(this){
		final org.ssor.protocol.Message message;
		try {
			message = (org.ssor.protocol.Message) Util.objectFromByteBuffer(msg
					.getBuffer());
			//System.out.print(((Object[])message.getBody())[1] + "******\n");
			if(!pf.notify(message)){
				
				String full = ((TestHeader)message.getHeader()).getService();
				String method = full.substring(full.lastIndexOf(".") + 1);
				pf.invoke(full.replace("." + method, ""), method,(Object[]) message.getBody());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//}
		
	}
	
	public void send(org.ssor.protocol.Message message){
		
		org.jgroups.Message msg = new org.jgroups.Message(null);
	
		try {
			msg.setBuffer(Util.objectToByteBuffer(message));
			//channel.send(null, null, Util.objectToByteBuffer(message));
			channel.send(msg);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}
