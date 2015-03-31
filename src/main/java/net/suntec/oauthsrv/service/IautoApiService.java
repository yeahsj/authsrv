package net.suntec.oauthsrv.service;

import net.suntec.framework.iauto.constant.IautoConstant;
import net.suntec.framework.iauto.dto.IautoConfigDTO;
import net.suntec.framework.iauto.dto.IautoHeaderDTO;
import net.suntec.framework.iauto.dto.IautoUserDTO;
import net.suntec.framework.iauto.dto.param.IautoGetDeviceUserInfoParamDTO;
import net.suntec.framework.iauto.dto.param.IautoPhoneLoginParamDTO;
import net.suntec.framework.iauto.dto.result.IautoPhoneLoginResultDTO;
import net.suntec.framework.iauto.service.IautoDeviceLoginService;
import net.suntec.framework.iauto.service.IautoGetDeviceUserInfoService;
import net.suntec.framework.iauto.service.IautoGetPhoneUserInfoService;
import net.suntec.framework.iauto.service.IautoGetWebUserInfoService;
import net.suntec.framework.iauto.service.IautoPhoneLoginService;
import net.suntec.oauthsrv.framework.ResourceConfig;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:53:39
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class IautoApiService {
	String errMsg;

	public IautoApiService() {
		iautoConfigDTO = ResourceConfig.getInstance().getIautoConfigDTO();
	}

	public IautoUserDTO doGetDeviceUserInfo(String sessionToken,
			String clientId, String deviceNo, String platformVersion,
			String languageCode) {
		IautoGetDeviceUserInfoParamDTO paramDTO = new IautoGetDeviceUserInfoParamDTO();
		if (StrUtil.isEmpty(clientId)) {
			clientId = iautoConfigDTO.getDeviceClientId();
		}
		if (StrUtil.isEmpty(languageCode)) {
			languageCode = iautoConfigDTO.getLanguageCode();
		}
		paramDTO.setClientId(clientId);
		paramDTO.setLanguageCode(languageCode);
		paramDTO.setDeviceNo(deviceNo);
		paramDTO.setPlatformVersion(platformVersion);
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();
		headerDTO.setSessionToken(sessionToken);
		headerDTO.setIfVersion(iautoConfigDTO.getIfVersion());
		IautoGetDeviceUserInfoService iautoGetDeviceUserInfoService = new IautoGetDeviceUserInfoService(
				iautoConfigDTO, paramDTO, headerDTO);
		iautoGetDeviceUserInfoService.service();
		return iautoGetDeviceUserInfoService.getResult();
		
		// isSuccess = iautoGetDeviceUserInfoService.isSuccess();
		// if (isSuccess) {
		// } else {
		// errMsg = " iauto login failed ";
		// throw new AuthException(errMsg);
		// }
	}

	public IautoUserDTO doGetWebUserInfo(String sessionToken,
			String clientId, String languageCode) {
		IautoGetDeviceUserInfoParamDTO paramDTO = new IautoGetDeviceUserInfoParamDTO();
//		if (StrUtil.isEmpty(clientId)) {
			clientId = iautoConfigDTO.getDeviceClientId();
//		}
		if (StrUtil.isEmpty(languageCode)) {
			languageCode = iautoConfigDTO.getLanguageCode();
		}
		paramDTO.setClientId(clientId);
		paramDTO.setLanguageCode(languageCode);
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();
		headerDTO.setSessionToken(sessionToken);
		headerDTO.setIfVersion(iautoConfigDTO.getIfVersion());
		IautoGetWebUserInfoService iautoGetDeviceUserInfoService = new IautoGetWebUserInfoService(
				iautoConfigDTO, paramDTO, headerDTO);
		iautoGetDeviceUserInfoService.service();
		// isSuccess = iautoGetDeviceUserInfoService.isSuccess();
		// if (isSuccess) {
		return iautoGetDeviceUserInfoService.getResult();
		// } else {
		// errMsg = " iauto login failed ";
		// throw new AuthException(errMsg);
		// }
	}
	
	public IautoUserDTO doGetPhoneUserInfo(String sessionToken,
			String clientId, String languageCode) {
		IautoGetDeviceUserInfoParamDTO paramDTO = new IautoGetDeviceUserInfoParamDTO();
		if (StrUtil.isEmpty(clientId)) {
			clientId = iautoConfigDTO.getPhoneClientId();
		}

		if (StrUtil.isEmpty(languageCode)) {
			languageCode = iautoConfigDTO.getLanguageCode();
		}
		paramDTO.setClientId(clientId);
		paramDTO.setLanguageCode(languageCode);
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();
		headerDTO.setSessionToken(sessionToken);
		headerDTO.setIfVersion(iautoConfigDTO.getIfVersion());
		IautoGetPhoneUserInfoService iautoGetDeviceUserInfoService = new IautoGetPhoneUserInfoService(
				iautoConfigDTO, paramDTO, headerDTO);
		iautoGetDeviceUserInfoService.service();
		// isSuccess = iautoGetDeviceUserInfoService.isSuccess();
		// if (isSuccess) {
		return iautoGetDeviceUserInfoService.getResult();
		// } else {
		// errMsg = " iauto login failed ";
		// throw new AuthException(errMsg);
		// }
	}

	public IautoPhoneLoginResultDTO doPhoneLogin() {
		return doPhoneLogin(IautoConstant.DEVICE_USERNAME,
				IautoConstant.DEVICE_PWD);
	}

	public IautoPhoneLoginResultDTO doPhoneLogin(String username,
			String password) {
		IautoPhoneLoginParamDTO paramDTO = new IautoPhoneLoginParamDTO();
		paramDTO.setUsername(username);
		paramDTO.setPassword(password);
		paramDTO.setClientId(iautoConfigDTO.getPhoneClientId());
		paramDTO.setClientSercet(iautoConfigDTO.getPhoneClientSercet());
		paramDTO.setGrantType(IautoConstant.GRANT_TYPE);
		IautoPhoneLoginService iautoPhoneLoginService = new IautoPhoneLoginService(
				iautoConfigDTO, paramDTO, null);
		iautoPhoneLoginService.service();
		// isSuccess = iautoPhoneLoginService.isSuccess();
		// if (isSuccess) {
		return iautoPhoneLoginService.getResult();
		// } else {
		// errMsg = " iauto login failed ";
		// throw new AuthException(errMsg);
		// }
	}

	public IautoPhoneLoginResultDTO doDeviceLogin() {
		IautoPhoneLoginParamDTO paramDTO = new IautoPhoneLoginParamDTO();
		paramDTO.setClientId(iautoConfigDTO.getDeviceClientId());
		paramDTO.setClientSercet(iautoConfigDTO.getDeviceClientSercet());
		paramDTO.setGrantType(IautoConstant.GRANT_TYPE);
		paramDTO.setUsername(IautoConstant.DEVICE_USERNAME);
		paramDTO.setPassword(IautoConstant.DEVICE_PWD);
		IautoHeaderDTO headerDTO = new IautoHeaderDTO();
		IautoDeviceLoginService iautoDeviceLoginService = new IautoDeviceLoginService(
				iautoConfigDTO, paramDTO, headerDTO);
		iautoDeviceLoginService.service();
		// isSuccess = iautoDeviceLoginService.isSuccess();
		// if (isSuccess) {
		return iautoDeviceLoginService.getResult();
		// } else {
		// errMsg = " iauto login failed ";
		// throw new AuthException(errMsg);
		// }
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public String getErrMsg() {
		return errMsg;
	}

	IautoConfigDTO iautoConfigDTO;
	boolean isSuccess = false;
}
