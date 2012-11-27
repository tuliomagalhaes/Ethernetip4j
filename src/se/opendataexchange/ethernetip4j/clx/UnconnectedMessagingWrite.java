package se.opendataexchange.ethernetip4j.clx;

import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;

public class UnconnectedMessagingWrite extends UnconnectedMessaging {
	public UnconnectedMessagingWrite(String tagName, Object value, long sessionHandle) throws InvalidTypeException, NotImplementedException{
		super();
		request.asWriteRequestByteBuffer(tagName, sessionHandle, value, 1);		
	}
	
	public UnconnectedMessagingWrite(String tagName, Object value[], int arraySize, long sessionHandle, byte routePathLinkedAddress) throws InvalidTypeException, NotImplementedException {
		super();
		request.asWriteRequestByteBuffer(tagName, sessionHandle, value, arraySize, routePathLinkedAddress);
	}

	public UnconnectedMessagingWrite(String tagName, Object value[], int arraySize, long sessionHandle, byte routePathPort, byte routePathLinkedAddress) throws InvalidTypeException, NotImplementedException {
		super();
		request.asWriteRequestByteBuffer(tagName, sessionHandle, value, arraySize, routePathPort, routePathLinkedAddress);
	}

	public UnconnectedMessagingWrite(String tagName, Object value[], int arraySize, long sessionHandle, int offset, int writeCount) throws InvalidTypeException, NotImplementedException {
		super();
		request.asWriteRequestByteBuffer(tagName, sessionHandle, value, arraySize, offset, writeCount);
	}

	public UnconnectedMessagingWrite(String tagName, Object value[], int arraySize, long sessionHandle, int offset, int writeCount, byte routePathLinkedAddress) throws InvalidTypeException, NotImplementedException {
		super();
		request.asWriteRequestByteBuffer(tagName, sessionHandle, value, arraySize, offset, writeCount, routePathLinkedAddress);
	}

	public UnconnectedMessagingWrite(String tagName, Object value[], int arraySize, long sessionHandle, int offset, int writeCount, byte routePathPort, byte routePathLinkedAddress) throws InvalidTypeException, NotImplementedException {
		super();
		request.asWriteRequestByteBuffer(tagName, sessionHandle, value, arraySize, offset, writeCount, routePathPort, routePathLinkedAddress);
	}
}
