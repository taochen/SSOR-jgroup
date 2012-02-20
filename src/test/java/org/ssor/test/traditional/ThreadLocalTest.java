package org.ssor.test.traditional;

public class ThreadLocalTest {

	static ThreadLocal local = new ThreadLocal();
	
	public static void set(Object o){
		local.set(o);
	}
	
	public static boolean isHave(){
		return local.get() != null;
	}
}
