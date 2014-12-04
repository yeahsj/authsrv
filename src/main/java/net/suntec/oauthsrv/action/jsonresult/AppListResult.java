package net.suntec.oauthsrv.action.jsonresult;

import net.suntec.framework.util.AuthSrvHtmlUtil;

public class AppListResult {
	private String appType;
	private String appName;
	private String description;
	private boolean isBind;

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBind(boolean isBind) {
		this.isBind = isBind;
	}

	public boolean getIsBind() {
		return isBind;
	}

	public String getClickAction() {
		return AuthSrvHtmlUtil.getClickActionForBind(this.getIsBind());
	}

	public String getBtnClassForBind() {
		return AuthSrvHtmlUtil.getBtnClassForBind(this.getIsBind());
	}
}
