package org.ssor.gcm.jgroup;

import org.ssor.conf.ConfigurationParser;
import org.ssor.conf.ConfigurationParser.AdaptorDelegate;
import org.ssor.gcm.GCMAdaptor;
import org.ssor.util.Group;

public class Activator {

	public static final String CONFIGURATION_FILE = "jgroup.xml";

	public Activator() {

		
		ConfigurationParser.initialize(new AdaptorDelegate() {

			private GCMAdaptor gcm;
			@Override
			public Group getGroup(String name) {

				// Read jgroup config file
				String prefix = Activator.class.getProtectionDomain()
						.getCodeSource().getLocation().getFile();
				if (prefix.endsWith("Activator.class"))
					prefix = prefix.replaceAll("org/ssor/gcm/jgroup/Activator.class", "");

				gcm = new JGroupAdaptor(name);
				gcm.setPath(prefix + CONFIGURATION_FILE);
				
				return gcm.getGroup();
			}
			
			public void init() {
				new JGroupReceiver(gcm);
			}

		});
	}

}
