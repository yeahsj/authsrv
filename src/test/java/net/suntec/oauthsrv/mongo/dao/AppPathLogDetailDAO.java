package net.suntec.oauthsrv.mongo.dao;

import java.util.List;

import net.suntec.framework.PioneerDAO;
import net.suntec.oauthsrv.dto.AppPathLogDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

/**
 * 
 * @项目名称: oauthsrv
 * @功能描述: oauth srv架构
 * @修改历史: 1.0
 * @当前版本： 1.0
 * @创建时间: 2014-08-26 15:04:41
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">sang jun</a>
 */
public class AppPathLogDetailDAO implements PioneerDAO<AppPathLogDetail> {
	@Autowired
	MongoTemplate mongoTemplate;

	String collectionName = "app_path_log_detail";

	@Override
	public int count(AppPathLogDetail arg0) {
		// mongoTemplate.count(query, collectionName);
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <P> int deleteByPrimaryKey(P arg0) {
		// TODO Auto-generated method stub
		// mongoTemplate.remove(object, collection)
		return 0;
	}

	@Override
	public int insert(AppPathLogDetail arg0) {
		mongoTemplate.insert(arg0, "app_path_log_detail");
		return 0;
	}

	@Override
	public int insertSelective(AppPathLogDetail arg0) {
		// TODO Auto-generated method stub
		mongoTemplate.insert(arg0, "app_path_log_detail");
		return 0;
	}

	@Override
	public List<AppPathLogDetail> select(AppPathLogDetail arg0) {
		Query query = new Query();
		return mongoTemplate.find(query, AppPathLogDetail.class);
	}

	@Override
	public List<AppPathLogDetail> select(AppPathLogDetail arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		Query query = new Query();
		return mongoTemplate.find(query, AppPathLogDetail.class);
	}

	@Override
	public <P> AppPathLogDetail selectByPrimaryKey(P arg0) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public int updateByPrimaryKey(AppPathLogDetail arg0) {
		// TODO Auto-generated method stub
		Query query = new Query();
		Update update = new Update();
		WriteResult result = mongoTemplate.upsert(query, update, collectionName);
		if (null != result.getLastError()) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateByPrimaryKeySelective(AppPathLogDetail arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
