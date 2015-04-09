package net.suntec.oauthsrv.framework.dto;

import net.suntec.framework.iauto.client.dto.IautoConfigDTO;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:52:38
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
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
