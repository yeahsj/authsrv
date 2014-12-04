package net.suntec.framework.iauto.dto;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: iauto user result
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:48:02
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class IautoUserDTO implements IautoResultDTO {
	private String userName;
	private String mailAddr;
	private String activationFlag;
	private String countryId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMailAddr() {
		return mailAddr;
	}

	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}

	public String getActivationFlag() {
		return activationFlag;
	}

	public void setActivationFlag(String activationFlag) {
		this.activationFlag = activationFlag;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

}
