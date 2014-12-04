package net.suntec.framework.iauto.dto.result;

import net.suntec.framework.iauto.dto.IautoResultDTO;

public class IautoPhoneLoginResultDTO implements IautoResultDTO {
	private String accessToken = "";
	private String refreshToken = "";
	private String expiresIn = "";
	private String loginName = "";

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

}
