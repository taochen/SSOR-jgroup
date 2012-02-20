package org.ssor.test;

import org.ssor.gcm.GCMAdaptor;
import org.ssor.gcm.jgroup.JGroupAdaptor;
import org.ssor.gcm.jgroup.JGroupReceiver;
import org.ssor.test.traditional.ProxyFactory;
import org.ssor.test.traditional.TestJGroupAdaptor;
import org.ssor.util.Environment;

public class Replica {

	ITest test;
	IUnd iund;
	public Replica() {
		test = new TestService();
    }
	public Replica(boolean isSSOR) {
		super();
		if (isSSOR) {
			Environment.ENABLE_CHANGE_FIFO = false;

			GCMAdaptor gcm = new JGroupAdaptor("__test_cluster",
					"org.ssor.test.Configurator", SSORSampleTest.initMethod);
			
			gcm.setPath(Replica.class.getProtectionDomain()
		            .getCodeSource().getLocation() + "flush-udp.xml");
			new JGroupReceiver(gcm);

			test = (ITest) gcm.getGroup().getProxyFactory().get(
					TestService.class);
			iund = (IUnd)gcm.getGroup().getProxyFactory().get(UndService.class);
		} else {
			ProxyFactory pf = new ProxyFactory();
			TestJGroupAdaptor gcm = new TestJGroupAdaptor(pf);

			test = (ITest) pf.get(gcm, TestService.class);

			try {
				pf.setMethod(ITest.class.getDeclaredMethod("count",
						new Class[] { Integer.TYPE, Integer.TYPE }));
				pf.setMethod(ITest.class.getDeclaredMethod("caculate",
						new Class[] { Integer.TYPE, Integer.TYPE }));
				pf.setMethod(ITest.class.getDeclaredMethod("work", new Class[] {
						Integer.TYPE, Integer.TYPE }));
				pf.setMethod(ITest.class.getDeclaredMethod("work1",
						new Class[] { Integer.TYPE, Integer.TYPE }));
				pf.setMethod(ITest.class.getDeclaredMethod("countAndCaculate",
						new Class[] { Integer.TYPE, Integer.TYPE }));
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public ITest getTest() {
		return test;
	}
	public IUnd getIund() {
		return iund;
	}
	public static void main(String[] args) {
		new Replica(true);

	}

}
