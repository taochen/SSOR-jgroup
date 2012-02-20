package org.ssor.gcm.jgroup;

import org.jgroups.Address;
import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssor.gcm.GenericGCMAdaptor;
import org.ssor.protocol.Message;
import org.ssor.util.Environment;
import org.ssor.util.Group;
import org.ssor.util.Util;

public class JGroupAdaptor extends GenericGCMAdaptor {

	
	private static final Logger logger = LoggerFactory
			.getLogger(JGroupAdaptor.class);
	private JChannel channel;

	protected Object lazy = new Byte[0];

	public JChannel getChannel() {
		return channel;
	}

	public JGroupAdaptor(String groupName) {
		super(groupName);
	}

	public JGroupAdaptor(String groupName, String configName, String method) {
		super(groupName);
		try {
			Class.forName(configName).getDeclaredMethod(method,
					new Class<?>[] { Group.class }).invoke(null, group);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void blockCall(Object object) {
		// TODO Auto-generated method stub

	}

	public void blockCall(Object object, Object address) {
		// TODO Auto-generated method stub

	}

	public void multicast(Message message) {

		org.jgroups.Message msg = new org.jgroups.Message(null);
		if (message.getFIFO_Scope() != null)
			msg.setScope(message.getFIFO_Scope());
		try {
			msg.setBuffer(Util.objectToByteBuffer(message));
			if (logger.isDebugEnabled()) {
				logger
						.debug("Group: "
								+ group.getName()
								+ ", size of orignial data sent, exclude the GCM properties: "
								+ msg.getBuffer().length);
				logger.debug("Group: " + group.getName()
						+ ", size of message(bytes) sent: " + msg.size());
			}
			 send(msg);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public void unicast(Message message, Object address) {

		processPriorUnicast(message);
		org.jgroups.Message msg = new org.jgroups.Message((Address) address);
		if (message.getFIFO_Scope() != null)
			msg.setScope(message.getFIFO_Scope());
		try {
			msg.setBuffer(Util.objectToByteBuffer(message));
			if (logger.isDebugEnabled()) {
				logger
						.debug("Group: "
								+ group.getName()
								+ ", size of orignial data sent, exclude the GCM properties: "
								+ msg.getBuffer().length);
				logger.debug("Group: " + group.getName()
						+ ", size of message(bytes) sent: " + msg.size());
			}
			
			 send(msg);
	
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	
	
	
	public void init(Object ra) throws Throwable {

		synchronized (lazy) {

			setUtil(JGroupUtil.getInstance());
			if (path == null) {
				channel = new JChannel(JGroupAdaptor.class
						.getProtectionDomain().getCodeSource().getLocation()
						.getFile()
						+ (isUDP ? "udp.xml" : "tcp.xml"));
			} else {
				channel = new JChannel(path);
			}
			channel.setReceiver((ReceiverAdapter) ra);

			channel.connect(group.getName());
			channel.getState(null, 5000);
			if (logger.isTraceEnabled()) {
				logger.trace("Connect to group: " + group.getName());
			}
		}

	}


	private void send(final org.jgroups.Message msg) throws ChannelNotConnectedException, ChannelClosedException {
		
		if(Environment.isNew.get() == null){
			//System.out.print("alternative: ");
			Environment.pool.execute(new Runnable(){

				@Override
				public void run() {
					try {
						channel.send(msg);
					} catch (ChannelNotConnectedException e) {
						e.printStackTrace();
					} catch (ChannelClosedException e) {
						e.printStackTrace();
					}
					
				}
				
			});
		} else	
		    channel.send(msg);
	}
	
}
