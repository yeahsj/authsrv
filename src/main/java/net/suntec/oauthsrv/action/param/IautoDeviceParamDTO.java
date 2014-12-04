package net.suntec.oauthsrv.action.param;

import com.openjava.model.DTO;

public class IautoDeviceParamDTO implements DTO {
	public String iautoClientId;
	public String sessionToken;
	public String deviceNo;
	public String platformVersion;

	public String getIautoClientId() {
		return iautoClientId;
	}

	public void setIautoClientId(String iautoClientId) {
		this.iautoClientId = iautoClientId;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}

}
