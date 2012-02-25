package org.ssor.gcm.jgroup;

import java.util.Queue;
import java.util.Vector;

import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssor.CollectionFacade;
import org.ssor.RegionDistributionSynchronyManager;
import org.ssor.State;
import org.ssor.exception.ConfigurationException;
import org.ssor.gcm.GCMAdaptor;
import org.ssor.util.Callback;
import org.ssor.util.Util;

public class JGroupReceiver extends ReceiverAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(JGroupReceiver.class);
	private GCMAdaptor adaptor;
	private Queue<Address> cache;

	private RegionDistributionSynchronyManager regionDistributionSynchronyManager;
    // For state transfer upon view delivery
	private State.StateList states;
	/*
	 * private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 100, 3,
	 * TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100), new
	 * ThreadPoolExecutor.DiscardOldestPolicy());
	 * 
	 * private Class<?>[] clazz = new Class<?>[]{JGroupReceiver.class,
	 * org.ssor.protocol.Message.class, Message.class};
	 */
	@SuppressWarnings("unchecked")
	public JGroupReceiver(GCMAdaptor adaptor) {
		this.adaptor = adaptor;
		cache = CollectionFacade.getConcurrentQueue();

		try {
			adaptor.init(this);
		} catch (Throwable e) {
			throw new ConfigurationException(e);
		}

	}

	public byte[] getState() {
		
		while (states == null) {
			logger.error("Sleep 1 sec then get states");
			try {
				Thread.sleep((long)1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
        if (logger.isDebugEnabled()) {
        	logger.debug("Getting states");
        }
		byte[] bytes = null;
		try {
			bytes = Util.objectToByteBuffer(states);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bytes;
	}

	public void setState(byte[] bytes) {
        if (logger.isDebugEnabled()) {
        	logger.debug("Setting states");
        }
		State[] states = null;
		try {
			states = ((State.StateList) Util.objectFromByteBuffer(bytes))
					.getStates();
		} catch (Exception e) {
			e.printStackTrace();
		}

		adaptor.getGroup().setStates(states);
	}

	@Override
	public void receive(final Message msg) {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		// This does not need another thread since it never be blocked
		final org.ssor.protocol.Message message;
		try {
			message = (org.ssor.protocol.Message) Util.objectFromByteBuffer(msg
					.getBuffer());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			adaptor.processPriorReceive(message, msg.getSrc());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.print(Thread.currentThread().hashCode() + "\n");

		// ThreadPool.getThread(ReceivingThread.class, clazz, this, message,
		// msg).start();
		/*
		 * This may changed if the GCM provide unblock thread implementation
		 */

		// if(adaptor.isNeedNewThread(message))
		// new ReceivingThread(message, msg).start();
		// else
		// adaptor.receive(message, msg.getSrc());
	}

	@Override
	public void viewAccepted(View view) {
		
        if (logger.isDebugEnabled()) {
        	logger.debug("View change starts");
        }
		try {
			onViewDelivery(view.getMembers(), view.getViewId().getId());
		} catch (Throwable t) {
			t.printStackTrace();
		}
        if (logger.isErrorEnabled()) {
        	logger.error("View change finishes");
        }
	}

	public void suspect(Address address) {

	}

	private void onViewDelivery(final Vector<Address> vector, long seqno) {

		// also to guarantee view synchrony after delivery by suspend FT
		// thread

		if (cache.size() == 0) {

			final Address address = ((JGroupAdaptor) adaptor).getChannel()
					.getLocalAddress();

			if (vector.size() > 2) {
				// System.out.print(vector.get(1).hashCode() + " : " +
				// vector.get(2).hashCode() + "\n");
			}

			// Set local address
			adaptor.getGroup().setUUID_ADDR(address.hashCode());
			regionDistributionSynchronyManager = adaptor.getGroup()
					.getRegionDistributionSynchronyManager();
			regionDistributionSynchronyManager.setViewSeqno(seqno);
			if (vector.size() == 1) {
				regionDistributionSynchronyManager.recordFirst(address
						.hashCode());
				cache.addAll(vector);
			} else {

				boolean isRecord = true;
				for (Address addr : vector) {

					// Record the joined node that after itself into the view
					// synchronizer
					if (addr.hashCode() == address.hashCode())
						isRecord = false;

					cache.add(addr);
					if (!isRecord)
						adaptor.getGroup()
								.getRegionDistributionSynchronyManager()
								.recordJoin(addr.hashCode());

				}

			}

			adaptor.initView(cache, vector.size() == 1, new Callback() {

				@Override
				public Object run() {

					// Trigger another thread in order to avoid deadlock
					// cause by flush and
					// send event
					new SendAfterViewDeliveryThread().start();

					return null;

				}

			});

			if (logger.isTraceEnabled()) {
				logger.trace("No of members: " + cache.size());
			}

			return;
		}
		//regionDistributionSynchronyManager.setUnderViewDelivering(true);
		//regionDistributionSynchronyManager.tryViewDelivery();
		
		// Need different thread, otherwise if the waiting delivery of msg has sending operation
		// then it would deadlock.
		//new Thread(new Runnable() {

			//@Override
			//public void run() {

				// This may suspend this view installation, but its liveness is
				// guaranteed
				regionDistributionSynchronyManager.isViewDeliverable();
				if (vector.size() > cache.size()) {

					Address address = null;

					for (int i = cache.size(); i < vector.size(); i++) {

						address = vector.get(i);
						// Do nothing, since election would triggered by the
						// region
						// based
						// election protocol
						adaptor.getGroup()
								.getRegionDistributionSynchronyManager()
								.recordJoin(address.hashCode());
						cache.add(address);
						// System.out.print("Add member: " + address.hashCode()
						// + " ***\n");
						states = adaptor.getGroup().getServiceManager().getRegionStates();
						if (logger.isTraceEnabled()) {
							logger.trace("Add member: " + vector.lastElement());
						}
					}
				} else {

					for (Address ad : cache) {

						if (!vector.contains(ad)) {

							// ViewSynchronizationManager.record(ad.hashCode(),
							// true);

							if (logger.isTraceEnabled()) {
								logger.trace("Remove member: " + ad);
							}

							cache.remove(ad);
							// Assume only one node change within each view
							adaptor.memberLeave(ad.hashCode(), cache);

						}

					}

				}
				
				//regionDistributionSynchronyManager.releaseView();

			//}

		//}).start();

				//regionDistributionSynchronyManager.setUnderViewDelivering(false);

	}

	private class SendAfterViewDeliveryThread extends Thread {

		public SendAfterViewDeliveryThread() {
			super();
		}

		public void run() {

			adaptor.currentNodeJoin(cache);

		}
	}

}
