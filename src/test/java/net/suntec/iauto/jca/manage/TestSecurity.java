package net.suntec.iauto.jca.manage;

import java.security.Provider;
import java.security.Security;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSecurity extends TestCase {
	static Logger logger = LoggerFactory.getLogger(TestSecurity.class);

	public void testQueryProvider() {
		Provider[] Providers = Security.getProviders();
		for (Provider provider : Providers) {
			logger.info("class: " + provider.getClass().getName());
			logger.info("version: " + provider.getVersion());
		}
	}
}
