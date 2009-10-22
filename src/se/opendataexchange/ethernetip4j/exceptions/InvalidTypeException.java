package se.opendataexchange.ethernetip4j.exceptions;

public class InvalidTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 53250411529380229L;

	/**
	 * 
	 */
	public InvalidTypeException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public InvalidTypeException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public InvalidTypeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public InvalidTypeException(Throwable arg0) {
		super(arg0);
	}
}
