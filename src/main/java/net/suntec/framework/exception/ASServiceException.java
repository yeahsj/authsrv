package net.suntec.framework.exception;

public class ASServiceException extends ASBaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ASServiceException() {
		super();
	}

	public ASServiceException(String message) {
		super(message);
	}

	public ASServiceException(String message, Integer errCode) {
		super(message, errCode);
	}

	public ASServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ASServiceException(Throwable cause) {
		super(cause);
	}

	public ASServiceException(Throwable cause, Integer errCode) {
		super(cause, errCode);
	}

}
