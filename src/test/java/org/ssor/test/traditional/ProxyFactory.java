package org.ssor.test.traditional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.ssor.CollectionFacade;
import org.ssor.protocol.Message;
import org.ssor.util.Group;

/**
 * 
 * Factory class for maintain instance of service, as well as invocation on
 * replica site This is a native implementation, can be replaced by Spring's IoC
 * 
 * 
 * @author Tao Chen
 * 
 */

public class ProxyFactory {

	private  Map<String, Object> registry = CollectionFacade
			.getConcurrentHashMap(50);
	private  Map<String, Method> methodRegistry = CollectionFacade
			.getConcurrentHashMap(50);
	private  Map<String, Message> messages = CollectionFacade
			.getConcurrentHashMap(50);

	
	public ProxyFactory() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public  Object get(TestJGroupAdaptor adaptor, Class<?> clazz) {

		if (registry.containsKey(clazz.getName()))
			return registry.get(clazz.getName());

		Object proxy = null;
		try {
			proxy = new TestInvocationHandler(clazz.newInstance(), adaptor, this)
					.getProxy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		registry.put(clazz.getName(), proxy);

		return proxy;
	}

	public  void setMethod(Method method) {
		methodRegistry.put(method.getName(), method);
	}

	@SuppressWarnings("unchecked")
	public  Object invoke(String name, String method, Object[] args) {

		try {
			
			ThreadLocalTest.set(true);
		
			return methodRegistry.get(method).invoke(registry.get(name), args);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			ThreadLocalTest.set(null);
		}

		return null;

	}

	/**
	 * This should always called if not using IoC framework like Spring
	 * 
	 * @param alias
	 * @return
	 */
	public  Object get(String alias) {
		return registry.get(alias);
	}

	public  void push(Message message) {
		messages.put(message.getReqId(), message);
	}

	public  boolean notify(Message message) {
		Message msg = messages.remove(message.getReqId());
		if (msg != null) {
			synchronized (msg) {
				msg.setIsExecutable(true);
				msg.notifyAll();

				while (msg.isRequirePreBroadcasting()) {
					try {
						msg.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			return true;
		}

		return false;
	}
}
