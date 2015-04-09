package net.suntec.oauthsrv.action.util;

import junit.framework.TestCase;
import net.suntec.oauthsrv.util.AesUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAES extends TestCase {
	Logger logger = LoggerFactory.getLogger(TestAES.class);

	public void testDecoder() {
		String appKey = AesUtil.decrypt("TegEiyk/dAac5854J66Frw==");
		logger.info(appKey);
		String appSecret = AesUtil
				.decrypt("EEW5d2BWQNd0z7tStvZb/x253aSQWXvq4GixurBsPPOIzTHsewK3/FeUC41wd6mh");
		logger.info(appSecret);
	}
	
	public void testEncoder() {
		String appKey = AesUtil.encrypt("7575840652");
		logger.info(appKey);
		String appSecret = AesUtil
				.encrypt("8c1f5a2820674996b0256f4b88344a8a");
		logger.info(appSecret);
	}
}
