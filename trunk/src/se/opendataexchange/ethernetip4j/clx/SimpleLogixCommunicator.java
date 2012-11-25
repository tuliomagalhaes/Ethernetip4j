package se.opendataexchange.ethernetip4j.clx;

import java.io.IOException;
import java.util.ArrayList;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.EthernetIpDataTypeValidator;
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

/***
 * A class for communication with the plc system.
 */
public class SimpleLogixCommunicator extends ControlLogixConnector {
	EthernetIpBufferUtil messageBuffer = new EthernetIpBufferUtil(1500);
	UnconnectedMessageManagerRequest request = new UnconnectedMessageManagerRequest(messageBuffer);
	UnconnectedMessageManagerResponse response = new UnconnectedMessageManagerResponse(incomingBuffer);
	public static final int MAX_WRITE_PAYLOAD = 212;
	
	/***
	 * Constructor.
	 * @param host Address of the control system.
	 * @param port Port to use (for instance 0xAF12)
	 * @throws IOException
	 */
	public SimpleLogixCommunicator(String host, int port) throws IOException {
		super(host, port);
	}

	/***
	 * Read values of the specified tag names from the plc.
	 * 
	 * This function can not handle segmented reads, all tag names/values must fit into one packet 
	 * 
	 * @param tagNames Array of tag names.
	 * @return Tag values.
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

	/***
	 * Reads a set of tags from PLC with CPU slot in rack given. (Using CIP route). 
	 * @param tagNames
	 * @param cpuSlot
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
	
	public Object[] read(String[] tagNames, byte cpuSlot) throws PathSegmentException,
	ItemNotFoundException, ProcessingAttributesException,
	InsufficientCommandException, InsufficientNrOfAttributesException,
	OtherWithExtendedCodeException, ResponseBufferOverflowException,
	InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
		Object[] objects = null;
		if (this.sessionHandle != 0) {
			//Send SendRRData request
			synchronized (socketChannel) {
				messageBuffer.getBuffer().clear();
				request.asReadRequestBuffer(tagNames, this.sessionHandle, cpuSlot);
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

	/***
	 * Reads a set of tags from PLC with both Ethernet card and CPU slots in rack given.
	 * @param tagNames
	 * @param ethernetSlot
	 * @param cpuSlot
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
	public Object[] read(String[] tagNames, byte ethernetSlot, byte cpuSlot) throws PathSegmentException,
	ItemNotFoundException, ProcessingAttributesException,
	InsufficientCommandException, InsufficientNrOfAttributesException,
	OtherWithExtendedCodeException, ResponseBufferOverflowException,
	InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
		Object[] objects = null;
		if (this.sessionHandle != 0) {
			//Send SendRRData request
			synchronized (socketChannel) {
				messageBuffer.getBuffer().clear();
				request.asReadRequestBuffer(tagNames, this.sessionHandle, ethernetSlot, cpuSlot);
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
	
	/***
	 * Read a tag from the control system. 
	 * 
	 * Segmented transfer will occur if the value is too big to fit in a packet.
	 * 
	 * @param tagName Tag name
	 * @param arraySize Number of values (>1 if array).
	 * @return An array of tag values.
	 * @throws PathSegmentException
	 * @throws ItemNotFoundException
	 * @throws ResponseBufferOverflowException
	 * @throws ProcessingAttributesException
	 * @throws InsufficientCommandException
	 * @throws InsufficientNrOfAttributesException
	 * @throws OtherWithExtendedCodeException
	 * @throws InvalidTypeException
	 * @throws IOException
	 * @throws EmbeddedServiceException
	 * @throws NotImplementedException
	 */
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
					if(! (object instanceof Object[])){ // arraySize == 1
						lst.add(object);
					}
					else{
						for(Object obj:(Object[])object){
							lst.add(obj);
						}
					}
					int offset = response.getPayloadSize();
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
						offset+=response.getPayloadSize();
					}
				}
				Object[] objects = new Object[lst.size()];
				lst.toArray(objects);
				return objects;
			}

	public Object read(String tagName, int arraySize, byte cpuSlot) throws PathSegmentException,
	ItemNotFoundException, ResponseBufferOverflowException,
	ProcessingAttributesException, InsufficientCommandException,
	InsufficientNrOfAttributesException,
	OtherWithExtendedCodeException, InvalidTypeException, IOException,
	EmbeddedServiceException, NotImplementedException {
		ArrayList<Object> lst = new ArrayList<Object>();
		
		if (this.sessionHandle != 0) {
			Object object = null;
			//Send SendRRData request
			request.asReadRequestBuffer(tagName, this.sessionHandle,arraySize, cpuSlot);
			this.sendData(request.getByteBuffer());
			this.receiveData(incomingBuffer.getBuffer());
			//Check for response status
			//Extract object from response
			response.validate();
			object = response.getValues();
			if(! (object instanceof Object[])){ // arraySize == 1
				lst.add(object);
			}
			else{
				for(Object obj:(Object[])object){
					lst.add(obj);
				}
			}
			int offset = response.getPayloadSize();
			while(response.getGeneralStatus()==0x06){
				synchronized (socketChannel) {
					request.asReadRequestBuffer(tagName, this.sessionHandle, arraySize,offset, cpuSlot);
					this.sendData(request.getByteBuffer());
					this.receiveData(incomingBuffer.getBuffer());							
				}
				response.validate();
				object = response.getValues();
				for(Object obj:(Object[])object){
					lst.add(obj);
				}
				offset+=response.getPayloadSize();
			}
		}
		Object[] objects = new Object[lst.size()];
		lst.toArray(objects);
		return objects;
	}

	public Object read(String tagName, int arraySize, byte ethernetSlot, byte cpuSlot) throws PathSegmentException,
	ItemNotFoundException, ResponseBufferOverflowException,
	ProcessingAttributesException, InsufficientCommandException,
	InsufficientNrOfAttributesException,
	OtherWithExtendedCodeException, InvalidTypeException, IOException,
	EmbeddedServiceException, NotImplementedException {
		ArrayList<Object> lst = new ArrayList<Object>();
		
		if (this.sessionHandle != 0) {
			Object object = null;
			//Send SendRRData request
			request.asReadRequestBuffer(tagName, this.sessionHandle,arraySize, ethernetSlot, cpuSlot);
			this.sendData(request.getByteBuffer());
			this.receiveData(incomingBuffer.getBuffer());
			//Check for response status
			//Extract object from response
			response.validate();
			object = response.getValues();
			if(! (object instanceof Object[])){ // arraySize == 1
				lst.add(object);
			}
			else{
				for(Object obj:(Object[])object){
					lst.add(obj);
				}
			}
			int offset = response.getPayloadSize();
			while(response.getGeneralStatus()==0x06){
				synchronized (socketChannel) {
					request.asReadRequestBuffer(tagName, this.sessionHandle, arraySize,offset, ethernetSlot, cpuSlot);
					this.sendData(request.getByteBuffer());
					this.receiveData(incomingBuffer.getBuffer());							
				}
				response.validate();
				object = response.getValues();
				for(Object obj:(Object[])object){
					lst.add(obj);
				}
				offset+=response.getPayloadSize();
			}
		}
		Object[] objects = new Object[lst.size()];
		lst.toArray(objects);
		return objects;
	}

	/***
	 * Write a tag to the control system. 
	 * 
	 * Segmented transfer will occur if the value is too big to fit in a packet.
	 * 
	 * @param tagName Tag name.
	 * @param value Tag value(s) to write. Array of values if arraySize>1.
	 * @param arraySize Number of values (>1 if array).
	 * @throws PathSegmentException
	 * @throws InvalidTypeException
	 * @throws ItemNotFoundException
	 * @throws ResponseBufferOverflowException
	 * @throws ProcessingAttributesException
	 * @throws InsufficientCommandException
	 * @throws InsufficientNrOfAttributesException
	 * @throws OtherWithExtendedCodeException
	 * @throws IOException
	 * @throws EmbeddedServiceException
	 * @throws NotImplementedException
	 */
	public void write(String tagName, Object value, int arraySize)
	throws PathSegmentException, InvalidTypeException,
	ItemNotFoundException, ResponseBufferOverflowException,
	ProcessingAttributesException, InsufficientCommandException,
	InsufficientNrOfAttributesException,
	OtherWithExtendedCodeException, IOException,
	EmbeddedServiceException, NotImplementedException {
		//Send SendRRData request
		synchronized (socketChannel) {
			int totSize;
			int vSize;
			if (value instanceof Object[])
				vSize = EthernetIpDataTypeValidator.sizeOf(((Object[])value)[0]);
			else
				vSize = EthernetIpDataTypeValidator.sizeOf(value);
			totSize = arraySize * vSize;
			if (totSize <= MAX_WRITE_PAYLOAD){ // Normal write
				request.asWriteRequestByteBuffer(tagName, this.sessionHandle, value, arraySize);
				this.sendData(request.getByteBuffer());
				try{ Thread.sleep(5); }catch(InterruptedException e){}
				super.receiveData(incomingBuffer.getBuffer());
				response.validate();
			}else{ // fragmentation needed
				int dataOffset = 0;
				int nbrWritten = 0;
				int writeCount;
				while (nbrWritten < arraySize){
					writeCount = Math.min((MAX_WRITE_PAYLOAD/vSize)-1, arraySize-nbrWritten); // Max in 1 packet or all values left
					request.asWriteRequestByteBuffer(tagName, this.sessionHandle, value, arraySize, dataOffset, writeCount);
					this.sendData(request.getByteBuffer());
					super.receiveData(incomingBuffer.getBuffer());
					response.validate();
					nbrWritten += writeCount;
					dataOffset += vSize*writeCount;
				}
			}
		}				
	}
	
	/***
	 * Writes a tag to the PLC with the CPU slot given
	 * @param tagName
	 * @param value
	 * @param arraySize
	 * @param routePathLinkedAddress
	 * @throws PathSegmentException
	 * @throws InvalidTypeException
	 * @throws ItemNotFoundException
	 * @throws ResponseBufferOverflowException
	 * @throws ProcessingAttributesException
	 * @throws InsufficientCommandException
	 * @throws InsufficientNrOfAttributesException
	 * @throws OtherWithExtendedCodeException
	 * @throws IOException
	 * @throws EmbeddedServiceException
	 * @throws NotImplementedException
	 */
	public void write(String tagName, Object value, int arraySize, byte routePathLinkedAddress)
	throws PathSegmentException, InvalidTypeException,
	ItemNotFoundException, ResponseBufferOverflowException,
	ProcessingAttributesException, InsufficientCommandException,
	InsufficientNrOfAttributesException,
	OtherWithExtendedCodeException, IOException,
	EmbeddedServiceException, NotImplementedException {
		//Send SendRRData request
		synchronized (socketChannel) {
			int totSize;
			int vSize;
			if (value instanceof Object[])
				vSize = EthernetIpDataTypeValidator.sizeOf(((Object[])value)[0]);
			else
				vSize = EthernetIpDataTypeValidator.sizeOf(value);
			totSize = arraySize * vSize;
			if (totSize <= MAX_WRITE_PAYLOAD){ // Normal write
				request.asWriteRequestByteBuffer(tagName, this.sessionHandle, value, arraySize, routePathLinkedAddress);
				this.sendData(request.getByteBuffer());
				try{ Thread.sleep(5); }catch(InterruptedException e){}
				super.receiveData(incomingBuffer.getBuffer());
				response.validate();
			}else{ // fragmentation needed
				int dataOffset = 0;
				int nbrWritten = 0;
				int writeCount;
				while (nbrWritten < arraySize){
					writeCount = Math.min((MAX_WRITE_PAYLOAD/vSize)-1, arraySize-nbrWritten); // Max in 1 packet or all values left
					request.asWriteRequestByteBuffer(tagName, this.sessionHandle, value, arraySize, dataOffset, writeCount, routePathLinkedAddress);
					this.sendData(request.getByteBuffer());
					super.receiveData(incomingBuffer.getBuffer());
					response.validate();
					nbrWritten += writeCount;
					dataOffset += vSize*writeCount;
				}
			}
		}				
	}
	/***
	 * Writes a tag value to the PLC with both the Ethernet and CPU slots given. 
	 * @param tagName
	 * @param value
	 * @param arraySize
	 * @param routePathPort
	 * @param routePathLinkedAddress
	 * @throws PathSegmentException
	 * @throws InvalidTypeException
	 * @throws ItemNotFoundException
	 * @throws ResponseBufferOverflowException
	 * @throws ProcessingAttributesException
	 * @throws InsufficientCommandException
	 * @throws InsufficientNrOfAttributesException
	 * @throws OtherWithExtendedCodeException
	 * @throws IOException
	 * @throws EmbeddedServiceException
	 * @throws NotImplementedException
	 */
	public void write(String tagName, Object value, int arraySize,byte routePathPort, byte routePathLinkedAddress)
	throws PathSegmentException, InvalidTypeException,
	ItemNotFoundException, ResponseBufferOverflowException,
	ProcessingAttributesException, InsufficientCommandException,
	InsufficientNrOfAttributesException,
	OtherWithExtendedCodeException, IOException,
	EmbeddedServiceException, NotImplementedException {
		//Send SendRRData request
		synchronized (socketChannel) {
			int totSize;
			int vSize;
			if (value instanceof Object[])
				vSize = EthernetIpDataTypeValidator.sizeOf(((Object[])value)[0]);
			else
				vSize = EthernetIpDataTypeValidator.sizeOf(value);
			totSize = arraySize * vSize;
			if (totSize <= MAX_WRITE_PAYLOAD){ // Normal write
				request.asWriteRequestByteBuffer(tagName, this.sessionHandle, value, arraySize, routePathPort, routePathLinkedAddress);
				this.sendData(request.getByteBuffer());
				try{ Thread.sleep(5); }catch(InterruptedException e){}
				super.receiveData(incomingBuffer.getBuffer());
				response.validate();
			}else{ // fragmentation needed
				int dataOffset = 0;
				int nbrWritten = 0;
				int writeCount;
				while (nbrWritten < arraySize){
					writeCount = Math.min((MAX_WRITE_PAYLOAD/vSize)-1, arraySize-nbrWritten); // Max in 1 packet or all values left
					request.asWriteRequestByteBuffer(tagName, this.sessionHandle, value, arraySize, dataOffset, writeCount, routePathPort, routePathLinkedAddress);
					this.sendData(request.getByteBuffer());
					super.receiveData(incomingBuffer.getBuffer());
					response.validate();
					nbrWritten += writeCount;
					dataOffset += vSize*writeCount;
				}
			}
		}				
	}

	/***
	 * Reads a tag value from the controller (if an array is specified, the first element will be read).
	 * 
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
	/***
	 * 
	 * @param tagName, Tag name
	 * @param cpuSlot, slot in Control Logix rack for CPU.
	 * @return Tag value
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
	public Object read(String tagName, byte cpuSlot) throws PathSegmentException,
	ItemNotFoundException, ProcessingAttributesException,
	InsufficientCommandException, InsufficientNrOfAttributesException,
	OtherWithExtendedCodeException, ResponseBufferOverflowException,
	InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
		Object[] objs = (Object[]) this.read(tagName,1,cpuSlot); 
		return objs[0];
	}
	
	/***
	 * Reads a tag specifying route in rack
	 * @param tagName, Tag name
	 * @param ethernetSlot, slot in Control Logix rack for ethernet card
	 * @param cpuSlot, slot slot in Control Logix rack for CPU
	 * @return Tag value
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
	public Object read(String tagName,byte ethernetSlot,byte cpuSlot) throws PathSegmentException,
	ItemNotFoundException, ProcessingAttributesException,
	InsufficientCommandException, InsufficientNrOfAttributesException,
	OtherWithExtendedCodeException, ResponseBufferOverflowException,
	InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
		Object[] objs = (Object[]) this.read(tagName,1,ethernetSlot,cpuSlot); 
		return objs[0];
	}
	
	/***
	 * Writes a value to the CPU
	 * @param tagName, Tag name
	 * @param value, Tag value
	 * @throws PathSegmentException
	 * @throws InvalidTypeException
	 * @throws ItemNotFoundException
	 * @throws ResponseBufferOverflowException
	 * @throws ProcessingAttributesException
	 * @throws InsufficientCommandException
	 * @throws InsufficientNrOfAttributesException
	 * @throws OtherWithExtendedCodeException
	 * @throws IOException
	 * @throws EmbeddedServiceException
	 * @throws NotImplementedException
	 */
	public void write(String tagName, Object value)
	throws PathSegmentException, InvalidTypeException,
	ItemNotFoundException, ResponseBufferOverflowException,
	ProcessingAttributesException, InsufficientCommandException,
	InsufficientNrOfAttributesException,
	OtherWithExtendedCodeException, IOException,
	EmbeddedServiceException, NotImplementedException {
		//Send SendRRData request
		write(tagName,value,1);
	}
	
	/***
	 * Writes a value to a CPU at a certain slot in the Control Logix rack
	 * @param tagName, Tag name
	 * @param value, Tag value
	 * @param cpuSlot, Slot in rack for CPU
	 * @throws PathSegmentException
	 * @throws InvalidTypeException
	 * @throws ItemNotFoundException
	 * @throws ResponseBufferOverflowException
	 * @throws ProcessingAttributesException
	 * @throws InsufficientCommandException
	 * @throws InsufficientNrOfAttributesException
	 * @throws OtherWithExtendedCodeException
	 * @throws IOException
	 * @throws EmbeddedServiceException
	 * @throws NotImplementedException
	 */
	public void write(String tagName, Object value, byte cpuSlot)
	throws PathSegmentException, InvalidTypeException,
	ItemNotFoundException, ResponseBufferOverflowException,
	ProcessingAttributesException, InsufficientCommandException,
	InsufficientNrOfAttributesException,
	OtherWithExtendedCodeException, IOException,
	EmbeddedServiceException, NotImplementedException {
		//Send SendRRData request
		write(tagName,value,1,cpuSlot);
	}
	
	/***
	 * Writes a value to a CPU in a certain slot in a Control Logix rack from an Ethernet card in a certain slot
	 * @param tagName
	 * @param value
	 * @param ethernetSlot
	 * @param cpuSlot
	 * @throws PathSegmentException
	 * @throws InvalidTypeException
	 * @throws ItemNotFoundException
	 * @throws ResponseBufferOverflowException
	 * @throws ProcessingAttributesException
	 * @throws InsufficientCommandException
	 * @throws InsufficientNrOfAttributesException
	 * @throws OtherWithExtendedCodeException
	 * @throws IOException
	 * @throws EmbeddedServiceException
	 * @throws NotImplementedException
	 */
	public void write(String tagName, Object value, byte ethernetSlot, byte cpuSlot)
	throws PathSegmentException, InvalidTypeException,
	ItemNotFoundException, ResponseBufferOverflowException,
	ProcessingAttributesException, InsufficientCommandException,
	InsufficientNrOfAttributesException,
	OtherWithExtendedCodeException, IOException,
	EmbeddedServiceException, NotImplementedException {
		//Send SendRRData request
		write(tagName,value,1,ethernetSlot,cpuSlot);
	}
	
	
}