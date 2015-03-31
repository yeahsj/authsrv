package net.suntec.oauthsrv.service;

import net.suntec.oauthsrv.dao.AppInfoMapper;
import net.suntec.oauthsrv.dto.AppInfo;
import net.suntec.oauth.check.CheckToken;
import net.suntec.oauth.check.CheckTokenFactory;
import net.suntec.oauth.check.TokenInfo;
import net.suntec.oauthsrv.action.param.DeviceAppInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenCheckService {
	Logger logger = LoggerFactory.getLogger(TokenCheckService.class);
	String CON_STR = ",";
	@Autowired
	CheckTokenFactory factory;

	@Autowired
	AppInfoMapper appInfoMapper;

	/**
	 * 尝试3次Check AccessToken
	 * 
	 * @param appType
	 * @param record
	 * @return
	 */
	public boolean isValid(TokenInfo record) {
		boolean isValid = false;
		int retryCount = 3;
		CheckToken checkToken = factory.create(record.getAppType());
		for (int i = 0; i < retryCount; i++) {
			isValid = checkToken.check(record);
			if (isValid) {
				break;
			}
		}
		return isValid;
	}

	public boolean isValid(String appType, DeviceAppInfo deviceAppInfo) {
		AppInfo params = new AppInfo();
		params.setAppClientid(deviceAppInfo.getClientId());
		params.setAppType(appType);

		AppInfo appInfo = appInfoMapper.selectByClientId(params);
		if (null != appInfo) {
			TokenInfo record = new TokenInfo();
			record.setAccessToken(deviceAppInfo.getAccessToken());
			record.setRefreshToken(deviceAppInfo.getRefreshToken());
			record.setUid(deviceAppInfo.getUid());
			record.setApiKey(appInfo.getAppClientid());
			record.setSecret(appInfo.getAppSecret());
			record.setAppType(appType);
			return isValid(record);
		} else {
			return false;
		}
	}
}
