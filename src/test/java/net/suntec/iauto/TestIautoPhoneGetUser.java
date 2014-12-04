package net.suntec.iauto;

import java.io.IOException;

import net.suntec.framework.exception.ASIautoException;
import net.suntec.framework.exception.ASIautoLoginException;
import net.suntec.framework.iauto.dto.IautoConfigDTO;
import net.suntec.framework.iauto.dto.IautoHeaderDTO;
import net.suntec.framework.iauto.dto.IautoUserDTO;
import net.suntec.framework.iauto.dto.param.IautoGetDeviceUserInfoParamDTO;
import net.suntec.framework.iauto.service.IautoGetPhoneUserInfoService;
import net.suntec.oauthsrv.framework.ResourceConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class TestIautoPhoneGetUser extends TestIautoPhone {
	Logger logger = LoggerFactory.getLogger(TestIautoPhoneGetUser.class.getName());

	private IautoGetDeviceUserInfoParamDTO getParamDTO(IautoConfigDTO configDTO) {
		IautoGetDeviceUserInfoParamDTO paramDTO = new IautoGetDeviceUserInfoParamDTO();
		paramDTO.setClientId(configDTO.getPhoneClientId());
		paramDTO.setLanguageCode(configDTO.getLanguageCode());
		return paramDTO;
	}

	public void testGetUserSucccess() throws SAXException, IOException {
		ResourceConfig instance = getInstance(null);
		IautoConfigDTO configDTO = getIautoConfigDTO(instance);
		IautoGetDeviceUserInfoParamDTO paramDTO = getParamDTO(configDTO);
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();
		headerDTO.setIfVersion(configDTO.getIfVersion());
		headerDTO.setSessionToken("<TK>01Jj9KROo9nzkC17z4/Uh942EuYb1XKOUn3Eo46hw+quo=");
		IautoGetPhoneUserInfoService service = new IautoGetPhoneUserInfoService(configDTO, paramDTO, headerDTO);
		try {
			service.service();
			if (service.isSuccess()) {
				IautoUserDTO user = service.getResult();
				logger.info(user.getUserName());
			} else {
				logger.error(service.getErrMsg());
			}
		} catch (ASIautoLoginException ex) {
			logger.error(ex.getMessage());
			logger.error("errCode: " + ex.getErrCode());
		} catch (ASIautoException ex) {
			logger.error(ex.getMessage());
			logger.error("errCode: " + ex.getErrCode());
		}
	}
}
