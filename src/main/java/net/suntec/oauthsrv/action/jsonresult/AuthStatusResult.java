package net.suntec.oauthsrv.action.jsonresult;

import net.suntec.framework.dto.SpringJsonResult;

public class AuthStatusResult extends SpringJsonResult<String> {
	String lastUpdateTime;

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getCode() {
		return code;
	}
}
