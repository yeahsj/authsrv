package net.suntec.oauthsrv.framework.dto;

import net.suntec.framework.iauto.dto.IautoConfigDTO;

public class SystemConfig {
	private ServerConfig serverConfig = null;
	private IautoConfigDTO iautoConfigDTO = null;

	public ServerConfig getServerConfig() {
		return serverConfig;
	}

	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	public IautoConfigDTO getIautoConfigDTO() {
		return iautoConfigDTO;
	}

	public void setIautoConfigDTO(IautoConfigDTO iautoConfigDTO) {
		this.iautoConfigDTO = iautoConfigDTO;
	}

}
