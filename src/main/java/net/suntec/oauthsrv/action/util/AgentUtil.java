package net.suntec.oauthsrv.action.util;

import net.suntec.constant.Agent;

public final class AgentUtil {
	public static Agent getAgent(String userAgent) {
		Agent agent = Agent.WEB;
		if (userAgent.equals("Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")) {
			agent = Agent.CARDEVICE;
		} else if (userAgent.indexOf("iPhone") > -1) {
			agent = Agent.IPHONE;
		} else if (userAgent.indexOf("Android") > -1) {
			agent = Agent.ANDROID;
		} else {
			agent = Agent.WEB;
		}
		return agent;
	}
}
