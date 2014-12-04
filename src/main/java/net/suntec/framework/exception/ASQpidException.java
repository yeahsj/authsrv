package net.suntec.framework.exception;

public class ASQpidException extends ASBaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ASQpidException() {
		super();
	}

	public ASQpidException(String message) {
		super(message);
	}

	public ASQpidException(String message, Integer errCode) {
		super(message, errCode);
	}

	public ASQpidException(String message, Throwable cause) {
		super(message, cause);
	}

	public ASQpidException(Throwable cause) {
		super(cause);
	}

	public ASQpidException(Throwable cause, Integer errCode) {
		super(cause, errCode);
	}
}
