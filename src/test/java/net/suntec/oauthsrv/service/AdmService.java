package net.suntec.oauthsrv.service;

import java.util.List;

import net.suntec.oauthsrv.dao.AppBaseMapper;
import net.suntec.oauthsrv.dao.AppInfoMapper;
import net.suntec.oauthsrv.dto.AppBase;
import net.suntec.oauthsrv.dto.AppInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:27:58
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
@Service
@Deprecated
public class AdmService {
	@Autowired
	AppInfoMapper appInfoMapper;
	@Autowired
	AppBaseMapper appBaseMapper;

	public void saveAppBase(AppBase appBase) {
		appBaseMapper.insert(appBase);
	}

	public List<AppInfo> list(AppInfo appInfo) {
		return appInfoMapper.select(appInfo);
	}

	public List<AppBase> listBase(AppBase appBase) {
		return appBaseMapper.select(appBase);
	}
}
