package se.opendataexchange.ethernetip4j.exceptions;

public class InsufficientNrOfAttributesException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4616287350249319586L;

	/**
	 * 
	 */
	public InsufficientNrOfAttributesException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public InsufficientNrOfAttributesException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public InsufficientNrOfAttributesException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public InsufficientNrOfAttributesException(Throwable arg0) {
		super(arg0);
	}
}
