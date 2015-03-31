package net.suntec.framework.constant;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:45:37
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public interface AppConstant {
	String NCMS_MSGID_ADD_SUB = "CT01";
	String NCMS_MSGID_DELETE_SUB = "CT02";
	String NCMS_MSGID_DEVICE_NOTI = "NT03";

	Integer NCMS_OPR_SUB_TYPE_ADD = 1;
	Integer NCMS_OPR_SUB_TYPE_REMOVE = 2;
	Long NCMS_OPR_SUB_SESSION_TIME = 0L;

	String ACCOUNTSYNC_AES_KEY = "pset|accountsync";
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

	boolean IS_SEND_TO_MQ = false;
}
