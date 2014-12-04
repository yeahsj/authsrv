package net.suntec.oauthsrv.action.jsonresult;

import net.suntec.framework.dto.SpringJsonResult;

public class AgreeBindStatusResult<T> extends SpringJsonResult<T> {
	boolean isBind = false;

	public int getCode() {
		return code;
	}

	public boolean getIsBind() {
		return isBind;
	}

	public void setIsBind(boolean isBind) {
		this.isBind = isBind;
	}
}
