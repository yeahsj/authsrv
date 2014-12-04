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
public class IautoGetPhoneUserInfoService extends IautoGetUserInfoService {
	private static String USER_INFO_PHONE_URL = "/aswapi/getUserInfoForPhone";

	public IautoGetPhoneUserInfoService(IautoConfigDTO configDTO,
			IautoGetDeviceUserInfoParamDTO paramDTO, IautoHeaderDTO headerDTO) {
		super(configDTO, paramDTO, headerDTO);
		super.loginUrl = USER_INFO_PHONE_URL;
	}

	@Override
	public void checkParams() {
		if (StrUtil.isEmpty(headerDTO.getSessionToken())) {
			throw new ASIautoException(errMsg,
					AuthErrorCodeConstant.IT_GUI_ERR_NO_SESSIONTOKEN);
		}
	}

	@Override
	public void addBodyParameters(OAuthRequest request) {
		request.addBodyParameter("client_id", paramDTO.getClientId());
		request.addBodyParameter("languageCode", paramDTO.getLanguageCode());
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
		paramDTO.setClientId(configDTO.getPhoneClientId());
		paramDTO.setLanguageCode(configDTO.getLanguageCode());
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();
		headerDTO.setIfVersion(configDTO.getIfVersion());
		headerDTO
				.setSessionToken("<TK>01Jj9KROo9nzkC17z4/Uh942EuYb1XKOUn3Eo46hw+quo=");
		IautoGetPhoneUserInfoService service = new IautoGetPhoneUserInfoService(
				configDTO, paramDTO, headerDTO);
		service.service();
	}

}
