package net.suntec.iauto;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.Assert;
import org.scribe.exceptions.OAuthConnectionException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestNetWork extends TestCase {
	Logger logger = LoggerFactory.getLogger(TestNetWork.class);

	public void testIauto() {
		String url = "https://info.iauto.com/accountsync/";
		OAuthRequest request = new OAuthRequest(Verb.GET, url);
		request.setConnectTimeout(15, TimeUnit.SECONDS);
		request.setReadTimeout(15, TimeUnit.SECONDS);
		Response response = null;
		try {
			response = request.send();
			String body = response.getBody();
			Assert.assertTrue(body.contains("success"));
		} catch (OAuthConnectionException ex) {
			throw new RuntimeException(ex);
		}
	}

	public boolean checkAuthServer() {
		boolean isValidConn = false;
		String url = "https://iauto.info.com/accountsync";
		OAuthRequest request = new OAuthRequest(Verb.GET, url);
		request.setConnectTimeout(15, TimeUnit.SECONDS);
		request.setReadTimeout(15, TimeUnit.SECONDS);
		Response response = null;
		try {
			response = request.send();
			String body = response.getBody();
			if (body.equals("success")) {
				isValidConn = true;
			}
		} catch (OAuthConnectionException ex) {
			logger.error(ex.getMessage());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return isValidConn;
	}
}
