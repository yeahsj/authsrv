package net.suntec.oauthsrv.framework.dto;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:52:23
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class AppstoreConfig {
	private String protocol = null;
	private String host = null;
	private String port = null;
	private String clientId = "Phone_353f7a0ca0660fc0d52f384d3081818e";
	private String clientSercet = "P_g0eMq2";
	private String languageCode = "02002";
	private String ifVersion = "1";

	private String userDeviceUrl = "/aswapi/getUserDeviceInfoList";
	private String getTokenUrl = "/auth/oauth/v2.0/token";
	private String grantType = "password";

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getHostUrl() {
		return this.getProtocol() + "://" + this.getHost() + ":"
				+ this.getPort();
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSercet() {
		return clientSercet;
	}

	public void setClientSercet(String clientSercet) {
		this.clientSercet = clientSercet;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getIfVersion() {
		return ifVersion;
	}

	public void setIfVersion(String ifVersion) {
		this.ifVersion = ifVersion;
	}

	public String getUserDeviceUrl() {
		return userDeviceUrl;
	}

	public void setUserDeviceUrl(String userDeviceUrl) {
		this.userDeviceUrl = userDeviceUrl;
	}

	public String getGetTokenUrl() {
		return getTokenUrl;
	}

	public void setGetTokenUrl(String getTokenUrl) {
		this.getTokenUrl = getTokenUrl;
	}

}
