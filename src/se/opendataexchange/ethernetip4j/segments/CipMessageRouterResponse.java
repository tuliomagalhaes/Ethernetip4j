package se.opendataexchange.ethernetip4j.segments;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.EthernetIpDataTypeValidator;
import se.opendataexchange.ethernetip4j.exceptions.InvalidEncapsulationPackageException;
import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;

/***
 * 
 * <table border="1">
 * <tr><th>Byte</th><th>	Name</th><th>									Type</th><th>	Description</th></tr>
 * <tr><td>0</td><td>		Type</td><td> 									USINT</td><td>	Data type</td></tr>
 * <tr><td>0</td><td>		Padding byte</td><td> 							Byte</td><td>	0x00</td></tr>
 * <tr><td>2</td><td>		Data</td><td>									Byte[]</td><td> Tag values</td></tr>
 * </table>
 *
 */
public class CipMessageRouterResponse{
	private EthernetIpBufferUtil buffer;
	private int offset = EthernetIpEncapsulationHeader.SEGMENT_LENGTH + 
	EthernetIpCommandSpecificData.SEGMENT_LENGTH +
	EthernetIpItemStruct.SEGMENT_LENGTH +
	CipPacketResponse.SEGMENT_LENGTH;
	private int payload;
	private CipMessageRouterResponse() {
	}
	
	public static CipMessageRouterResponse createPackage(EthernetIpBufferUtil incomingBuffer) throws InvalidEncapsulationPackageException {
		if(incomingBuffer.getBufferLength()<24) throw new InvalidEncapsulationPackageException("Package can not be a valid Ethernet/IP package.");
		CipMessageRouterResponse response = new CipMessageRouterResponse();
		response.buffer = incomingBuffer;
		response.payload = response.buffer.getBufferLength() - response.offset - 2;
		return response;
	}
	
	public static CipMessageRouterResponse createPackage(EthernetIpBufferUtil incomingBuffer, int offset, int nextOffset) throws InvalidEncapsulationPackageException {
		if(incomingBuffer.getBufferLength()<24) throw new InvalidEncapsulationPackageException("Package can not be a valid Ethernet/IP package.");
		CipMessageRouterResponse response = new CipMessageRouterResponse();
		response.offset = offset + CipPacketResponse.SEGMENT_LENGTH;
		response.buffer = incomingBuffer;
		response.payload = nextOffset - response.offset - 2; // Value length
		return response;
	}
	
	public Object getValue(int count) throws InvalidTypeException, NotImplementedException{
		if (payload == 0) throw new InvalidTypeException("No data in response");
		return EthernetIpDataTypeValidator.getNumberedValues(this.buffer, offset, count);
	}
	
	public Object getValue() throws InvalidTypeException{
		if (payload == 0) throw new InvalidTypeException("No data in response");
		return EthernetIpDataTypeValidator.getValues(this.buffer, offset, payload);
	}
	
	public Object getValues() throws InvalidTypeException{
		if (payload == 0) throw new InvalidTypeException("No data in response");
		return EthernetIpDataTypeValidator.getValues(this.buffer, offset, payload);
	}
	
	public void setValue(Object value) throws InvalidTypeException, NotImplementedException{
		EthernetIpDataTypeValidator.putTypeAndValue(value, buffer, offset);
	}
	
	public void setValue(byte[] value) throws InvalidTypeException{
		this.buffer.putByteArray(2 + offset, value);
	}
	
	public int getPayloadSize(){
		return payload;
	}
	
	public int getSegmentLength() throws NotImplementedException {
		throw new NotImplementedException();
	}	
}
