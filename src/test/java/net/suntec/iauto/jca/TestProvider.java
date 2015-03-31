package net.suntec.iauto.jca;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestProvider extends TestCase{
	static Logger logger = LoggerFactory.getLogger(TestProvider.class);

	@Test
	public static void testMessageDigest() throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		logger.info(md.getAlgorithm());
		logger.info(md.getProvider().getName());
		logger.info(md.getProvider().getClass().getName());
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		testMessageDigest();
	}
}
