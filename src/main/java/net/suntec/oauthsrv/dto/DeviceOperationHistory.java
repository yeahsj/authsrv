package net.suntec.oauthsrv.dto;

import java.io.Serializable;

import net.suntec.framework.core.PioneerDTO;

/**
 * 
 * @项目名称: oauthsrv
 * @功能描述: oauth srv架构
 * @修改历史: 1.0
 * @当前版本： 1.0
 * @创建时间: 2015-03-23 11:38:05
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">sang jun</a>
 */
public class DeviceOperationHistory extends PioneerDTO {
	private String appType; //
	private String deviceNo; //
	private String appVersion; //
	private String creationDate; //

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getDeviceNo() {
		return this.deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getAppVersion() {
		return this.appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Serializable getPrimaryKey() {
		return appType;
	}

	public void setPrimaryKey(Serializable primaryKey) {
		this.appType = (String) primaryKey;
	}
}
