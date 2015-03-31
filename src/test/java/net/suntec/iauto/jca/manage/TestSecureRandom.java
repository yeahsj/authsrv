package net.suntec.iauto.jca.manage;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSecureRandom extends TestCase {
	static Logger logger = LoggerFactory.getLogger(TestSecureRandom.class);

	public void testCreate() throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] b = sr.generateSeed(24);
		for (byte c : b) {
			logger.info("" + c);
		}
		String str = new String(b, "utf-8");
		logger.info(str);
	}
}
