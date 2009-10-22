package se.opendataexchange.ethernetip4j.clx;

import java.nio.ByteBuffer;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.exceptions.EmbeddedServiceException;
import se.opendataexchange.ethernetip4j.exceptions.InsufficientCommandException;
import se.opendataexchange.ethernetip4j.exceptions.InsufficientNrOfAttributesException;
import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.ItemNotFoundException;
import se.opendataexchange.ethernetip4j.exceptions.OtherWithExtendedCodeException;
import se.opendataexchange.ethernetip4j.exceptions.PathSegmentException;
import se.opendataexchange.ethernetip4j.exceptions.ProcessingAttributesException;
import se.opendataexchange.ethernetip4j.exceptions.ResponseBufferOverflowException;
import se.opendataexchange.ethernetip4j.services.UnconnectedMessageManagerRequest;
import se.opendataexchange.ethernetip4j.services.UnconnectedMessageManagerResponse;

/***
 * 
 * Request, response
 * 
 */

public abstract class UnconnectedMessaging {
	public static final int MAX_MESSAGE_SIZE = 1500;
	
	protected UnconnectedMessageManagerRequest request = new UnconnectedMessageManagerRequest(new EthernetIpBufferUtil(MAX_MESSAGE_SIZE));
	protected UnconnectedMessageManagerResponse response = new UnconnectedMessageManagerResponse(new EthernetIpBufferUtil(MAX_MESSAGE_SIZE));
	
	public UnconnectedMessaging(){
		
	}
	
	public ByteBuffer getSendRequest(){
		return request.getByteBuffer();
	}
	
	public ByteBuffer getResponseBuffer(){
		return response.getBuffer();
	}
	
	public void validateResponse() throws PathSegmentException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, InvalidTypeException, EmbeddedServiceException{
		response.validate();
	}
}
