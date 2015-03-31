package net.suntec.common;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTest extends TestCase {
	public ApplicationContext ctx = null;
	public Logger logger = null;

	public void setUp() throws Exception {
		super.setUp();
		logger = LoggerFactory.getLogger(BaseTest.class);
		logger.info(" start ");
		try {
			ctx = new ClassPathXmlApplicationContext("/springCore.xml");
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	public void testOwn(){
//		http://www.baidu.com/?accessToken=CAAViKRVgPswBAJHY0HkTSqESYSosMGXnTuN26L4yAJqBBNjrZBbX4Sd8eN0UzYeHE5t38mU9yVrRWSqZBfym5GXYKrOpDGc2yWy79SsJF7jgQCV1vZCWfTMMJLZCWvI8dKJcfZCFS3vYVNSJZAXaA8wLmzeZAhAt8IZA6QkVp8O1SeMBdMWobF8YM3WMtDgaFFoowKrsCUgHkcnlPUDQLc2p&refreshToken=&uid=100008110475808&clientId=1515303475363532#
//		http://www.baidu.com/?accessToken=CAAEitZBXm40gBAD9abkzZBUSVa5cWsgEsnjZBDj2RpZAPRp7unBXUujYJ1W97z6VDK2pVZC3EgzCUsO0yurNzSfyrwbA4SxSiRtEeoO19pvEBaSLRI9et7zcWdcA8bDrWoDEwJDxLozMFYSLkrNIGtvfKreTSZBP0qXsi5VYnSdWDlzafzpyOnr3Edotpgq2bNoxiZBJCaR1KpiZAyz9IBO8aZCcu79Q3wet993qq5ZCBd6Lz9JYsZAs5nKiacHDHVCLUEZD&refreshToken=&uid=100008110475808&clientId=319648208184136#
		String ass = "CAAViKRVgPswBAJHY0HkTSqESYSosMGXnTuN26L4yAJqBBNjrZBbX4Sd8eN0UzYeHE5t38mU9yVrRWSqZBfym5GXYKrOpDGc2yWy79SsJF7jgQCV1vZCWfTMMJLZCWvI8dKJcfZCFS3vYVNSJZAXaA8wLmzeZAhAt8IZA6QkVp8O1SeMBdMWobF8YM3WMtDgaFFoowKrsCUgHkcnlPUDQLc2p";
		String strFacebookToken="CAAEitZBXm40gBAD9abkzZBUSVa5cWsgEsnjZBDj2RpZAPRp7unBXUujYJ1W97z6VDK2pVZC3EgzCUsO0yurNzSfyrwbA4SxSiRtEeoO19pvEBaSLRI9et7zcWdcA8bDrWoDEwJDxLozMFYSLkrNIGtvfKreTSZBP0qXsi5VYnSdWDlzafzpyOnr3Edotpgq2bNoxiZBJCaR1KpiZAyz9IBO8aZCcu79Q3wet993qq5ZCBd6Lz9JYsZAs5nKiacHDHVCLUEZD";
//		String strFacebookToken="CAAHPmTcpn6MBAI3uvexJVNPNXMm16GklHkYfpp6hV8bP6tSOJdNs3Jd06QeWCZB7A0uinszzBIGHG1BqWVzZBrP5wmBZCV1ZCbyZCXoxDCRvnvKsZClKK9vfph9GUorwqn4b1pQmm5kdsExeZC3fPUgFVBd0REwaPVIdS9w6EhIdHUjlioZAms8GjedZBVnmYiwSO8XLXZCvU8fbzeLc0ZBQX0TZAVnculRvMZADGGqrFOxQJuwKDmVjIaZADo";
		String str2="method=POST&clientId=cd1da91bf7d493363b6bfe6defb24e19&accessToken=72157650708326368-ff1789b39b4b5446&refreshToken=62cd7e3e8ede194d&requestUrl=https%3A%2F%2Fapi.flickr.com%2Fservices%2Frest%2F%3Fmethod%3Dflickr.photos.comments.addComment%26api_key%3Dcd1da91bf7d493363b6bfe6defb24e19%26comment_text%3Dtest%26photo_id%3D123";
		String str ="OAuth oauth_signature=\"ijGwgG%2FKx69uySNTzB%2Bh4C0dNcI%3D\", oauth_version=\"1.0\", oauth_nonce=\"920249115\", oauth_signature_method=\"HMAC-SHA1\", oauth_consumer_key=\"cd1da91bf7d493363b6bfe6defb24e19\", oauth_token=\"72157650708326368-ff1789b39b4b5446\", oauth_timestamp=\"1426129272\"";
		logger.info("hello world");
		logger.info("ass len" + ass.length() );
		logger.info("strFacebookToken len" + strFacebookToken.length() );
		logger.info(str);
		logger.info("size:" + str2.length());
	}
}
