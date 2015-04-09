package net.suntec.oauthsrv.dto;

import java.io.Serializable;

import net.suntec.framework.core.PioneerDTO;

/**
 * 
 * @项目名称: oauthsrv
 * @功能描述: oauth srv架构
 * @修改历史: 1.0
 * @当前版本： 1.0
 * @创建时间: 2014-07-28 15:39:45
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">sang jun</a>
 */
public class AppIautoBindConfig extends PioneerDTO {
	private String userName;
	private String appType;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public Serializable getPrimaryKey() {
		return userName;
	}

	public void setPrimaryKey(Serializable primaryKey) {
		this.userName = (String) primaryKey;
	}
}
