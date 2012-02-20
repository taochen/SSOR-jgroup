package org.ssor.test;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.ssor.test.traditional.*;


public class JgSampleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final List<Replica> replicas = new LinkedList<Replica>();
		for(int i = 0; i < 3; i++)
			replicas.add(new Replica(false));
		final Queue<Integer> numberOfThreads = new PriorityQueue<Integer>();
		
		numberOfThreads.add(2);		
		numberOfThreads.add(5);
		numberOfThreads.add(10);
		numberOfThreads.add(15);
		numberOfThreads.add(20);
		numberOfThreads.add(25);
		numberOfThreads.add(30);
		
		VisualizedChart.numberOfRound = numberOfThreads.size();
		Object mutualLock  = VisualizedChart.mutualLock;
		try {
			Thread.sleep((long)8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
					AsynchronousClient1.run(replicas.get(0), numberOfThread);
				}

			}).start();

			/*new ThreadTest(new Run() {

				@Override
				public void run() {
					AsynchronousClient2.run(replicas.get(0), numberOfThread);
				}

			}).start();

			new ThreadTest(new Run() {

				@Override
				public void run() {
					AsynchronousClient3.run(replicas.get(0), numberOfThread);
				}

			}).start();*/

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

		public void run(){
			run.run();
		}
	}
	
	private static interface Run{
		void run();
	}
}
