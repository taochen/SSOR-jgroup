package org.ssor.test.client1;

import org.ssor.gcm.GCMAdaptor;
import org.ssor.gcm.jgroup.JGroupAdaptor;
import org.ssor.gcm.jgroup.JGroupReceiver;
import org.ssor.util.Environment;

public class InitialElectionTest {

	public final static String name = "node1";
	
	public static void main(String[] arg) {
		
		Environment.ENABLE_CHANGE_FIFO = true;

		try {
			Class.forName("org.ssor.test.node1.Configurator");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		GCMAdaptor gcm = new JGroupAdaptor("__test_cluster");
		gcm.setPath("D:/flush-udp.xml");

		new JGroupReceiver(gcm);
	}
}
