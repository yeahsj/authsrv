package net.suntec.oauthsrv.action.param;

import com.openjava.model.DTO;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:50:58
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
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
