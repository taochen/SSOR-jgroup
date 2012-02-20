package org.ssor.gcm.jgroup;

import org.ssor.util.Util;

public class JGroupUtil extends Util {


	private static Util util = new JGroupUtil();
	
	private JGroupUtil(){
		
	}
	
	public static Util getInstance(){
		return util;
	}
	
	@Override
	public int getUUIDFromAddress(Object object) {
		// TODO Auto-generated method stub
		return object.hashCode();
	}

}
