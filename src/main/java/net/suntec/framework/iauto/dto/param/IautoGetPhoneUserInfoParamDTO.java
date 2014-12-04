package net.suntec.framework.iauto.dto.param;

import net.suntec.framework.iauto.dto.IautoParamDTO;

public class IautoGetPhoneUserInfoParamDTO implements IautoParamDTO{
	private String clientId = "";
	private String languageCode = "";

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

}
