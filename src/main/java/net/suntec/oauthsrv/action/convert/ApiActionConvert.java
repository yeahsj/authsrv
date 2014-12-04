package net.suntec.oauthsrv.action.convert;

import java.util.ArrayList;
import java.util.List;

import net.suntec.oauthsrv.action.jsonresult.AppListResult;
import net.suntec.oauthsrv.dto.AppBase;

import com.openjava.core.util.StrUtil;

public final class ApiActionConvert {
	public static List<AppListResult> connvert(List<AppBase> appBases) {
		List<AppListResult> results = new ArrayList<AppListResult>();
		AppListResult appListResult = null;
		for (AppBase appBase : appBases) {
			appListResult = new AppListResult();
			appListResult.setAppName(appBase.getAppName());
			appListResult.setAppType(appBase.getAppType());
			appListResult.setDescription(appBase.getDescription());
			if (StrUtil.isNotEmpty(appBase.getAccessToken())) {
				appListResult.setBind(true);
			} else {
				appListResult.setBind(false);
			}
			results.add(appListResult);
		} 
		return results;
	}
	
	public static AppListResult connvert(AppBase appBase) {
		AppListResult appListResult = new AppListResult();
		appListResult.setAppName(appBase.getAppName());
		appListResult.setAppType(appBase.getAppType());
		appListResult.setDescription(appBase.getDescription());
		if (StrUtil.isNotEmpty(appBase.getAccessToken())) {
			appListResult.setBind(true);
		} else {
			appListResult.setBind(false);
		}
		return appListResult;

	}
}
