package net.suntec.oauthsrv.dto;

import java.io.Serializable;

import org.scribe.builder.api.Api;

/**
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:26:52
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class AppConfig implements Serializable {
	private boolean isRequestForUserInfo = true;

	private Integer appId;
	private String appType;
	private String appKey;
	private String appSecret;
	private String callbackUrl;
	private String version;
	private String scope;

	private Integer oauthVersion;

	private String localServer;

	public String getLocalServer() {
		return localServer;
	}

	public void setLocalServer(String localServer) {
		this.localServer = localServer;
	}

	private Class<? extends Api> clazz;

	public Class<? extends Api> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends Api> clazz) {
		this.clazz = clazz;
	}

	public Integer getOauthVersion() {
		return oauthVersion;
	}

	public void setOauthVersion(Integer oauthVersion) {
		this.oauthVersion = oauthVersion;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public boolean isRequestForUserInfo() {
		return isRequestForUserInfo;
	}

	public void setRequestForUserInfo(boolean isRequestForUserInfo) {
		this.isRequestForUserInfo = isRequestForUserInfo;
	}

}
