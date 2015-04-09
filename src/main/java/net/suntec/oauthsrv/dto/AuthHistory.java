package net.suntec.oauthsrv.dto;

import java.io.Serializable;

import net.suntec.framework.core.PioneerDTO;

/**
 * 
 * @项目名称: oauthsrv
 * @功能描述: oauth srv架构
 * @修改历史: 1.0
 * @当前版本： 1.0
 * @创建时间: 2014-11-06 16:25:38
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">sang jun</a>
 */
public class AuthHistory extends PioneerDTO {
	String iautoUserId;
	String appType;
	Integer action;
	String creationDate;

	public String getIautoUserId() {
		return this.iautoUserId;
	}

	public void setIautoUserId(String iautoUserId) {
		this.iautoUserId = iautoUserId;
	}

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public Integer getAction() {
		return this.action;
	}

	public void setAction(Integer action) {
		this.action = action;
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
