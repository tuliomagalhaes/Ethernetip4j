package se.opendataexchange.ethernetip4j.clx;

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

public class SimpleLogixCommunicator extends ControlLogixConnector {
	EthernetIpBufferUtil incomingBuffer = new EthernetIpBufferUtil(1500);
	EthernetIpBufferUtil messageBuffer = new EthernetIpBufferUtil(1500);
	UnconnectedMessageManagerRequest request = new UnconnectedMessageManagerRequest(messageBuffer);
	UnconnectedMessageManagerResponse response = new UnconnectedMessageManagerResponse(incomingBuffer);
	
	public SimpleLogixCommunicator(String host, int port) throws IOException {
		super(host, port);
	}

	/***
	 * Read
	 * @param tagNames
	 * @return
	 * @throws PathSegmentException
	 * @throws ItemNotFoundException
	 * @throws ProcessingAttributesException
	 * @throws InsufficientCommandException
	 * @throws InsufficientNrOfAttributesException
	 * @throws OtherWithExtendedCodeException
	 * @throws ResponseBufferOverflowException
	 * @throws InvalidTypeException
	 * @throws IOException
	 * @throws EmbeddedServiceException
	 * @throws NotImplementedException 
	 */
	public Object[] read(String[] tagNames) throws PathSegmentException,
			ItemNotFoundException, ProcessingAttributesException,
			InsufficientCommandException, InsufficientNrOfAttributesException,
			OtherWithExtendedCodeException, ResponseBufferOverflowException,
			InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
				Object[] objects = null;
				if (this.sessionHandle != 0) {
					//Send SendRRData request
					synchronized (socketChannel) {
						messageBuffer.getBuffer().clear();
						request.asReadRequestBuffer(tagNames, this.sessionHandle);
						this.sendData(request.getByteBuffer());
						incomingBuffer.getBuffer().clear();
						this.receiveData(incomingBuffer.getBuffer());
					}
					//Check for response status
					//Extract object from response
					response.validate();
					objects = response.getValue();		
				}
				return objects;
			}

	public Object read(String tagName, int arraySize) throws PathSegmentException,
			ItemNotFoundException, ResponseBufferOverflowException,
			ProcessingAttributesException, InsufficientCommandException,
			InsufficientNrOfAttributesException,
			OtherWithExtendedCodeException, InvalidTypeException, IOException,
			EmbeddedServiceException, NotImplementedException {
				ArrayList<Object> lst = new ArrayList<Object>();
				
				if (this.sessionHandle != 0) {
					Object object = null;
					//Send SendRRData request
					request.asReadRequestBuffer(tagName, this.sessionHandle,arraySize);
					this.sendData(request.getByteBuffer());
					this.receiveData(incomingBuffer.getBuffer());
					//Check for response status
					//Extract object from response
					response.validate();
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
						synchronized (socketChannel) {
							request.asReadRequestBuffer(tagName, this.sessionHandle, arraySize,offset);
							this.sendData(request.getByteBuffer());
							this.receiveData(incomingBuffer.getBuffer());							
						}
						response.validate();
						object = response.getValues();
						for(Object obj:(Object[])object){
							lst.add(obj);
						}
						
						offset+=response.getPayloadSize()-2;
					}
				}
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
				//Send SendRRData request
				synchronized (socketChannel) {
					request.asWriteRequestByteBuffer(tagName, this.sessionHandle, value, arraySize);
					this.sendData(request.getByteBuffer());
					//Receive SendRRData response and check for response status
					try{
						Thread.sleep(5);
					}catch(InterruptedException e){
						
					}
					super.receiveData(incomingBuffer.getBuffer());
					response.validate();
				}				
			}

	/***
	 * Reads a tag value from the controller.
	 * @param tagName Tag name.
	 * @return Tag value.
	 * @throws PathSegmentException
	 * @throws ItemNotFoundException
	 * @throws ProcessingAttributesException
	 * @throws InsufficientCommandException
	 * @throws InsufficientNrOfAttributesException
	 * @throws OtherWithExtendedCodeException
	 * @throws ResponseBufferOverflowException
	 * @throws InvalidTypeException
	 * @throws IOException
	 * @throws EmbeddedServiceException
	 * @throws NotImplementedException 
	 */
	public Object read(String tagName) throws PathSegmentException,
			ItemNotFoundException, ProcessingAttributesException,
			InsufficientCommandException, InsufficientNrOfAttributesException,
			OtherWithExtendedCodeException, ResponseBufferOverflowException,
			InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
				Object[] objs = (Object[]) this.read(tagName,1); 
				return objs[0];
			}

}
