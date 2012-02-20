package org.ssor.test.traditional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssor.AtomicService;
import org.ssor.protocol.Message;
import org.ssor.protocol.replication.RequestHeader;
import org.ssor.util.Group;

public class TestInvocationHandler implements
		InvocationHandler {

	private static final Logger logger = LoggerFactory
	.getLogger(TestInvocationHandler.class);
	
	private Object realInstance;
	
	
	private String instanceName;
	
	ProxyFactory pf;
	
	TestJGroupAdaptor adaptor;

	public TestInvocationHandler(Object realInstance, TestJGroupAdaptor adaptor, ProxyFactory pf) {
		this.realInstance = realInstance;
		instanceName = realInstance.getClass().getName();
		this.adaptor = adaptor;
		this.pf = pf;
	}

	@Override
	public Object invoke(Object instance, Method method, Object[] arguments)
			throws Throwable {
		// TODO finish the custom exception handling
		
		if(ThreadLocalTest.isHave())
		  return method.invoke(realInstance, arguments);
		Object result = null;
		TestHeader header = new TestHeader();
		header.setService(realInstance.getClass().getName() + "." + method.getName());
		Message msg = new Message(header, arguments, true);
		
		pf.push(msg);
		
		adaptor.send(msg);
		
		synchronized(msg){
			
			while(!msg.isExecutable()){
				msg.wait();
			}
			
		}
		
		result = method.invoke(realInstance, arguments);
		
	   synchronized(msg){
			
		   msg.setIsRequirePreBroadcasting(false);
				msg.notifyAll();
			
			
		}
		//result = invoke(method, instanceName + "." + method.getName(), 
				//realInstance, arguments);
		
		//System.out.print("method "+ method.getName() + "*******\n");
		return result;
	}

	
	
	public Object getProxy() {

		return Proxy.newProxyInstance(
				realInstance.getClass().getClassLoader(), realInstance
						.getClass().getInterfaces(), this);
	}

}
