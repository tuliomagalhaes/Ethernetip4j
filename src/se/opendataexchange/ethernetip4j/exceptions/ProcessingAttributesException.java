package se.opendataexchange.ethernetip4j.exceptions;

public class ProcessingAttributesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7859634817960610129L;
	
	/**
	 * 
	 */
	public ProcessingAttributesException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ProcessingAttributesException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ProcessingAttributesException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ProcessingAttributesException(Throwable arg0) {
		super(arg0);
	}
}
