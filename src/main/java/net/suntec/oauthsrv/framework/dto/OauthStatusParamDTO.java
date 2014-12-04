package net.suntec.oauthsrv.framework.dto;

import com.openjava.core.util.StrUtil;
import com.openjava.model.DTO;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 认证流程开启需要传入的参数对象,并且这些对象要在认证callback后仍旧存在session中,以便后续使用. 比如backurl.
 * @当前版本： 1.0
 * @创建时间: 2014-6-13 上午11:06:04
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class OauthStatusParamDTO implements DTO {
	private String backurl; // current server 获取token后,返回到客户端的路径
	private String errurl; // current server 获取token后,返回到客户端的路径
	private String fromPhone;// 控制跳转路径
	private String saveFlag; // 控制是否每次oauth flow成功后,保存Token
	private String loginName; // 02002
	private String sessionToken;

	public String getErrurl() {
		return errurl;
	}

	public void setErrurl(String errurl) {
		this.errurl = errurl;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public boolean isSave() {
		saveFlag = StrUtil.nullToString(saveFlag);
		return (saveFlag.equals("true") || saveFlag.equals("1"));
	}

	public String getBackurl() {
		return backurl;
	}

	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}

	public String getFromPhone() {
		return fromPhone;
	}

	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
