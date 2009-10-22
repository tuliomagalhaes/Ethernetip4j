package se.opendataexchange.ethernetip4j.test;

import java.io.IOException;
import java.util.ArrayList;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.exceptions.EmbeddedServiceException;
import se.opendataexchange.ethernetip4j.exceptions.InsufficientCommandException;
import se.opendataexchange.ethernetip4j.exceptions.InsufficientNrOfAttributesException;
import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.ItemNotFoundException;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;
import se.opendataexchange.ethernetip4j.exceptions.OtherWithExtendedCodeException;
import se.opendataexchange.ethernetip4j.exceptions.PathSegmentException;
import se.opendataexchange.ethernetip4j.exceptions.ProcessingAttributesException;
import se.opendataexchange.ethernetip4j.exceptions.ResponseBufferOverflowException;
import se.opendataexchange.ethernetip4j.services.UnconnectedMessageManagerRequest;
import se.opendataexchange.ethernetip4j.services.UnconnectedMessageManagerResponse;

public class ControlLogixMockDataSource{
	EthernetIpBufferUtil messageBuffer = new EthernetIpBufferUtil(512);
	EthernetIpBufferUtil incomingBuffer = new EthernetIpBufferUtil(512);
	public ControlLogixMockDataSource(){
		
	}
	
	private long sessionHandle = 1;
	
	public Object read(String tagName) throws PathSegmentException,
			ItemNotFoundException, ProcessingAttributesException,
			InsufficientCommandException, InsufficientNrOfAttributesException,
			OtherWithExtendedCodeException, ResponseBufferOverflowException,
			InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
		
		Object[] objs = (Object[]) this.read(tagName,1); 
		return objs[0];
		
	}

	public Object[] read(String[] tagNames) throws PathSegmentException,
			ItemNotFoundException, ProcessingAttributesException,
			InsufficientCommandException, InsufficientNrOfAttributesException,
			OtherWithExtendedCodeException, ResponseBufferOverflowException,
			InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
		
		Object[] objects = null;
			UnconnectedMessageManagerResponse response = null;
			new UnconnectedMessageManagerRequest(messageBuffer).asReadRequestBuffer(tagNames, sessionHandle);
			response = new UnconnectedMessageManagerResponse(receiveData1());
			objects = response.getValue();
			
		return objects;
	}

	private EthernetIpBufferUtil receiveData1() {
		incomingBuffer.putByteArray(0, new byte[]{(byte)0x6F, (byte)0x00, (byte)0x1A, (byte)0x00, (byte)0x15, (byte)0xCD, (byte)0x5B, (byte)0x07, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xCD, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x10, (byte)0x00, 
				(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0xCC, (byte)0x00, (byte)0x00, (byte)0x05, (byte)0x00, (byte)0xCA, (byte)0xCD, (byte)0xCC, (byte)0x0C, (byte)0x40
				});
		return incomingBuffer;
	}

	public Object read(String tagName, int arraySize)
			throws PathSegmentException, ItemNotFoundException,
			ResponseBufferOverflowException, ProcessingAttributesException,
			InsufficientCommandException, InsufficientNrOfAttributesException,
			OtherWithExtendedCodeException, InvalidTypeException, IOException,
			EmbeddedServiceException, NotImplementedException {
		ArrayList<Object> lst = new ArrayList<Object>();
		
		Object object = null;
		//Send SendRRData request
		new UnconnectedMessageManagerRequest(messageBuffer).asReadRequestBuffer(tagName, sessionHandle,arraySize);
		UnconnectedMessageManagerResponse response = new UnconnectedMessageManagerResponse(this.receiveData1());
		//Check for response status
		//Extract object from response
		object = response.getValues();
		if(arraySize==1){
			lst.add(object);
		}
		else{
			for(Object obj:(Object[])object){
				lst.add(obj);
			}
		}
		int offset = response.getPayloadSize()-2;
		while(response.getGeneralStatus()==0x06){
			new UnconnectedMessageManagerRequest(messageBuffer).asReadRequestBuffer(tagName, this.sessionHandle, arraySize,offset);
			response = new UnconnectedMessageManagerResponse(this.receiveData1());
		}
		object = response.getValues();
		// arrayify
		if (! (object instanceof Object[])){
			object = new Object[]{object};
		}
		
		for(Object obj:(Object[])object){
			lst.add(obj);
		}
		offset+=response.getPayloadSize()-2;
		Object[] objects = new Object[lst.size()];
		lst.toArray(objects);
		return objects;
	}

	public void write(String tagName, Object value, int arraySize)
			throws PathSegmentException, InvalidTypeException,
			ItemNotFoundException, ResponseBufferOverflowException,
			ProcessingAttributesException, InsufficientCommandException,
			InsufficientNrOfAttributesException,
			OtherWithExtendedCodeException, IOException,
			EmbeddedServiceException, NotImplementedException {
		
		new UnconnectedMessageManagerRequest(messageBuffer).asWriteRequestByteBuffer(tagName, this.sessionHandle, value, arraySize);
		//Receive SendRRData response and check for response status
		try{
			Thread.sleep(5);
		}catch(InterruptedException e){
			
		}
		new UnconnectedMessageManagerResponse(this.receiveData1());
	}
}
