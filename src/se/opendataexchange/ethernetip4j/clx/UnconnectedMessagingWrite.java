package se.opendataexchange.ethernetip4j.clx;

import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;

public class UnconnectedMessagingWrite extends UnconnectedMessaging {
	public UnconnectedMessagingWrite(String tagName, Object value, long sessionHandle) throws InvalidTypeException, NotImplementedException{
		super();
		request.asWriteRequestByteBuffer(tagName, sessionHandle, value, 1);		
	}
	
	public UnconnectedMessagingWrite(String tagName, Object value, long sessionHandle, int arraySize) throws InvalidTypeException, NotImplementedException
	{
		super();
		request.asWriteRequestByteBuffer(tagName, sessionHandle, value, arraySize);
	}
}
