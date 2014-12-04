package net.suntec.oauthsrv.action.param;

import com.openjava.model.DTO;

public class IautoPhoneParamDTO implements DTO {
	String sessionToken;
	String loginName;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

}
