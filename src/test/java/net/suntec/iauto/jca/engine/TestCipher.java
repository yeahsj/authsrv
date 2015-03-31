package net.suntec.iauto.jca.engine;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCipher extends TestCase {
	static Logger logger = LoggerFactory.getLogger(TestCipher.class);

	@Test
	public static void testSelectProvider() throws NoSuchAlgorithmException,
			NoSuchPaddingException {
		Cipher md = Cipher.getInstance("AES");
		logger.info(md.getAlgorithm());
		logger.info(md.getProvider().getName());
		logger.info(md.getProvider().getClass().getName());
	}

	public static void main(String[] args) throws NoSuchAlgorithmException,
			NoSuchPaddingException {
		testSelectProvider();
	}
}