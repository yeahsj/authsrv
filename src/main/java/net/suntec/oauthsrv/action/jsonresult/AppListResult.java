package net.suntec.oauthsrv.action.jsonresult;

import net.suntec.framework.util.AuthSrvHtmlUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:50:42
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
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
