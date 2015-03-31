package net.suntec.iauto.jca.engine;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestKeyFactory extends TestCase {
	static Logger logger = LoggerFactory.getLogger(TestKeyFactory.class);

	@Test
	public static void testSelectProvider() throws NoSuchAlgorithmException {
		KeyFactory md = KeyFactory.getInstance("DSA");
		logger.info(md.getAlgorithm());
		logger.info(md.getProvider().getName());
		logger.info(md.getProvider().getClass().getName());
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		testSelectProvider();
	}
}