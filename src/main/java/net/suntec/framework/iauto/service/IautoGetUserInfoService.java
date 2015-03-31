package net.suntec.framework.iauto.service;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASIautoException;
import net.suntec.framework.iauto.dto.IautoConfigDTO;
import net.suntec.framework.iauto.dto.IautoHeaderDTO;
import net.suntec.framework.iauto.dto.IautoUserDTO;
import net.suntec.framework.iauto.dto.param.IautoGetDeviceUserInfoParamDTO;

import org.json.JSONObject;
import org.scribe.exceptions.OAuthConnectionException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:49:03
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public abstract class IautoGetUserInfoService extends
		IautoAbstractHttpService<IautoGetDeviceUserInfoParamDTO, IautoUserDTO> {
	String loginUrl = "";

	public IautoGetUserInfoService(IautoConfigDTO configDTO,
			IautoGetDeviceUserInfoParamDTO paramDTO, IautoHeaderDTO headerDTO) {
		super(configDTO, paramDTO, headerDTO);
	}

	public abstract void checkParams();

	@Override
	public void addBodyParameters(OAuthRequest request) {
		request.addBodyParameter("client_id", paramDTO.getClientId());
		request.addBodyParameter("languageCode", paramDTO.getLanguageCode());
	}

	@Override
	public void service() {
		logger.debug("start IautoGetUserInfoService ...... ");
		checkParams();

		OAuthRequest request = new OAuthRequest(Verb.POST,
				configDTO.getIautoServer() + loginUrl);
		setHeader(request);
		setTimeOut(request);
		addBodyParameters(request);
		Response response = null;
		try {
			response = request.send();// fe01ce2a7fbac8fafaed7c982a04e229
		} catch (OAuthConnectionException ex) {
			errMsg = ex.getMessage();
			throw new ASIautoException(errMsg,
					AuthErrorCodeConstant.IT_ERR_CONN);
		}
		String body = response.getBody();
		logger.debug("response body : " + body);
		JSONObject result = new JSONObject(body);
		if (result.has("code")) {
			int code = result.getInt("code");
			if (code == 99000) {
				resultDTO = new IautoUserDTO();
				resultDTO.setUserName(result.getString("userName"));
				isSuccess = true;
			} else {
				errMsg = result.getString("errMsg");
				if (errMsg.contains("expired")
						|| errMsg.contains("login again")) {
					throw new ASIautoException(errMsg,
							AuthErrorCodeConstant.IT_GUI_ERR_TOKEN_EXPIRE);
				} else {
					throw new ASIautoException(errMsg,
							AuthErrorCodeConstant.IT_GUI_ERR_PARAM);
				}
			}
		} else {
			errMsg = "request iauto user info failed";
			throw new ASIautoException(errMsg,
					AuthErrorCodeConstant.IT_ERR_NO_CODE);
		}
	}

}
