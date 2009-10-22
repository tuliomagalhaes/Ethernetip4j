package se.opendataexchange.ethernetip4j.segments;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.services.UnconnectedMessageManagerRequest;

/**
 * A packet segment according to Ethernet IP and CIP specification.
 * 
 * <table border="1">
 * <tr><th>Byte</th><th>	Name</th><th>									Type</th><th>							Description</th></tr>
 * <tr><td>0</td><td>		Service</td><td> 								Byte</td><td>							0x52 by default</td></tr>
 * <tr><td>1</td><td>		Request path size</td><td>						Byte</td><td> 							0x02 by default</td></tr>
 * <tr><td>2</td><td>		Request path logical class segment</td><td> 	Byte</td><td> 							0x20 by default</td></tr>
 * <tr><td>3</td><td>		Request path class</td><td> 					Byte</td><td> 							0x06 by default</td></tr>
 * <tr><td>4</td><td>		Request path logical instance segment</td><td> 	Byte</td><td> 							0x24 by default</td></tr>
 * <tr><td>5</td><td>		Request path instance</td><td> 					Byte</td><td> 							0x01 by default</td></tr>
 * </table>
 */
public class CipPacketRequest {
	public static final int SEGMENT_LENGTH = 6;
	private static int DEFAULT_OFFSET =
		EthernetIpEncapsulationHeader.SEGMENT_LENGTH + 
		EthernetIpCommandSpecificData.SEGMENT_LENGTH +
		EthernetIpItemStruct.SEGMENT_LENGTH;
	
	/***
	 * Fill buffer at default offset according to {@link UnconnectedMessageManagerRequest}
	 * 
	 * @param buffer The buffer to fill.
	 */
	public static void fillBuffer(EthernetIpBufferUtil buffer){
		fillBuffer(buffer, DEFAULT_OFFSET);
	}
	public static void fillBuffer(EthernetIpBufferUtil buffer, int offset){
		setService(buffer, offset, (short)0x52);
		setRequestPathSize(buffer, offset, (short)0x02);
		setRequestPathLogicalClassSegment(buffer, offset, (short)0x20);
		setRequestPathClass(buffer, offset, (short)0x06);
		setRequestPathLogicalInstanceSegment(buffer, offset, (short)0x24);
		setRequestPathInstance(buffer, offset, (short)0x01);
	}
	
	public static short getService(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getByte(0 + offset);
	}

	public static void setService(EthernetIpBufferUtil buffer, int offset, short value) {
		buffer.putByte(0 + offset, (byte)value);
	}
	
	public static short getRequestPathSize(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getByte(1 + offset);
	}

	public static void setRequestPathSize(EthernetIpBufferUtil buffer, int offset, short value) {
		buffer.putByte(1 + offset,(byte) value);
	}
	
	public static short getRequestPathLogicalClassSegment(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getByte(2 + offset);
	}
	
	public static void setRequestPathLogicalClassSegment(EthernetIpBufferUtil buffer, int offset, short value) {
		buffer.putByte(2 + offset,(byte) value);
	}

	public static short getRequestPathClass(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getByte(3 + offset);
	}

	public static void setRequestPathClass(EthernetIpBufferUtil buffer, int offset, short value) {
		buffer.putByte(3 + offset,(byte) value);
	}
	
	public static short getRequestPathLogicalInstanceSegment(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getByte(4 + offset);
	}
	
	public static void setRequestPathLogicalInstanceSegment(EthernetIpBufferUtil buffer, int offset, short value) {
		buffer.putByte(4 + offset, (byte)value);
	}
	
	public static short getRequestPathInstance(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getByte(5 + offset);
	}

	public static void setRequestPathInstance(EthernetIpBufferUtil buffer, int offset, short value) {
		buffer.putByte(5 + offset,(byte) value);
	}	
}
