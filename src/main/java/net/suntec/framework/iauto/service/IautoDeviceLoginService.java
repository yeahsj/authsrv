package net.suntec.framework.iauto.service;

import java.io.IOException;

import net.suntec.framework.iauto.dto.IautoConfigDTO;
import net.suntec.framework.iauto.dto.IautoHeaderDTO;
import net.suntec.framework.iauto.dto.param.IautoPhoneLoginParamDTO;
import net.suntec.framework.iauto.dto.result.IautoPhoneLoginResultDTO;
import net.suntec.oauthsrv.framework.ResourceConfig;

import org.xml.sax.SAXException;

public class IautoDeviceLoginService extends IautoLoginService {

	static String PHONE_LOGIN_URL = "/auth/oauth/v2.0/token";

	public IautoDeviceLoginService(IautoConfigDTO configDTO, IautoPhoneLoginParamDTO paramDTO, IautoHeaderDTO headerDTO) {
		super(configDTO, paramDTO, headerDTO);
	}
	
	@Override
	public void service() {
		super.login(PHONE_LOGIN_URL);
	}

	public static void main(String[] args) throws SAXException, IOException {
		ResourceConfig instance = ResourceConfig.getInstance();
		instance.init("H:\\code\\java\\openjava\\authsrv\\src\\main\\resources\\config\\SystemConfig.xml");
		IautoConfigDTO configDTO = instance.getIautoConfigDTO();
		IautoPhoneLoginParamDTO paramDTO = new IautoPhoneLoginParamDTO();
		paramDTO.setClientId(configDTO.getDeviceClientId());
		paramDTO.setClientSercet(configDTO.getDeviceClientSercet());
		paramDTO.setGrantType("password");
		paramDTO.setUsername("demo1");
		paramDTO.setPassword("demo");
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();

		IautoDeviceLoginService iautoDeviceLoginService = new IautoDeviceLoginService(configDTO, paramDTO, headerDTO);
		iautoDeviceLoginService.service();
		if (iautoDeviceLoginService.isSuccess()) {
			IautoPhoneLoginResultDTO result = iautoDeviceLoginService.getResult();
			System.out.println(result.getAccessToken());
		} else {
			System.out.println(iautoDeviceLoginService.getErrMsg());
		}
	}


}
