package org.ssor.test;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.ssor.test.Replica;
import org.ssor.test.VisualizedChart;

public class RefactorTest {


	/*
	 * Uncomment the following for corresponding setup
	 */
	public static final String initMethod = "initStrongSequentialConsistency";
	//public static final String initMethod = "initCausalAndStrongSequentialConsistency";
	//public static final String initMethod = "initStrongSequentialConsistencyForThreeRegions";
	//public static final String initMethod = "initSequentialConsistency";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final List<Replica> replicas = new LinkedList<Replica>();
		for(int i = 0; i < 1; i++)
			replicas.add(new Replica(true));
		
		System.gc();
	

		
		System.out.print(" round attempt to start, we wait for 10 secs\n");
		try {
			Thread.sleep((long)5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		new ThreadTest(new Run() {

			@Override
			public void run() {
				try {
					Thread.sleep((long)2500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*for(int i = 0; i < 1; i++)
					new Replica(true);*/
			}

		}).start();
		
		new ThreadTest(new Run() {

			@Override
			public void run() {
				// MetaServiceTest1.main(null);
				org.ssor.test.client1.AsynchronousClient.run(replicas.get(0), 20);
			}

		}).start();

		new ThreadTest(new Run() {

			@Override
			public void run() {
				// MetaServiceTest2.main(null);
				org.ssor.test.client2.AsynchronousClient.run(replicas.get(0), 20);
			}

		}).start();

		new ThreadTest(new Run() {

			@Override
			public void run() {
				// MetaServiceTest3.main(null);
				org.ssor.test.client3.AsynchronousClient.run(replicas.get(0), 20);
			}

		}).start();

	}

	private static class ThreadTest extends Thread {

		Run run;

		public ThreadTest(Run run) {
			super();
			this.run = run;
			// TODO Auto-generated constructor stub
		}

		public void run() {
			run.run();
		}
	}

	private static interface Run {
		void run();
	}

}
