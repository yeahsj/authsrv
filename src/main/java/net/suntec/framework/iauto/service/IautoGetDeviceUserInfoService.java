package net.suntec.framework.iauto.service;

import java.io.IOException;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASIautoException;
import net.suntec.framework.iauto.dto.IautoConfigDTO;
import net.suntec.framework.iauto.dto.IautoHeaderDTO;
import net.suntec.framework.iauto.dto.param.IautoGetDeviceUserInfoParamDTO;
import net.suntec.oauthsrv.framework.ResourceConfig;

import org.scribe.model.OAuthRequest;
import org.xml.sax.SAXException;

import com.openjava.core.util.StrUtil;

/**
 * 
 * @项目名称: OauthSrv
 * @功能描述: 根据clientId以及sessionToken 获取终端用户信息
 * @当前版本： 1.0
 * @创建时间: 2014-6-17 上午11:49:27
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class IautoGetDeviceUserInfoService extends IautoGetUserInfoService {
	private static String USER_INFO_DEVICE_URL = "/aswapi/getUserInfoForDevice";

	public IautoGetDeviceUserInfoService(IautoConfigDTO configDTO, IautoGetDeviceUserInfoParamDTO paramDTO,
			IautoHeaderDTO headerDTO) {
		super(configDTO, paramDTO, headerDTO);
		super.loginUrl = USER_INFO_DEVICE_URL;
	}

	@Override
	public void checkParams() {
		if (StrUtil.isEmpty(headerDTO.getSessionToken())) {
			throw new ASIautoException(errMsg, AuthErrorCodeConstant.IT_GUI_ERR_NO_SESSIONTOKEN);
		}
		if (StrUtil.isEmpty(paramDTO.getDeviceNo())) {
			throw new ASIautoException(errMsg, AuthErrorCodeConstant.IT_GUI_DEVICE_ERR_NO_DEVICENO);
		}
		if (StrUtil.isEmpty(paramDTO.getClientId())) {
			throw new ASIautoException(errMsg, AuthErrorCodeConstant.IT_GUI_DEVICE_ERR_NO_CLIENTID);
		}
		if (StrUtil.isEmpty(paramDTO.getPlatformVersion())) {
			throw new ASIautoException(errMsg, AuthErrorCodeConstant.IT_GUI_DEVICE_ERR_NO_IF);
		}
	}

	@Override
	public void addBodyParameters(OAuthRequest request) {
		request.addBodyParameter("client_id", paramDTO.getClientId());
		request.addBodyParameter("languageCode", paramDTO.getLanguageCode());
		request.addBodyParameter("deviceNo", paramDTO.getDeviceNo());
		request.addBodyParameter("platformVersion", paramDTO.getPlatformVersion());
	}

	/**
	 * response body : {"code":1000,"expires_in":86399,"refresh_token":
	 * "bb4a536f-e87b-4759-96ec-8c62c785fb14"
	 * ,"access_token":"35aa1220-3269-4b88-b4bd-738177af93d6"}
	 */
	public static void main(String[] args) throws SAXException, IOException {
		ResourceConfig instance = ResourceConfig.getInstance();
		instance.init("H:\\code\\java\\openjava\\authsrv\\src\\main\\resources\\config\\SystemConfig.xml");
		IautoConfigDTO configDTO = instance.getIautoConfigDTO();
		IautoGetDeviceUserInfoParamDTO paramDTO = new IautoGetDeviceUserInfoParamDTO();
		paramDTO.setClientId(configDTO.getDeviceClientId());
		paramDTO.setLanguageCode(configDTO.getLanguageCode());
		paramDTO.setDeviceNo("8b71ce1590e9b595a2f91aea97343117");
		paramDTO.setPlatformVersion(configDTO.getIfVersion());
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();
		headerDTO.setIfVersion(configDTO.getIfVersion());
		// deviceNo=
		headerDTO.setSessionToken("8b71ce1590e9b595a2f91aea97343117");
		IautoGetDeviceUserInfoService service = new IautoGetDeviceUserInfoService(configDTO, paramDTO, headerDTO);
		service.service();
	}

}
