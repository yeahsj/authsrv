package net.suntec.oauthsrv.service;

import net.suntec.oauthsrv.dao.AppPathLogDetailMapper;
import net.suntec.oauthsrv.dto.AppPathLogDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PathLogService {
	@Autowired
	AppPathLogDetailMapper appPathLogDetailMapper;
	
	public void saveLog(AppPathLogDetail param){
		appPathLogDetailMapper.insert(param);
	}
}
