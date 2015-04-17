package net.suntec.oauthsrv.action.piece;

public class CallbackCacheKey {
	// 会话ID
	String sessionId;

	String appType;

	String code;

	String oauthVerifier;

	String oauthToken;

	static String CON_STR = "-";

	public CallbackCacheKey(String sessionId, String appType, String code,
			String oauthVerifier, String oauthToken) {
		super();
		this.sessionId = sessionId;
		this.appType = appType;
		this.code = code;
		this.oauthVerifier = oauthVerifier;
		this.oauthToken = oauthToken;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(sessionId);
		sb.append(CON_STR);
		sb.append(appType);
		sb.append(CON_STR);
		sb.append(code);
		sb.append(CON_STR);
		sb.append(oauthVerifier);
		sb.append(CON_STR);
		sb.append(oauthToken);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appType == null) ? 0 : appType.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((oauthToken == null) ? 0 : oauthToken.hashCode());
		result = prime * result
				+ ((oauthVerifier == null) ? 0 : oauthVerifier.hashCode());
		result = prime * result
				+ ((sessionId == null) ? 0 : sessionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CallbackCacheKey other = (CallbackCacheKey) obj;
		if (appType == null) {
			if (other.appType != null)
				return false;
		} else if (!appType.equals(other.appType))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (oauthToken == null) {
			if (other.oauthToken != null)
				return false;
		} else if (!oauthToken.equals(other.oauthToken))
			return false;
		if (oauthVerifier == null) {
			if (other.oauthVerifier != null)
				return false;
		} else if (!oauthVerifier.equals(other.oauthVerifier))
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		return true;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOauthVerifier() {
		return oauthVerifier;
	}

	public void setOauthVerifier(String oauthVerifier) {
		this.oauthVerifier = oauthVerifier;
	}

	public String getOauthToken() {
		return oauthToken;
	}

	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}

}
