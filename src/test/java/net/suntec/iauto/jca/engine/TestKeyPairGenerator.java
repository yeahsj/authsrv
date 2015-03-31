package net.suntec.iauto.jca.engine;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestKeyPairGenerator extends TestCase {
	static Logger logger = LoggerFactory.getLogger(TestKeyPairGenerator.class);

	@Test
	public static void testSelectProvider() throws NoSuchAlgorithmException {
		KeyPairGenerator md = KeyPairGenerator.getInstance("DSA");
		logger.info(md.getAlgorithm());
		logger.info(md.getProvider().getName());
		logger.info(md.getProvider().getClass().getName());
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		testSelectProvider();
	}
}