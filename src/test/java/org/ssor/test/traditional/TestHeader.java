package org.ssor.test.traditional;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.ssor.protocol.Header;
import org.ssor.util.Util;

public class TestHeader extends Header{

	String service;
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Override
	public void readFrom(DataInputStream in) throws IOException,
			IllegalAccessException, InstantiationException {
		// TODO Auto-generated method stub
		service = Util.readString(in);
		readOuter(in);
	}

	@Override
	public void writeTo(DataOutputStream out) throws IOException {
		// TODO Auto-generated method stub
        Util.writeString(service, out);
		writeOuter(out);
		
	}
}