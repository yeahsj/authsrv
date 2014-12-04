package net.suntec.oauthsrv.framework;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 缓存token-username map
 * @当前版本： 1.0
 * @创建时间: 2014-8-7 下午05:52:41
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public final class IautoUserCacheMap {
	Logger logger = LoggerFactory.getLogger(IautoUserCacheMap.class);
	static IautoUserCacheMap instance;
	public static HashMap<String, String> IAUTO_USER_MAP;

	public static IautoUserCacheMap getInstance() {
		return instance;
	}
 
	public synchronized String put(String key, String value) {
		if (IAUTO_USER_MAP.size() >= 1000) {
			logger.info("cache size over 1000 and remove");
			for(int i=0; i<200; i++ ){
				IAUTO_USER_MAP.remove(IAUTO_USER_MAP.keySet().iterator().next());
			}
		}
		return IAUTO_USER_MAP.put(key, value);
	}

	public boolean containsKey(String key) {
		return IAUTO_USER_MAP.containsKey(key);
	}

	public String get(String key) {
		return IAUTO_USER_MAP.get(key);
	}

	private IautoUserCacheMap() {
		IAUTO_USER_MAP = new HashMap<String, String>();
	}

	static {
		instance = new IautoUserCacheMap();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println("i = " + i);
			IautoUserCacheMap.getInstance().put(i + "", i + 1 + "");
		}
	}
}
