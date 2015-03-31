package net.suntec.iauto;

import java.io.IOException;

import net.suntec.framework.iauto.dto.IautoConfigDTO;
import net.suntec.framework.iauto.dto.param.IautoPhoneLoginParamDTO;
import net.suntec.oauthsrv.framework.ResourceConfig;

import org.xml.sax.SAXException;

import junit.framework.TestCase;

public class TestIautoPhone extends TestCase {
	public void testOne() {
		int i = 0;
		System.out.println("hello test" + i);
	}

	protected ResourceConfig getInstance(String configFile) throws SAXException, IOException {
		ResourceConfig instance = ResourceConfig.getInstance();
		if (null == configFile) {
			configFile = "config/SystemConfig.xml";
		}
		instance.init(configFile);
		return instance;
	}

	protected IautoPhoneLoginParamDTO getIautoPhoneLoginParamDTO(IautoConfigDTO configDTO) {
		IautoPhoneLoginParamDTO paramDTO = new IautoPhoneLoginParamDTO();
		paramDTO.setClientId(configDTO.getPhoneClientId());
		paramDTO.setClientSercet(configDTO.getPhoneClientSercet());
		paramDTO.setGrantType("password");
		paramDTO.setUsername("zpf");
		paramDTO.setPassword("111111");
		return paramDTO;
	}

	protected IautoConfigDTO getIautoConfigDTO(ResourceConfig instance) {
		return instance.getIautoConfigDTO();
	}
}
