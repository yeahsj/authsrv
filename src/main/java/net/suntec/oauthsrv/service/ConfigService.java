package net.suntec.oauthsrv.service;

import java.util.List;

import net.suntec.oauthsrv.dao.AppBaseMapper;
import net.suntec.oauthsrv.dao.AppInfoMapper;
import net.suntec.oauthsrv.dto.AppBase;
import net.suntec.oauthsrv.dto.AppInfo;
import net.suntec.oauthsrv.framework.OauthAppConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:53:32
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
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
