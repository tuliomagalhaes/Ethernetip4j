package se.opendataexchange.ethernetip4j.exceptions;

public class ResponseBufferOverflowException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2905147581344675490L;

	/**
	 * 
	 */
	public ResponseBufferOverflowException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ResponseBufferOverflowException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ResponseBufferOverflowException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ResponseBufferOverflowException(Throwable arg0) {
		super(arg0);
	}
}
