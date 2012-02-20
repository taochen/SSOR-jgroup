package org.ssor.test;

import org.ssor.invocation.ProxyFactory;


public class TestService implements ITest{

	
	public int caculate(int i, int k){
		
		int x = k;
		for(int l=0;l<60000000;l++)
		    x++; 
		for(int l=0;l<60000000;l++)
		    x++; 
		for(int l=0;l<60000000;l++)
		    x++; 
		for(int l=0;l<60000000;l++)
		    x++;
		for(int l=0;l<60000000;l++)
		    x++;
		for(int l=0;l<60000000;l++)
		    x++;
		for(int l=0;l<60000000;l++)
		    x++;
		//x = ((IUnd)ProxyFactory.get(null, UndService.class)).random();
		
		return  x;
		
	}
	
	public int count(int i, int k){
		
	
		int x = k;
		//if(Sharing.iund != null)
			//x = Sharing.iund.random();
	
		
		for(int l=0;l<70000000;l++)
		    x++; 
			
		
		for(int l=0;l<70000000;l++)
		    x++; 
		
		for(int l=0;l<70000000;l++)
		    x++; 
	
	
		
		//Sharing.number = k;
		//x = ((IUnd)ProxyFactory.get(null, UndService.class)).random();
		//System.out.print(k + "yyyyyyyyyy\n");
		return x;
		
	}
	
	public int countAndCaculate(int i, int o){
		caculate(i, o);
	
		int x = 0;
		if(Sharing.test == null)
			x  = count(i, o);
		else
		//int x = Sharing.test.caculate(i, o);
		 x = Sharing.test.count(i, o);
		
		return x;
	}

	@Override
	public int work(int i, int o) {
		int x = i;
		//for(int l=0;l<50000;l++)
		   // x++; 
		for(int l=0;l<70000000;l++)
		    x++; 
			
		for(int l=0;l<70000000;l++)
		    x++; 
		
		for(int l=0;l<70000000;l++)
		    x++;  
	
		
		//Sharing.number = o;
		//x = ((IUnd)ProxyFactory.get(null, UndService.class)).random();
		return x;
	}
	

	@Override
	public int work1(int i, int o) {
		int x = i;
		//for(int l=0;l<50000;l++)
		   // x++; 
		for(int l=0;l<70000000;l++)
		    x++; 
			
		for(int l=0;l<70000000;l++)
		    x++; 
		
		for(int l=0;l<70000000;l++)
		    x++; 

		 
		//Sharing.number = o;
		//x = ((IUnd)ProxyFactory.get(null, UndService.class)).random();
		return x;
	}
}
