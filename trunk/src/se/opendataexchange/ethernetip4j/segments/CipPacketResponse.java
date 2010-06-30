package se.opendataexchange.ethernetip4j.segments;

import java.security.InvalidParameterException;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.HexConverter;
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
import se.opendataexchange.ethernetip4j.services.CipCommandServices;
/***
 * <table border="1">
 * <tr><th>Byte</th><th>	Name</th><th>									Type</th><th>																			Description</th></tr>
 * <tr><td>0</td><td>		Service</td><td> 								USINT</td><td>																			{@link CipCommandServices}</td></tr>
 * <tr><td>2</td><td>		General status</td><td>							USINT</td><td> 																			0x00 if all ok</td></tr>
 * <tr><td>2</td><td>		Message router response</td><td> 				{@link CipMessageRouterResponse} or {@link CipMultipleMessageRouterResponse}</td><td> 	0x20 by default</td></tr>
 * <tr><td>3</td><td>		Request path class</td><td> 					Byte</td><td> 							0x06 by default</td></tr>
 * <tr><td>4</td><td>		Request path logical instance segment</td><td> 	Byte</td><td> 							0x24 by default</td></tr>
 * <tr><td>5</td><td>		Request path instance</td><td> 					Byte</td><td> 							0x01 by default</td></tr>
 * </table>
 */
public class CipPacketResponse {
	private EthernetIpBufferUtil buffer;
	public static final int SEGMENT_LENGTH = 4;
	
	private int offset =
		EthernetIpEncapsulationHeader.SEGMENT_LENGTH + 
		EthernetIpCommandSpecificData.SEGMENT_LENGTH +
		EthernetIpItemStruct.SEGMENT_LENGTH;
	
	private CipMessageRouterResponse getMessageRouterResponse()
	{
		try {
			short s = this.getService();
			if(s == 0xCC || s==0xD2){ //CC=read, D2=fragmented read
				return CipMessageRouterResponse.createPackage(buffer);
			}else if(s == 0x8A){
				//Indicates multiple reads.
				return null; //TODO: implement CipMultipleMessageRouterResponse.createPackage(buffer);
			}
		} catch (InvalidEncapsulationPackageException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private CipMultipleMessageRouterResponse getMultipleMessageRouterResponse() throws InvalidTypeException
	{
		try {
			return CipMultipleMessageRouterResponse.createPackage(buffer);
		} catch (InvalidEncapsulationPackageException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private CipPacketResponse(EthernetIpBufferUtil incomingBuffer) throws PathSegmentException, ItemNotFoundException, 
	ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, 
	InsufficientNrOfAttributesException, OtherWithExtendedCodeException, InvalidEncapsulationPackageException, 
	InvalidTypeException, EmbeddedServiceException{
		this.buffer = incomingBuffer;		
	}
	
	public static CipPacketResponse createPackage(EthernetIpBufferUtil incomingBuffer)throws InvalidEncapsulationPackageException, 
	PathSegmentException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, 
	InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, 
	InvalidTypeException, EmbeddedServiceException{
		if(incomingBuffer.getBufferLength()<EthernetIpEncapsulationHeader.SEGMENT_LENGTH + 
				EthernetIpCommandSpecificData.SEGMENT_LENGTH +
				EthernetIpItemStruct.SEGMENT_LENGTH) 
			throw new InvalidEncapsulationPackageException("Package can not be a valid Ethernet/IP Cip package.");
		
		CipPacketResponse res = new CipPacketResponse(incomingBuffer);
		res.buffer = incomingBuffer;
		if(res.checkStatus())
			return res;
		else
			return null;
	}
	
	private boolean checkStatus() throws PathSegmentException,
			ItemNotFoundException, ResponseBufferOverflowException,
			ProcessingAttributesException, InsufficientCommandException,
			InsufficientNrOfAttributesException, OtherWithExtendedCodeException,EmbeddedServiceException {
		byte error = getGeneralStatus();
		short service = getService();
		switch (error) {
		case 0x04:
			throw new PathSegmentException("The IOI could not be deciphered. Either it was not formed correctly or the match tag does not exist. Error code: 0x04");
		case 0x05:
			throw new ItemNotFoundException("The particular item referenced (usually instance) could not be found. Error code: 0x05");
		case 0x06:
			if((service!=0xD2)&&(service!=0xCC))
				throw new ResponseBufferOverflowException("The amount of data requested would not fit into the response buffer. Partial data transfer has occurred. Error code: 0x06");
			else{
				// Fragmented read, more is to come
				return true;
			}
		case 0x0A:
			throw new ProcessingAttributesException("An error has occurred trying to process one of the attributes. Error code: 0x0A");
		case 0x13:
			throw new InsufficientCommandException("Not enough command data / parameters were supplied in the command to execute the service requested. Error code: 0x13");
		case 0x1C:
			throw new InsufficientNrOfAttributesException("An insufficient number of attributes were provided compared to the attribute count. Error code: 0x1C");
		case 0x1E:
			throw new EmbeddedServiceException("A service request in this service went wrong. Error code: 0x1E");
		case 0x26:
			throw new InvalidParameterException("The IOI word length did not match the amount of IOI which was processed. Error code: 0x26");
		case 0x00:
			return true;
		case (byte) 0xFF:
			StringBuilder sb = new StringBuilder();
			for(int i=2; i<6; i++)
				sb.append(HexConverter.byte2hex(buffer.getByte(offset + i))).append(" ");
			throw new OtherWithExtendedCodeException("Error with extended status. Error code: 0xFF. Bytes: " + sb.toString());					
		default:
			throw new OtherWithExtendedCodeException("Error with extended status: "+error);
		}
	}
	
	public short getService() {
		return this.buffer.getUSINT(0 + offset);
	}
	
	public void setService(short value) {
		this.buffer.putUSINT(0 + offset, value);
	}
	
	public byte getGeneralStatus(){
		return this.buffer.getByte(2 + offset);
	}
	
	public void setGeneralStatus(byte value){
		this.buffer.putByte(2 + offset,value);
	}
	
	public int getSizeOfAdditionalStatus(){
		return this.buffer.getUSINT(3 + offset);
	}
	
	public void setSizeOfAdditionalStatus(short value){
		this.buffer.putUSINT(3 + offset, value);
	}
	
	public int getPayloadSize(){
		return getMessageRouterResponse().getPayloadSize();
	}
	
	public Object getValue(int count) throws InvalidTypeException, NotImplementedException {
		return getMessageRouterResponse().getValue(count);
	}
	
	public Object[] getValue() throws InvalidTypeException{
		return getMultipleMessageRouterResponse().getValue();
	}
	
	public Object getValues() throws InvalidTypeException{
		return getMessageRouterResponse().getValues();
	}
	
	public static short getService(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getUSINT(0 + offset);
	}	
}
