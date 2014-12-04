package net.suntec.framework.iauto.service;

import java.util.concurrent.TimeUnit;

import net.suntec.framework.iauto.dto.IautoConfigDTO;
import net.suntec.framework.iauto.dto.IautoHeaderDTO;
import net.suntec.framework.iauto.dto.IautoParamDTO;
import net.suntec.framework.iauto.dto.IautoResultDTO;

import org.scribe.model.OAuthRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class IautoAbstractHttpService<P extends IautoParamDTO, R extends IautoResultDTO> implements
		IautoHttpService {
	IautoConfigDTO configDTO;
	P paramDTO;
	IautoHeaderDTO headerDTO;
	R resultDTO;
	String errMsg;
	boolean isSuccess = false;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public IautoAbstractHttpService(IautoConfigDTO configDTO, P paramDTO, IautoHeaderDTO headerDTO) {
		this.configDTO = configDTO;
		this.paramDTO = paramDTO;
		this.headerDTO = headerDTO;
	}

	public void setHeader(OAuthRequest request) {
		request.getHeaders().put("IF-VERSION", headerDTO.getIfVersion());
		request.getHeaders().put("SESSION-TOKEN", headerDTO.getSessionToken());
	}

	public void setTimeOut(OAuthRequest request) {
		request.setConnectTimeout(20, TimeUnit.SECONDS);
		request.setReadTimeout(20, TimeUnit.SECONDS);
	}

	public abstract void addBodyParameters(OAuthRequest request);

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public R getResult() {
		return resultDTO;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
