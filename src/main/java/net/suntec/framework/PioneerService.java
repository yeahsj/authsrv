package net.suntec.framework;

import java.util.List;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:26:08
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public interface PioneerService<T extends PioneerDTO> {

	public int insert(T record);

	public int save(T record);

	public List<T> select(T record);

	public List<T> select(T record, int skipRow, int maxRow);

	public <P> T selectByPrimaryKey(P id);

}
