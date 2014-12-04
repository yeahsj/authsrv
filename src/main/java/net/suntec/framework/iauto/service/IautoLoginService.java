package net.suntec.framework.iauto.service;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASIautoLoginException;
import net.suntec.framework.iauto.dto.IautoConfigDTO;
import net.suntec.framework.iauto.dto.IautoHeaderDTO;
import net.suntec.framework.iauto.dto.param.IautoPhoneLoginParamDTO;
import net.suntec.framework.iauto.dto.result.IautoPhoneLoginResultDTO;

import org.json.JSONObject;
import org.scribe.exceptions.OAuthConnectionException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

import com.openjava.core.util.encryption.MD5Processor;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:49:13
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public abstract class IautoLoginService
		extends
		IautoAbstractHttpService<IautoPhoneLoginParamDTO, IautoPhoneLoginResultDTO> {

	public IautoLoginService(IautoConfigDTO configDTO,
			IautoPhoneLoginParamDTO paramDTO, IautoHeaderDTO headerDTO) {
		super(configDTO, paramDTO, headerDTO);
	}

	@Override
	public void addBodyParameters(OAuthRequest request) {
		request.addBodyParameter("grant_type", paramDTO.getGrantType());
		request.addBodyParameter("username", paramDTO.getUsername());
		request.addBodyParameter("password",
				MD5Processor.getMD5Value(paramDTO.getPassword()));
		request.addBodyParameter("client_id", paramDTO.getClientId());
		request.addBodyParameter("client_secret", paramDTO.getClientSercet());
	}

	public void login(String loginUrl) {
		isSuccess = false;
		logger.debug("start AppstorePhoneLoginService doLogin ...... ");
		String appstoreLoginUrl = configDTO.getIautoServer() + loginUrl;
		logger.debug("appstoreLoginUrl: " + appstoreLoginUrl);
		OAuthRequest request = new OAuthRequest(Verb.POST, appstoreLoginUrl);
		this.addBodyParameters(request);
		this.setTimeOut(request);
		Response response = null;
		try {
			response = request.send();// fe01ce2a7fbac8fafaed7c982a04e229
		} catch (OAuthConnectionException ex) {
			errMsg = ex.getMessage();
			throw new ASIautoLoginException(errMsg,
					AuthErrorCodeConstant.IT_ERR_CONN);
		}
		String body = response.getBody();
		logger.debug("response body : " + body);
		JSONObject result = new JSONObject(body);
		if (result.has("code")) {
			int code = result.getInt("code");
			if (code == 1000) {
				String accessToken = result.getString("access_token");
				String refreshToken = result.getString("refresh_token");
				resultDTO = new IautoPhoneLoginResultDTO();
				resultDTO.setAccessToken(accessToken);
				resultDTO.setRefreshToken(refreshToken);
				resultDTO.setLoginName(paramDTO.getUsername());
				isSuccess = true;
			} else {
				errMsg = result.getString("error_description");
				throw new ASIautoLoginException(errMsg,
						AuthErrorCodeConstant.IT_LG_ERR_PARAM);
			}
		} else {
			errMsg = "get token is failed";
			throw new ASIautoLoginException(errMsg,
					AuthErrorCodeConstant.IT_ERR_NO_CODE);
		}
	}

}
