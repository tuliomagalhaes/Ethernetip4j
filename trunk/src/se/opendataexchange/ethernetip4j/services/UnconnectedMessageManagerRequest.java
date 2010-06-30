package se.opendataexchange.ethernetip4j.services;

import java.nio.ByteBuffer;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;
import se.opendataexchange.ethernetip4j.exceptions.TooLongMessageException;
import se.opendataexchange.ethernetip4j.segments.CipCommandSpecificDataRequest;
import se.opendataexchange.ethernetip4j.segments.CipMessageRouterRequest;
import se.opendataexchange.ethernetip4j.segments.CipMultipleMessageRouterRequest;
import se.opendataexchange.ethernetip4j.segments.CipPacketRequest;
import se.opendataexchange.ethernetip4j.segments.EthernetIpCommandSpecificData;
import se.opendataexchange.ethernetip4j.segments.EthernetIpEncapsulationHeader;
import se.opendataexchange.ethernetip4j.segments.EthernetIpItemStruct;

/**
 * This class is used for creating read- and write CIP requests for ControlLogix communication. 
 * 
 * The "Logix5000 Data Access" and the TCP/IP specification have been used to determine the packet structures.
 * 
 * <table border="1">
 * <tr><th>Byte</th><th>	Segment</th><th>							Type</th></tr>
 * <tr><td>0-23</td><td>	Header</td><td>								{@link EthernetIpEncapsulationHeader}</td></tr>
 * <tr><td>24-35</td><td>	Command specific data</td><td> 				{@link EthernetIpCommandSpecificData}</td></tr>
 * <tr><td>36-39</td><td>	Data item</td><td>		 					{@link EthernetIpItemStruct}</td></tr>
 * <tr><td>40-45</td><td>	CIP packet request</td><td> 				{@link CipPacketRequest}</td></tr>
 * <tr><td>46-53</td><td>	CIP command specific data request</td><td> 	{@link CipCommandSpecificDataRequest}</td></tr>
 * </table>
 * 
 */
public class UnconnectedMessageManagerRequest {
	EthernetIpBufferUtil messageBuffer;
	int cipLength;
	
	private static final int MESSAGE_REQUEST_BASE_LENGTH = 
		EthernetIpEncapsulationHeader.SEGMENT_LENGTH + 
		EthernetIpCommandSpecificData.SEGMENT_LENGTH +
		EthernetIpItemStruct.SEGMENT_LENGTH;
		
	public UnconnectedMessageManagerRequest(EthernetIpBufferUtil messageBuffer){
		this.messageBuffer = messageBuffer;
	}
	
	private void buildHeader(long sessionHandle, int messageLength){
		EthernetIpEncapsulationHeader.setCommand(messageBuffer, EthernetIpCommandServices.SEND_RR_DATA);
		EthernetIpEncapsulationHeader.setLength(messageBuffer, messageLength - EthernetIpEncapsulationHeader.SEGMENT_LENGTH);
		EthernetIpEncapsulationHeader.setStatus(messageBuffer, 0);
		EthernetIpEncapsulationHeader.setSessionHandle(messageBuffer, sessionHandle);
		EthernetIpEncapsulationHeader.setSenderContext(messageBuffer, new byte[] {0, 0, 0, 0, 0, 0, 0, 0});
		EthernetIpEncapsulationHeader.setOptions(messageBuffer, 0);
	}
	
	public void asReadRequestBuffer(String tagName, long sessionHandle, int arraySize) throws NotImplementedException{
		cipLength = CipMessageRouterRequest.getSegmentLength(CipCommandServices.CIP_READ_DATA, tagName, null, 0)
			+CipPacketRequest.SEGMENT_LENGTH + CipCommandSpecificDataRequest.SEGMENT_LENGTH;
		
		messageBuffer.getBuffer().clear();
		messageBuffer.getBuffer().limit(MESSAGE_REQUEST_BASE_LENGTH + cipLength);
		
		try {
			int segmentLength = CipMessageRouterRequest.fillBuffer(CipCommandServices.CIP_READ_DATA, tagName, null, arraySize, messageBuffer);
			CipCommandSpecificDataRequest.fillBuffer(segmentLength, messageBuffer);
			CipPacketRequest.fillBuffer(messageBuffer);
			EthernetIpItemStruct.fillDataItem(cipLength, messageBuffer);
			EthernetIpCommandSpecificData.fillBuffer(messageBuffer);
		} catch (InvalidTypeException e) {
			e.printStackTrace();
		}
		buildHeader(sessionHandle, MESSAGE_REQUEST_BASE_LENGTH + cipLength);
	}
	
