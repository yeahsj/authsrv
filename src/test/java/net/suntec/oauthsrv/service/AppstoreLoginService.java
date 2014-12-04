package net.suntec.oauthsrv.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.suntec.framework.exception.ASBaseException;
import net.suntec.oauthsrv.framework.ResourceConfig;
import net.suntec.oauthsrv.framework.dto.AppstoreConfig;
import net.suntec.oauthsrv.framework.dto.SessionUser;
import net.suntec.oauthsrv.framework.dto.UserDevice;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.openjava.core.util.encryption.MD5Processor;

/**
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014-5-30 下午04:20:51
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class AppstoreLoginService {

	private SessionUser sessionUser = null;

	private String username;
	private String password;
	private String errMsg;

	private String clientId = "Phone_353f7a0ca0660fc0d52f384d3081818e";
	private String clientSercet = "P_g0eMq2";
	private String appstore_host = "http://172.26.181.40:8080";
	private String languageCode = "02002";
	private String appstoreIfVersion = "1";
	private String loginUrl = "/auth/oauth/v2.0/token";
	private String deviceUrl = "/aswapi/getUserDeviceInfoList";
	private String grant_type = "password";

	private static String USER_INFO_DEVICE_URL = "/aswapi/getUserInfoForDevice";
	private static String USER_INFO_PHONE_URL = "/aswapi/getUserInfoForPhone";
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public AppstoreLoginService(String username, String password) {
		sessionUser = new SessionUser();
		sessionUser.setIAutoLoginName(username);
		AppstoreConfig appstoreConfig = new AppstoreConfig();
//			ResourceConfig.getInstance().getAppstoreConfig();
		clientId = appstoreConfig.getClientId();
		clientSercet = appstoreConfig.getClientSercet();
		appstore_host = appstoreConfig.getHostUrl();
		languageCode = appstoreConfig.getLanguageCode();
		appstoreIfVersion = appstoreConfig.getIfVersion();
		grant_type = appstoreConfig.getGrantType();
		loginUrl = appstoreConfig.getGetTokenUrl();
		deviceUrl = appstoreConfig.getUserDeviceUrl();

		this.username = username;
		this.password = password;
	}

	public void prodPhoneLoginName(String sessionToken) {
		logger.debug("start prodPhoneLoginName ...... ");
		OAuthRequest request = new OAuthRequest(Verb.POST, appstore_host + USER_INFO_PHONE_URL);
		request.getHeaders().put("IF-VERSION", appstoreIfVersion);
		request.getHeaders().put("SESSION-TOKEN", sessionUser.getAppstoreAccessToken());
		request.addBodyParameter("client_id", clientId);
		request.addBodyParameter("languageCode", languageCode);

		Response response = request.send();
		String body = response.getBody();
		logger.debug( "response body : " + body );
		JSONObject result = new JSONObject(body);
		if (result.has("code")) {
			int code = result.getInt("code");
			if (code == 99000) {
				sessionUser.setIAutoLoginName(result.getString("userName"));
			} else {
				errMsg = result.getString("errMsg");
				throw new ASBaseException(errMsg);
			}
		} else {
			errMsg = "get user device is failed";
			throw new ASBaseException(errMsg);
		}
	}

	public void prodDeviceLoginName(String sessionToken) {
		OAuthRequest request = new OAuthRequest(Verb.POST, appstore_host + USER_INFO_DEVICE_URL);
		request.getHeaders().put("IF-VERSION", appstoreIfVersion);
		request.getHeaders().put("SESSION-TOKEN", sessionUser.getAppstoreAccessToken());
		request.addBodyParameter("client_id", clientId);
		request.addBodyParameter("languageCode", languageCode);
		Response response = request.send();
		String body = response.getBody();
		JSONObject result = new JSONObject(body);
		if (result.has("code")) {
			int code = result.getInt("code");
			if (code == 99000) {
				sessionUser.setIAutoLoginName(result.getString("userName"));
			} else {
				errMsg = result.getString("errMsg");
				throw new ASBaseException(errMsg);
			}
		} else {
			errMsg = "get user device is failed";
			throw new ASBaseException(errMsg);
		}
	}

	public void doLogin() {
		doLoginPhone();
	}
	
	public void doLoginPhone() {
		loginAppstore();
		prodPhoneLoginName(sessionUser.getAppstoreAccessToken());
		// getLanguageCode();
		// fetchUserDevice();
	}
	
	public void doLoginDevice() {
		loginAppstoreDevice();
		prodDeviceLoginName(sessionUser.getAppstoreAccessToken());
		// getLanguageCode();
		// fetchUserDevice();
	}

	// private void getLanguageCode() {
	// String userDeviceUrl = appstore_host +
	// "/aswapi/getSystemSupportedLanguageList";
	// OAuthRequest request = new OAuthRequest(Verb.POST, userDeviceUrl);
	// request.getHeaders().put("IF-VERSION", "1");
	// request.getHeaders().put("SESSION-TOKEN", accessToken);
	// request.addBodyParameter("languageCode", "02001");
	//
	// Response response = request.send();
	// String body = response.getBody();
	// JSONObject result = new JSONObject(body);
	// }

	@Deprecated
	private void fetchUserDevice() {
		String userDeviceUrl = appstore_host + deviceUrl;
		OAuthRequest request = new OAuthRequest(Verb.POST, userDeviceUrl);
		request.getHeaders().put("IF-VERSION", appstoreIfVersion);
		request.getHeaders().put("SESSION-TOKEN", sessionUser.getAppstoreAccessToken());
		request.addBodyParameter("client_id", clientId);
		request.addBodyParameter("pageSize", "100");
		request.addBodyParameter("pageNum", "1");
		request.addBodyParameter("languageCode", languageCode);

		Response response = request.send();
		String body = response.getBody();
		JSONObject result = new JSONObject(body);
		if (result.has("code")) {
			int code = result.getInt("code");
			if (code == 99000) {
				List<UserDevice> userDevices = new ArrayList<UserDevice>();
				JSONObject userDevicesPageList = result.getJSONObject("userDevicesPageList");
				JSONArray rows = userDevicesPageList.getJSONArray("rows");
				int len = rows.length();
				for (int i = 0; i < len; i++) {
					JSONObject row = (JSONObject) rows.get(i);
					UserDevice device = new UserDevice();
					device.setUserId(row.getInt("userId"));
					device.setPlatformId(row.getLong("platformId"));
					device.setDeviceNo(row.getString("deviceNo"));
					device.setDeviceId(row.getLong("deviceId"));
					if (row.has("deviceAlias")) {
						device.setDeviceAlias(row.getString("deviceAlias"));// deviceAlias
					}
					device.setBandingStatus(row.getString("bindingStatus")); // bindingStatus
					device.setBandingStatusValue(row.getString("bindingStatusValue"));// bindingStatusValue
					userDevices.add(device);
				}
				// sessionUser.setUserDevices(userDevices);
			} else {
				errMsg = result.getString("errMsg");
				throw new ASBaseException(errMsg);
			}
		} else {
			errMsg = "get user device is failed";
			throw new ASBaseException(errMsg);
		}
	}

	private void loginAppstore() {
		String appstoreLoginUrl = appstore_host + loginUrl;
		OAuthRequest request = new OAuthRequest(Verb.POST, appstoreLoginUrl);
		request.addBodyParameter("grant_type", grant_type);
		request.addBodyParameter("username", username);
		request.addBodyParameter("password", MD5Processor.getMD5Value(password));
		request.addBodyParameter("client_id", clientId);
		request.addBodyParameter("client_secret", clientSercet);
		request.setConnectTimeout(5, TimeUnit.SECONDS);
		request.setReadTimeout(5, TimeUnit.SECONDS);
		Response response = request.send();// fe01ce2a7fbac8fafaed7c982a04e229
		String body = response.getBody();
		JSONObject result = new JSONObject(body);
		if (result.has("code")) {
			int code = result.getInt("code");
			if (code == 1000) {
				String accessToken = result.getString("access_token");
				String refreshToken = result.getString("refresh_token");
				sessionUser.setAppstoreAccessToken(accessToken);
				sessionUser.setAppstoreRefreshToken(refreshToken);
			} else {
				errMsg = result.getString("error_description");
				throw new ASBaseException(errMsg);
			}
		} else {
			errMsg = "get token is failed";
			throw new ASBaseException(errMsg);
		}
	}
	
	private void loginAppstoreDevice() {
		String appstoreLoginUrl = appstore_host + loginUrl;
		OAuthRequest request = new OAuthRequest(Verb.POST, appstoreLoginUrl);
		request.addBodyParameter("grant_type", grant_type);
		request.addBodyParameter("username", username);
		request.addBodyParameter("password", MD5Processor.getMD5Value(password));
		request.addBodyParameter("client_id", clientId);
		request.addBodyParameter("client_secret", clientSercet);
		request.setConnectTimeout(5, TimeUnit.SECONDS);
		request.setReadTimeout(5, TimeUnit.SECONDS);
		Response response = request.send();// fe01ce2a7fbac8fafaed7c982a04e229
		String body = response.getBody();
		JSONObject result = new JSONObject(body);
		if (result.has("code")) {
			int code = result.getInt("code");
			if (code == 1000) {
				String accessToken = result.getString("access_token");
				String refreshToken = result.getString("refresh_token");
				sessionUser.setAppstoreAccessToken(accessToken);
				sessionUser.setAppstoreRefreshToken(refreshToken);
			} else {
				errMsg = result.getString("error_description");
				throw new ASBaseException(errMsg);
			}
		} else {
			errMsg = "get token is failed";
			throw new ASBaseException(errMsg);
		}
	}

	public SessionUser getSessionUser() {
		return sessionUser;
	}

	public static void main(String[] args) throws SAXException, IOException {
		ResourceConfig resourceConfig = ResourceConfig.getInstance();
		resourceConfig.init("classpath:/config/SystemConfig.xml");
		AppstoreLoginService loginUtil = new AppstoreLoginService("demo1", "demo");
		loginUtil.doLogin();
	}
}
