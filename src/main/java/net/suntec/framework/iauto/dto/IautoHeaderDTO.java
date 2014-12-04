package net.suntec.framework.iauto.dto;

public class IautoHeaderDTO {
	private String sessionToken;
	private String ifVersion = "1";

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getIfVersion() {
		return ifVersion;
	}

	public void setIfVersion(String ifVersion) {
		this.ifVersion = ifVersion;
	}

}