	public void asReadRequestBuffer(String tagName, long sessionHandle, int arraySize, int offset) throws NotImplementedException{
		cipLength = CipMessageRouterRequest.getSegmentLength(CipCommandServices.CIP_READ_FRAGMENT, tagName, null, 0)
			+CipPacketRequest.SEGMENT_LENGTH + CipCommandSpecificDataRequest.SEGMENT_LENGTH;
		
		messageBuffer.getBuffer().clear();
		messageBuffer.getBuffer().limit(MESSAGE_REQUEST_BASE_LENGTH + cipLength);
		
		try{
			int segmentLength = CipMessageRouterRequest.fillBuffer(CipCommandServices.CIP_READ_FRAGMENT, tagName, null,arraySize,offset,  messageBuffer);
			CipCommandSpecificDataRequest.fillBuffer(segmentLength, messageBuffer);	
			CipPacketRequest.fillBuffer(messageBuffer);
			EthernetIpItemStruct.fillDataItem(cipLength, messageBuffer);
			EthernetIpCommandSpecificData.fillBuffer(messageBuffer);
		}catch (InvalidTypeException e) {
			e.printStackTrace();
		}
		buildHeader(sessionHandle, MESSAGE_REQUEST_BASE_LENGTH + cipLength);
	}
	
	public void asReadRequestBuffer(String[] tagNames, long sessionHandle) throws NotImplementedException{
		cipLength = CipMultipleMessageRouterRequest.getSegmentLength(tagNames)
			+CipPacketRequest.SEGMENT_LENGTH + CipCommandSpecificDataRequest.SEGMENT_LENGTH;

		messageBuffer.getBuffer().clear();
		messageBuffer.getBuffer().limit(MESSAGE_REQUEST_BASE_LENGTH + cipLength);
		
		try{
			int segmentLength = CipMultipleMessageRouterRequest.fillBuffer(tagNames,null, messageBuffer);
			CipCommandSpecificDataRequest.fillBuffer(segmentLength, messageBuffer);	
			CipPacketRequest.fillBuffer(messageBuffer);
			EthernetIpItemStruct.fillDataItem(cipLength, messageBuffer);
			EthernetIpCommandSpecificData.fillBuffer(messageBuffer);
		}catch(TooLongMessageException e){
			e.printStackTrace();
		}
		buildHeader(sessionHandle, MESSAGE_REQUEST_BASE_LENGTH + cipLength);
	}
	
	public void asWriteRequestByteBuffer(String tagName, long sessionHandle, Object value, int arraySize) throws InvalidTypeException, NotImplementedException{
		cipLength = CipMessageRouterRequest.getSegmentLength(CipCommandServices.CIP_WRITE_DATA, tagName, value, arraySize)
		+CipPacketRequest.SEGMENT_LENGTH + CipCommandSpecificDataRequest.SEGMENT_LENGTH;
		
		messageBuffer.getBuffer().clear();
		messageBuffer.getBuffer().limit(MESSAGE_REQUEST_BASE_LENGTH + cipLength);
		
		int segmentLength = CipMessageRouterRequest.fillBuffer(CipCommandServices.CIP_WRITE_DATA, tagName, value,arraySize, messageBuffer);
		CipCommandSpecificDataRequest.fillBuffer(segmentLength, messageBuffer);		
		CipPacketRequest.fillBuffer(messageBuffer);
		EthernetIpItemStruct.fillDataItem(cipLength, messageBuffer);
		EthernetIpCommandSpecificData.fillBuffer(messageBuffer);
		
		buildHeader(sessionHandle, MESSAGE_REQUEST_BASE_LENGTH + cipLength);
	}
	
	/***
	 * Fragmented read
	 * @param tagName
	 * @param sessionHandle
	 * @param value
	 * @param arraySize
	 * @param offset
	 * @param writeCount 
	 * @throws InvalidTypeException
	 * @throws NotImplementedException
	 */
	public void asWriteRequestByteBuffer(String tagName, long sessionHandle, Object value, int arraySize, int offset, int writeCount) throws InvalidTypeException, NotImplementedException{
		cipLength = CipMessageRouterRequest.getSegmentLength(CipCommandServices.CIP_WRITE_FRAGMENT, tagName, value, writeCount)
		+CipPacketRequest.SEGMENT_LENGTH + CipCommandSpecificDataRequest.SEGMENT_LENGTH;
		
		messageBuffer.getBuffer().clear();
		messageBuffer.getBuffer().limit(MESSAGE_REQUEST_BASE_LENGTH + cipLength);
		
		int segmentLength = CipMessageRouterRequest.fillBuffer(CipCommandServices.CIP_WRITE_FRAGMENT, tagName, value,arraySize, offset, writeCount, messageBuffer);
		CipCommandSpecificDataRequest.fillBuffer(segmentLength, messageBuffer);		
		CipPacketRequest.fillBuffer(messageBuffer);
		EthernetIpItemStruct.fillDataItem(cipLength, messageBuffer);
		EthernetIpCommandSpecificData.fillBuffer(messageBuffer);
		
		buildHeader(sessionHandle, MESSAGE_REQUEST_BASE_LENGTH + cipLength);
	}
	
	public int getPacketLength(){
		return messageBuffer.getBufferLength();
	}
	
	public ByteBuffer getByteBuffer(){
		return messageBuffer.getBuffer();
	}	
}
