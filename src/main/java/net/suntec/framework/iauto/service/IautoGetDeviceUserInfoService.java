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

	public IautoGetDeviceUserInfoService(IautoConfigDTO configDTO,
			IautoGetDeviceUserInfoParamDTO paramDTO, IautoHeaderDTO headerDTO) {
		super(configDTO, paramDTO, headerDTO);
		super.loginUrl = USER_INFO_DEVICE_URL;
	}

	@Override
	public void checkParams() {
		if (StrUtil.isEmpty(headerDTO.getSessionToken())) {
			throw new ASIautoException(errMsg,
					AuthErrorCodeConstant.IT_GUI_ERR_NO_SESSIONTOKEN);
		}
		if (StrUtil.isEmpty(paramDTO.getDeviceNo())) {
			throw new ASIautoException(errMsg,
					AuthErrorCodeConstant.IT_GUI_DEVICE_ERR_NO_DEVICENO);
		}
		if (StrUtil.isEmpty(paramDTO.getClientId())) {
			throw new ASIautoException(errMsg,
					AuthErrorCodeConstant.IT_GUI_DEVICE_ERR_NO_CLIENTID);
		}
		if (StrUtil.isEmpty(paramDTO.getPlatformVersion())) {
			throw new ASIautoException(errMsg,
					AuthErrorCodeConstant.IT_GUI_DEVICE_ERR_NO_IF);
		}
	}

	@Override
	public void addBodyParameters(OAuthRequest request) {
		request.addBodyParameter("client_id", paramDTO.getClientId());
		request.addBodyParameter("languageCode", paramDTO.getLanguageCode());
		request.addBodyParameter("deviceNo", paramDTO.getDeviceNo());
		request.addBodyParameter("platformVersion",
				paramDTO.getPlatformVersion());
	}

	/**
	 * response body : {"code":1000,"expires_in":86399,"refresh_token":
	 * "bb4a536f-e87b-4759-96ec-8c62c785fb14"
	 * ,"access_token":"35aa1220-3269-4b88-b4bd-738177af93d6"}
	 */
	public static void main(String[] args) throws SAXException, IOException {
		ResourceConfig instance = ResourceConfig.getInstance();
		instance.init("config/SystemConfig.xml");
//		instance.init("H:\\code\\java\\openjava\\authsrv\\src\\main\\resources\\config\\SystemConfig.xml");
		IautoConfigDTO configDTO = instance.getIautoConfigDTO();
		IautoGetDeviceUserInfoParamDTO paramDTO = new IautoGetDeviceUserInfoParamDTO();
		paramDTO.setClientId(configDTO.getDeviceClientId());
		paramDTO.setLanguageCode(configDTO.getLanguageCode());
//		paramDTO.setDeviceNo("7AB9FAC353FA8F4D234E46DCD11C9F6A");
		paramDTO.setDeviceNo("7ADF1323450F5F364A64A4EA4BCF3EE4");
		
		paramDTO.setPlatformVersion(  "01000000");
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();
		headerDTO.setIfVersion(configDTO.getIfVersion());
		// deviceNo=
//		CarDevice_726ff9b40d40aa1e6e798abc701b4e06
		headerDTO.setSessionToken("<TK>01NWVNkLH6AG0C7JpmKD9NAUlFf9LX6SABPxhz21d5NzI=");
		IautoGetDeviceUserInfoService service = new IautoGetDeviceUserInfoService(
				configDTO, paramDTO, headerDTO);
		service.service();
//		sessionToken=<TK>01PxD8dT7mfadhhqEYaL6ZYfwnw6HdrkT4EEDuwfyLP6E=&iautoClientId=CarDevice_726ff9b40d40aa1e6e798abc701b4e06&deviceNo=7AB9FAC353FA8F4D234E46DCD11C9F6A&platformVersion=01000000
	}
	
	//https://info.iauto.com/accountsync/auth/pocket?sessionToken=%3CTK%3E01zy%2FVyoVfuDFX2ijd6khR3d8Qv2op6MUYfyt%2F9vlArmg88kVXc5N3kMRfe1NNryabNnYxa3ZqvvMG5W4w2ycr4g%3D%3D&iautoClientId=CarDevice_726ff9b40d40aa1e6e798abc701b4e06&deviceNo=7ADF1323450F5F364A64A4EA4BCF3EE4&platformVersion=01000000&backurl=http%3A%2F%2Flocalhost%2Foauth2callback%2Fiauto-app%3A%2F%2Fnet.suntec.web.pocket%2Fchrome%2Fcontent%2Fcallback.html&st=0.14663751143962145

}
