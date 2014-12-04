package net.suntec.framework.iauto.dto;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: Header config
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:47:23
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
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
