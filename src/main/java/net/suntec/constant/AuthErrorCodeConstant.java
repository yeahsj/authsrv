package net.suntec.constant;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 错误码常量
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:45:07
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public interface AuthErrorCodeConstant {
	int SYSTEM_ERROR_UNEXCEPTED = 0011;

	int NULL_POINTER_ERROR = 1099;
	int JSON_PARSE_ERROR = 1098;
	// 认证失败
	int AUTH_FAILED = 1001;

	int AUTH_CALLBACK_FAILED = 1101;

	int AUTH_CALLBACK_RESUBMIT = 1102;
	// 连接iauto认证失败
	int CONNECT_AND_AUTH_IAUTO_FAILED = 1002;

	// int AUTH_CLIENT_ID_IS_REQUIRED = 1003;
	//
	// int AUTH_LOGIN_NAME_IS_REQUIRED = 1004;
	//
	// int AUTH_BACKURL_IS_REQUIRED = 1005;
	//
	// int AUTH_ACCESS_TOKEN_IS_REQUIRED = 1006;
	//
	// int AUTH_METHOD_IS_REQUIRED = 1007;

	// 获取App list 失败
	int API_FETCH_APP_LIST = 2001;
	int API_LOGIN_NAME_IS_REQUIRED = 2002;
	int API_UNBIDN_FAILED = 2003;
	/******************* IAUTO exception *********************/
	// COMMON
	int IT_ERR = 3001;
	int IT_ERR_CONN = 3002;
	int IT_ERR_NO_CODE = 3003;

	// LOGIN
	int IT_LG_ERR = 3101;
	int IT_LG_ERR_PARAM = 3102;

	// Get User Info
	int IT_GUI_ERR_PARAM = 3201;
	int IT_GUI_ERR_NO_SESSIONTOKEN = 3202;
	int IT_GUI_ERR_TOKEN_EXPIRE = 3203;
	int IT_GUI_DEVICE_ERR_NO_CLIENTID = 3211;
	int IT_GUI_DEVICE_ERR_NO_DEVICENO = 3212;
	int IT_GUI_DEVICE_ERR_NO_IF = 3213;

	/******************* App CRUD exception *********************/
	// COMMON
	int APP_NO_LOGIN_NAME = 4001;
	int APP_NO_APP_TYPE = 4002;
	int APP_NO_CLIENT_ID = 4003;
	int APP_NO_SECRET_ID = 4004;
	int APP_NO_BACKURL = 4005;
	int APP_NO_ACCESS_TOKEN = 4006;
	int APP_NO_METHOD = 4007;
	int APP_INVALID_ACCESS_TOKEN = 4008;
	int APP_NO_CODE_OR_VERIFY = 4009;
	int APP_NO_DEVICE_NO = 4010;
	int APP_CLIENT_ID_INVALID = 4011; 
	
	
	// app_base crud
	int AB_NO_APPTYPE = 4101;
	int AB_NO_CLAZZNAME = 4102;

	/*************** session ************/
	int SS_NO_PARAMS_OAUTHFLOWSTATUS = 6001;
	int SS_NO_PARAMS_OAUTHSTATUSPARAM = 6002;
	int SS_NO_PARAMS_USERINFO = 6003;
	
}
