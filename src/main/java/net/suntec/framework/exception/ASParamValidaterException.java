package net.suntec.framework.exception;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 参数检验异常
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:46:21
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class ASParamValidaterException extends ASBaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ASParamValidaterException() {
		super();
	}

	public ASParamValidaterException(String message) {
		super(message);
	}

	public ASParamValidaterException(String message, Integer errCode) {
		super(message, errCode);
	}

	public ASParamValidaterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ASParamValidaterException(Throwable cause) {
		super(cause);
	}

	public ASParamValidaterException(Throwable cause, Integer errCode) {
		super(cause, errCode);
	}
}
