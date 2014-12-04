package net.suntec.oauthsrv.dto;

import java.io.Serializable;

import net.suntec.framework.PioneerDTO;
import net.suntec.framework.util.AuthSrvHtmlUtil;

import com.openjava.core.util.StrUtil;

/**
 * 
 * @项目名称: oauthsrv
 * @功能描述: oauth srv架构
 * @修改历史: 1.0
 * @当前版本： 1.0
 * @创建时间: 2014-05-22 17:06:48
 * @author: <a href="mailto:yeahsj@gmail.com">sang jun</a>
 */
public class AppBase extends PioneerDTO {
	private String appType;
	private String appName;
	private String clazzName;
	private String scope;
	private Integer oauthVersion;
	private Integer requestUserInfo;
	private String description;
	private Integer expiredTime;

	// 显示字段
	private String accessToken;
	private String refreshToken;
	private String apiUid;
	private String creationDate;

	public Integer getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Integer expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getApiUid() {
		return apiUid;
	}

	public void setApiUid(String apiUid) {
		this.apiUid = apiUid;
	}

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getClazzName() {
		return this.clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Integer getOauthVersion() {
		return this.oauthVersion;
	}

	public void setOauthVersion(Integer oauthVersion) {
		this.oauthVersion = oauthVersion;
	}

	public Integer getRequestUserInfo() {
		return this.requestUserInfo;
	}

	public void setRequestUserInfo(Integer requestUserInfo) {
		this.requestUserInfo = requestUserInfo;
	}

	public Serializable getPrimaryKey() {
		return appType;
	}

	public void setPrimaryKey(Serializable primaryKey) {
		this.appType = (String) primaryKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public boolean getIsBind() {
		return StrUtil.isNotEmpty(this.accessToken);
	}

	public String getClickAction() {
		return AuthSrvHtmlUtil.getClickActionForBind(this.getIsBind());
	}

	public String getBtnClassForBind() {
		return AuthSrvHtmlUtil.getBtnClassForBind(this.getIsBind());
	}
}
