package net.suntec.oauthsrv.service;

import java.util.List;

import net.suntec.oauthsrv.dao.AppBaseMapper;
import net.suntec.oauthsrv.dao.AppInfoMapper;
import net.suntec.oauthsrv.dto.AppBase;
import net.suntec.oauthsrv.dto.AppInfo;
import net.suntec.oauthsrv.framework.OauthAppConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
	@Autowired
	AppBaseMapper appBaseMapper;
	@Autowired
	AppInfoMapper appInfoMapper;

	public void getOauthConfig() {
		OauthAppConfig instance = OauthAppConfig.getInstance();
		List<AppInfo> appInfos = appInfoMapper.select(new AppInfo());
		List<AppBase> appBases = appBaseMapper.select(new AppBase());
		instance.setAppInfos(appInfos);
		instance.setAppBases(appBases);
		instance.init();
	}
}
