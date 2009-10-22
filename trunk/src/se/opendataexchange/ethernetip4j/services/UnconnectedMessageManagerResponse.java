package se.opendataexchange.ethernetip4j.services;

import java.nio.ByteBuffer;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.exceptions.EmbeddedServiceException;
import se.opendataexchange.ethernetip4j.exceptions.InsufficientCommandException;
import se.opendataexchange.ethernetip4j.exceptions.InsufficientNrOfAttributesException;
import se.opendataexchange.ethernetip4j.exceptions.InvalidEncapsulationPackageException;
import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.ItemNotFoundException;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;
import se.opendataexchange.ethernetip4j.exceptions.OtherWithExtendedCodeException;
import se.opendataexchange.ethernetip4j.exceptions.PathSegmentException;
import se.opendataexchange.ethernetip4j.exceptions.ProcessingAttributesException;
import se.opendataexchange.ethernetip4j.exceptions.ResponseBufferOverflowException;
import se.opendataexchange.ethernetip4j.segments.CipPacketResponse;
import se.opendataexchange.ethernetip4j.segments.EthernetIpCommandSpecificData;
import se.opendataexchange.ethernetip4j.segments.EthernetIpEncapsulationHeader;
import se.opendataexchange.ethernetip4j.segments.EthernetIpItemStruct;

/***
 * 
 * This class is used for interpreting read- and write CIP responses in ControlLogix communication. 
 * 
 * The "Logix5000 Data Access" and the TCP/IP specification have been used to determine the packet structures.
 * 
 * <table border="1">
 * <tr><th>Byte</th><th>	Segment</th><th>							Type</th></tr>
 * <tr><td>0-23</td><td>	Header</td><td>								{@link EthernetIpEncapsulationHeader}</td></tr>
 * <tr><td>24-35</td><td>	Command specific data</td><td> 				{@link EthernetIpCommandSpecificData}</td></tr>
 * <tr><td>36-39</td><td>	Data item</td><td>		 					{@link EthernetIpItemStruct}</td></tr>
 * <tr><td>40-45</td><td>	CIP packet response</td><td> 				{@link CipPacketResponse}</td></tr>
 * </table>
 * 
 */
public class UnconnectedMessageManagerResponse {
	EthernetIpBufferUtil buffer;
    CipPacketResponse response;
    
    public UnconnectedMessageManagerResponse(EthernetIpBufferUtil byteBuffer){
		buffer = byteBuffer;
	}
	
    public void validate() throws PathSegmentException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, InvalidTypeException, EmbeddedServiceException{
		try { // validate
			EthernetIpEncapsulationHeader.validate(buffer);
			response = CipPacketResponse.createPackage(buffer);
		} catch (InvalidEncapsulationPackageException e) {
			e.printStackTrace();
		}
	}
	
	public Object getValue(int count) throws InvalidTypeException, NotImplementedException {
		return response.getValue(count);
	}
		
	public Object[] getValue() throws InvalidTypeException {
		return response.getValue();
	}
	
	public Object getValues() throws InvalidTypeException {
		return response.getValues();
	}
	
	public int getPayloadSize() {
		return response.getPayloadSize();
	}
	
	public byte getGeneralStatus(){
		return response.getGeneralStatus();
	}
	
	public ByteBuffer getBuffer(){
		return buffer.getBuffer();
	}
}
