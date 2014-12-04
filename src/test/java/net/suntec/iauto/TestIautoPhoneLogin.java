package net.suntec.iauto;

import java.io.IOException;

import net.suntec.framework.exception.ASIautoLoginException;
import net.suntec.framework.iauto.dto.IautoConfigDTO;
import net.suntec.framework.iauto.dto.IautoHeaderDTO;
import net.suntec.framework.iauto.dto.param.IautoPhoneLoginParamDTO;
import net.suntec.framework.iauto.dto.result.IautoPhoneLoginResultDTO;
import net.suntec.framework.iauto.service.IautoPhoneLoginService;
import net.suntec.oauthsrv.framework.ResourceConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class TestIautoPhoneLogin extends TestIautoPhone {
	Logger logger = LoggerFactory.getLogger(TestIautoPhoneLogin.class.getName());
	String errorConfigFile = "H:\\code\\java\\openjava\\authsrv\\src\\test\\resources\\config\\SystemConfig.xml";

	//成功操作
	public void testPhoneLoginSuccess() throws SAXException, IOException {
		ResourceConfig instance = getInstance(null);
		IautoConfigDTO configDTO = getIautoConfigDTO(instance);
		IautoPhoneLoginParamDTO paramDTO = getIautoPhoneLoginParamDTO(configDTO);
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();
		IautoPhoneLoginService iautoDeviceLoginService = new IautoPhoneLoginService(configDTO, paramDTO, headerDTO);
		iautoDeviceLoginService.service();
		if (iautoDeviceLoginService.isSuccess()) {
			IautoPhoneLoginResultDTO result = iautoDeviceLoginService.getResult();
			System.out.println(result.getAccessToken());
		} else {
			System.out.println(iautoDeviceLoginService.getErrMsg());
		}
	}

	//参数错误
	public void testPhoneLoginErrorPwd() throws SAXException, IOException {
		ResourceConfig instance = getInstance(null);
		IautoConfigDTO configDTO = getIautoConfigDTO(instance);
		IautoPhoneLoginParamDTO paramDTO = getIautoPhoneLoginParamDTO(configDTO);
		paramDTO.setPassword("11111");
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();
		IautoPhoneLoginService iautoDeviceLoginService = new IautoPhoneLoginService(configDTO, paramDTO, headerDTO);
		try {
			iautoDeviceLoginService.service();
			if (iautoDeviceLoginService.isSuccess()) {
				IautoPhoneLoginResultDTO result = iautoDeviceLoginService.getResult();
				System.out.println(result.getAccessToken());
			} else {
				System.out.println(iautoDeviceLoginService.getErrMsg());
			}
		} catch (ASIautoLoginException ex) {
			logger.error(ex.getMessage());
			logger.error("errCode: " + ex.getErrCode());
		}
	}

	//网络错误
	public void testPhoneLoginErrorNetWork() throws SAXException, IOException {
		ResourceConfig instance = getInstance(null);
		IautoConfigDTO configDTO = getIautoConfigDTO(instance);
		IautoPhoneLoginParamDTO paramDTO = getIautoPhoneLoginParamDTO(configDTO);
		paramDTO.setPassword("11111");
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();
		IautoPhoneLoginService iautoDeviceLoginService = new IautoPhoneLoginService(configDTO, paramDTO, headerDTO);
		try {
			iautoDeviceLoginService.service();
			if (iautoDeviceLoginService.isSuccess()) {
				IautoPhoneLoginResultDTO result = iautoDeviceLoginService.getResult();
				System.out.println(result.getAccessToken());
			} else {
				System.out.println(iautoDeviceLoginService.getErrMsg());
			}
		} catch (ASIautoLoginException ex) {
			logger.error(ex.getMessage());
			logger.error("errCode: " + ex.getErrCode());
		}
	}

	
}
