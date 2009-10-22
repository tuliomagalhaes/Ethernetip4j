package se.opendataexchange.ethernetip4j.clx;

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

public class UnconnectedMessagingRead extends UnconnectedMessaging{
	public UnconnectedMessagingRead(String tagName, long sessionHandle) throws NotImplementedException{
		super();
		request.asReadRequestBuffer(tagName, sessionHandle, 1);
	}
	
	public UnconnectedMessagingRead(String[] tagNames, long sessionHandle) throws NotImplementedException{
		super();
		request.asReadRequestBuffer(tagNames, sessionHandle);
	}
	
	public UnconnectedMessagingRead(String tagName,int arraySize, long sessionHandle) throws NotImplementedException{
		super();
		request.asReadRequestBuffer(tagName, sessionHandle, arraySize);
	}
	
	/***
	 * 
	 * @return
	 */
	public Object[] getValue(){
		try {
			response.validate();
			return response.getValue();
		} catch (PathSegmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ItemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResponseBufferOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProcessingAttributesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientNrOfAttributesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OtherWithExtendedCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmbeddedServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	public Object getValues(){
		try {
			response.validate();
			return response.getValues();
		} catch (PathSegmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ItemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResponseBufferOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProcessingAttributesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientNrOfAttributesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OtherWithExtendedCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmbeddedServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
}
