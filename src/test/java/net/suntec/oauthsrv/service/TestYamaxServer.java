package net.suntec.oauthsrv.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

public class TestYamaxServer extends TestCase {

	public void testOne() {
		System.out.println(1);
	}

	public static void testAuthHeaderType1() {
		String url = "http://tmap-beluga-auth3rdsrv-1918984635.ap-northeast-1.elb.amazonaws.com/auth/twitter/header";
		testAuthHeader(url);
	}

	public static void testAuthHeaderType2() {
		String url = "http://54.199.174.148:8080/auth/twitter/header";
		testAuthHeader(url);
	}

	public static void testAuthHeader(String hostUrl) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		try {
			String url = "http://tmap-beluga-auth3rdsrv-1918984635.ap-northeast-1.elb.amazonaws.com/auth/twitter/header";
			OAuthRequest request = new OAuthRequest(Verb.GET, url);
			request.addQuerystringParameter("sessionToken", "d6f44085-5de1-4f2a-bdb9-a3ed7dfd8812");
			request.addQuerystringParameter("method", "GET");
			request
					.addQuerystringParameter("requestUrl",
							"https://api.twitter.com/1.1/account/verify_credentials.json");
			request.setConnectTimeout(60, TimeUnit.SECONDS);
			Response response = request.send();
			String body = response.getBody();
			System.out.println(" response body : " + body);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		System.out.println("cast time: " + (endTime - startTime));
	}

	public static void testIndex() {
		String url = "https://info.iauto.com/accountsync";
		OAuthRequest request = new OAuthRequest(Verb.GET, url);
		Response response = request.send();
		String body = response.getBody();
		System.out.println("response body : " + body);
	}

	public static void main(String[] args) {
		// TestYamaxServer.testIndex();
		// TestYamaxServer.testAuthHeaderType1();
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new YamaxServerThread());
			threads.add(thread);
		}

		for (Iterator<Thread> threadItr = threads.iterator(); threadItr.hasNext();) {
			Thread thread = (Thread) threadItr.next();
			thread.start();
		}
	}

	static class YamaxServerThread implements Runnable {
		@Override
		public void run() {
			TestYamaxServer.testAuthHeaderType2();
		}
	}

}
