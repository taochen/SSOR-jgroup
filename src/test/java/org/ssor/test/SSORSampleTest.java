package org.ssor.test;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class SSORSampleTest {

	/*
	 * Uncomment the following for corresponding setup
	 */
	//public static final String initMethod = "initStrongSequentialConsistency";
	//public static final String initMethod = "initCausalAndStrongSequentialConsistency";
	public static final String initMethod = "initStrongSequentialConsistencyForThreeRegions";
	//public static final String initMethod = "initSequentialConsistency";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final List<Replica> replicas = new LinkedList<Replica>();
		for(int i = 0; i < 3; i++)
			replicas.add(new Replica(true));
		final Queue<Integer> numberOfThreads = new PriorityQueue<Integer>();
		/*
		numberOfThreads.add(1);	
		numberOfThreads.add(5);
		numberOfThreads.add(10);
		numberOfThreads.add(15);
		numberOfThreads.add(20);
		numberOfThreads.add(25);*/
		numberOfThreads.add(10);
		VisualizedChart.numberOfRound = numberOfThreads.size();
		Object mutualLock  = VisualizedChart.mutualLock;
		
		for (int i = 0; i < VisualizedChart.numberOfRound; i++) {
		
			final Integer numberOfThread  = numberOfThreads.poll();
			System.gc();
			synchronized(mutualLock){
				
				while(!VisualizedChart.executable){
					try {
						mutualLock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}


				System.out.print((i + 1) + " round attempt to start, we wait for 10 secs\n");
				try {
					Thread.sleep((long)10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			System.out.print( (i + 1) + " round starts\n");
			
			new ThreadTest(new Run() {

				@Override
				public void run() {
					// MetaServiceTest1.main(null);
					org.ssor.test.client1.AsynchronousClient.run(replicas.get(0), numberOfThread);
				}

			}).start();

			new ThreadTest(new Run() {

				@Override
				public void run() {
					// MetaServiceTest2.main(null);
					org.ssor.test.client2.AsynchronousClient.run(replicas.get(0), numberOfThread);
				}

			}).start();

			new ThreadTest(new Run() {

				@Override
				public void run() {
					// MetaServiceTest3.main(null);
					org.ssor.test.client3.AsynchronousClient.run(replicas.get(0), numberOfThread);
				}

			}).start();

			VisualizedChart.executable = false;
		}
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
