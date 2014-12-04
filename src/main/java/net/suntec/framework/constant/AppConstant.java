package net.suntec.framework.constant;

public interface AppConstant {
	String SESSION_USER_ATTR = "user";
	String SESSION_OAUTH_STATUS = "oauthFlowStatus"; // 在认证流程中需要保存状态的参数
	String SESSION_OAUTH_PARAMS_STATUS = "oauthFlowParamStatus"; // 在认证流程中需要额外保存状态的一些参数

	String INDEX_URL = "/flow/index";
	String MAIN_URL = "/main.html";
	String LOGIN_URL = "/flow/login";
	String ERROR_URL = "/flow/error";
	String DEVICE_ERROR_URL = "/flow/device/error";

	Integer SUCCESS_CODE = 1000;
	Integer ERROR_CODE = 9999;
	Integer IAUTO_AUTH_ERROR_CODE = 2000;

	Integer AUTH_LOGIN_PHONE = 1;
	Integer AUTH_LOGIN_DEVICE = 2;
	Integer AUTH_LOGOUT_PHONE = 3;
	Integer AUTH_LOGOUT_DEVICE = 4;
}
