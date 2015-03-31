package net.suntec.oauthsrv.dto;

import java.io.Serializable;

import net.suntec.framework.PioneerDTO;

/**
 * 
 * @项目名称: oauthsrv
 * @功能描述: oauth srv架构
 * @修改历史: 1.0
 * @当前版本： 1.0
 * @创建时间: 2014-05-22 17:06:48
 * @author: <a href="mailto:yeahsj@gmail.com">sang jun</a>
 */
public class AppIautoMap extends PioneerDTO {
	private Integer aimId;
	private String iautoUserId;
	private String uid;
	private String appType;
	private String clientId;

	private String accessToken;
	private String refreshToken;
	private String apiUid;
	private String creationDate;

	private Integer expireTime;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getAimId() {
		return this.aimId;
	}

	public void setAimId(Integer aimId) {
		this.aimId = aimId;
	}

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

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return this.refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getApiUid() {
		return this.apiUid;
	}

	public void setApiUid(String apiUid) {
		this.apiUid = apiUid;
	}

	public Serializable getPrimaryKey() {
		return aimId;
	}

	public void setPrimaryKey(Serializable primaryKey) {
		this.aimId = (Integer) primaryKey;
	}
}
