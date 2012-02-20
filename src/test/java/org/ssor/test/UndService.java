package org.ssor.test;

import java.util.Random;

public class UndService implements IUnd {

	private Random r = new Random();
	@Override
	public int random() {
	
		// TODO Auto-generated method stub
		return r.nextInt(Integer.MAX_VALUE);
	}

}
