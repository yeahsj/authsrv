package net.suntec.iauto.jca;

import java.security.KeyStore;
import java.security.KeyStoreException;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestKeyStore extends TestCase {
	static Logger logger = LoggerFactory.getLogger(TestKeyStore.class);

	public void testOne() {
		logger.info("TestKeyStore ");
	}

	public static void main(String[] args) throws KeyStoreException {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		String type = ks.getType();
		logger.info("type: " + type);

	}
}
