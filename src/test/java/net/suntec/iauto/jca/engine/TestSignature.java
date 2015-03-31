package net.suntec.iauto.jca.engine;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @项目名称: OauthSrv
 * @功能描述: Signature
 * @当前版本： 1.0
 * @创建时间: 2015年1月4日 下午5:32:34
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class TestSignature extends TestCase {
	static Logger logger = LoggerFactory.getLogger(TestSignature.class);
	KeyPair keys;
	String sourceStr = "hello";

	@Override
	public void setUp() throws Exception {
		super.setUp();
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance("DSA");
			keys = kpg.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public static void testSelectProvider() throws NoSuchAlgorithmException {
		Signature md = Signature.getInstance("DSA");
		logger.info(md.getAlgorithm());
		logger.info(md.getProvider().getName());
		logger.info(md.getProvider().getClass().getName());
	}

	public void testSignAndVerify() throws InvalidKeyException,
			NoSuchAlgorithmException, SignatureException {
		boolean result = verify();
		logger.info("result is : " + result);
	}

	public boolean verify() throws InvalidKeyException,
			NoSuchAlgorithmException, SignatureException {
		byte[] destB = sign();
		Signature md = Signature.getInstance("DSA");
		logger.info("status: " + md.toString());
		md.initVerify(keys.getPublic());
		logger.info("status: " + md.toString());
		md.update(sourceStr.getBytes());
		logger.info("status: " + md.toString());
		boolean result = md.verify(destB);
		logger.info("status: " + md.toString());
		return result;
	}

	public byte[] sign() throws NoSuchAlgorithmException, InvalidKeyException,
			SignatureException {
		Signature md = Signature.getInstance("DSA");
		logger.info("status: " + md.toString());
		md.initSign(keys.getPrivate());
		logger.info("status: " + md.toString());
		byte[] srcB = sourceStr.getBytes();
		for (byte b : srcB) {
			logger.info("old: " + b);
		}
		md.update(srcB);
		logger.info("status: " + md.toString());
		byte[] destB = md.sign();
		logger.info("status: " + md.toString());
		for (byte b : destB) {
			logger.info("new: " + b);
		}
		return destB;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		testSelectProvider();
	}
}
