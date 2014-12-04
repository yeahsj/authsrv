package net.suntec.framework.iauto.dto;

public class IautoConfigDTO {
	private String protocol = null;
	private String host = null;
	private String port = null;
	
	private String phoneClientId = "";
	private String phoneClientSercet = "";
	private String deviceClientId = "";
	private String deviceClientSercet = "";

	private String languageCode = "02002";
	private String ifVersion = "1";

	private boolean isDemo = false;
	private String demoName = "";
	
	public String getDemoName() {
		return demoName;
	}

	public void setDemoName(String demoName) {
		this.demoName = demoName;
	}

	public boolean getIsDemo() {
		return isDemo;
	}

	public void setIsDemo(boolean isDemo) {
		this.isDemo = isDemo;
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

	public String getPhoneClientId() {
		return phoneClientId;
	}

	public void setPhoneClientId(String phoneClientId) {
		this.phoneClientId = phoneClientId;
	}

	public String getPhoneClientSercet() {
		return phoneClientSercet;
	}

	public void setPhoneClientSercet(String phoneClientSercet) {
		this.phoneClientSercet = phoneClientSercet;
	}

	public String getDeviceClientId() {
		return deviceClientId;
	}

	public void setDeviceClientId(String deviceClientId) {
		this.deviceClientId = deviceClientId;
	}

	public String getDeviceClientSercet() {
		return deviceClientSercet;
	}

	public void setDeviceClientSercet(String deviceClientSercet) {
		this.deviceClientSercet = deviceClientSercet;
	}

	public String getIautoServer() {
		String serverUrl = this.getProtocol() + "://" + this.getHost() + ":" + this.getPort();
		return serverUrl;
	}

}
