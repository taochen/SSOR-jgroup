package org.ssor.test.elasticity;

import java.util.LinkedList;
import java.util.List;

import org.ssor.test.Replica;
import org.ssor.test.Sharing;

public class ElasticityTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final List<Replica> replicas = new LinkedList<Replica>();
		for(int i = 0; i < 1; i++)
			replicas.add(new Replica(true));
		
		Sharing.test = replicas.get(0).getTest();
		
		try {
			Thread.sleep((long)10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.print("start\n");
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				SynchronousClient1.main(null);
				
			}
			
		}).start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				SynchronousClient2.main(null);
				
			}
			
		}).start();
		
		

	}

}
