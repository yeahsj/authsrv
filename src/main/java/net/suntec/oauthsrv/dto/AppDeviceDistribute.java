package net.suntec.oauthsrv.dto;

import java.io.Serializable;

import net.suntec.framework.core.PioneerDTO;

/**
 * 
 * @项目名称: oauthsrv
 * @功能描述: oauth srv架构
 * @修改历史: 1.0
 * @当前版本： 1.0
 * @创建时间: 2014-09-05 16:54:36
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">sang jun</a>
 */
public class AppDeviceDistribute extends PioneerDTO {
	private String appType;
	private String clientId;
	private String deviceNo;

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getDeviceNo() {
		return this.deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Serializable getPrimaryKey() {
		return appType;
	}

	public void setPrimaryKey(Serializable primaryKey) {
		this.appType = (String) primaryKey;
	}
}
