package se.opendataexchange.ethernetip4j.exceptions;

public class PathSegmentException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1925160532004284783L;
	
	/**
	 * 
	 */
	public PathSegmentException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public PathSegmentException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public PathSegmentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public PathSegmentException(Throwable arg0) {
		super(arg0);
	}
}
