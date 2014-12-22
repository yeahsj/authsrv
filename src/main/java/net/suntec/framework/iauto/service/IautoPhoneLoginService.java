package net.suntec.framework.iauto.service;

import java.io.IOException;

import org.xml.sax.SAXException;

import net.suntec.framework.iauto.dto.IautoConfigDTO;
import net.suntec.framework.iauto.dto.IautoHeaderDTO;
import net.suntec.framework.iauto.dto.param.IautoPhoneLoginParamDTO;
import net.suntec.framework.iauto.dto.result.IautoPhoneLoginResultDTO;
import net.suntec.oauthsrv.framework.ResourceConfig;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:49:17
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class IautoPhoneLoginService extends IautoLoginService {

	static String PHONE_LOGIN_URL = "/auth/oauth/v2.0/token";

	public IautoPhoneLoginService(IautoConfigDTO configDTO,
			IautoPhoneLoginParamDTO paramDTO, IautoHeaderDTO headerDTO) {
		super(configDTO, paramDTO, headerDTO);
	}

	@Override
	public void service() {
		super.login(PHONE_LOGIN_URL);
	}

	public static void main(String[] args) throws SAXException, IOException {
		ResourceConfig instance = ResourceConfig.getInstance();
//		instance.init("H:\\code\\java\\openjava\\authsrv\\src\\main\\resources\\config\\SystemConfig.xml");
		instance.init("config/SystemConfig.xml");
		IautoConfigDTO configDTO = instance.getIautoConfigDTO();
		IautoPhoneLoginParamDTO paramDTO = new IautoPhoneLoginParamDTO();
		paramDTO.setClientId(configDTO.getPhoneClientId());
		paramDTO.setClientSercet(configDTO.getPhoneClientSercet());
		paramDTO.setGrantType("password");
		paramDTO.setUsername("accountsync");
		paramDTO.setPassword("suntec");
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();

		IautoPhoneLoginService iautoDeviceLoginService = new IautoPhoneLoginService(
				configDTO, paramDTO, headerDTO);
		iautoDeviceLoginService.service();
		if (iautoDeviceLoginService.isSuccess()) {
			IautoPhoneLoginResultDTO result = iautoDeviceLoginService
					.getResult();
			System.out.println(result.getAccessToken());
		} else {
			System.out.println(iautoDeviceLoginService.getErrMsg());
		}
	}
}
