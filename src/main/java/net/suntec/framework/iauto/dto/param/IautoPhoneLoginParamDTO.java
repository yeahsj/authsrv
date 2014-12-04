package net.suntec.framework.iauto.dto.param;

import net.suntec.framework.iauto.dto.IautoParamDTO;

public class IautoPhoneLoginParamDTO implements IautoParamDTO {
	private String clientId = "";
	private String clientSercet = "";
	private String grantType = "password";
	private String username;
	private String password;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSercet() {
		return clientSercet;
	}

	public void setClientSercet(String clientSercet) {
		this.clientSercet = clientSercet;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
