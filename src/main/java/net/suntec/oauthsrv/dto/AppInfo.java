package net.suntec.oauthsrv.dto;

import java.io.Serializable;

import net.suntec.framework.PioneerDTO;

/**
 * 
 * @项目名称: oauthsrv
 * @功能描述: oauth srv架构
 * @修改历史: 1.0
 * @当前版本： 1.0
 * @创建时间: 2014-05-22 17:06:47
 * @author: <a href="mailto:yeahsj@gmail.com">sang jun</a>
 */
public class AppInfo extends PioneerDTO {
	private Integer appId;
	private String appType;
	private String appClientid;
	private String appSecret;
	private String callbackUrl;
	private Integer sortNo;

	// private String version;

	// private AppBase appBase;
	//
	// public AppBase getAppBase() {
	// return appBase;
	// }
	//
	// public void setAppBase(AppBase appBase) {
	// this.appBase = appBase;
	// }

	public Integer getAppId() {
		return this.appId;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppClientid() {
		return this.appClientid;
	}

	public void setAppClientid(String appClientid) {
		this.appClientid = appClientid;
	}

	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getCallbackUrl() {
		return this.callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	// public String getVersion() {
	// return this.version;
	// }
	//
	// public void setVersion(String version) {
	// this.version = version;
	// }

	public Serializable getPrimaryKey() {
		return appId;
	}

	public void setPrimaryKey(Serializable primaryKey) {
		this.appId = (Integer) primaryKey;
	}

}
