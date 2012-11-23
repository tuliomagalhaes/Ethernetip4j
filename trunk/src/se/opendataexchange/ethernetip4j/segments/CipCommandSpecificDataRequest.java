package se.opendataexchange.ethernetip4j.segments;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;

/**
 * This class fills info into a byte buffer.
 * 
 * According to "Table 3-5.15. Unconnected Send Service Parameters"
 * in the document Ethernet_IP_CIP_Specification.pdf
 * 
 * <table border="1">
 * <tr><th>Byte</th><th>			Name</th><th>					Type</th><th>							Description</th></tr>
 * <tr><td>0</td><td>				Priority</td><td> 				USINT</td><td>							0x0A by default</td></tr>
 * <tr><td>1</td><td>				Timeout ticks</td><td>	 		Byte</td><td> 							0xF0 by default</td></tr>
 * <tr><td>2-3</td><td>				Message request size</td><td> 	UINT</td><td> 							Length according to {@link CipMessageRouterRequest}</td></tr>
 * <tr><td>4 - (3+Length)</td><td>	Message data</td><td> 			{@link CipMessageRouterRequest} or {@link CipMultipleMessageRouterRequest} </td><td></td></tr>
 * <tr><td>4+Length</td><td>		Route path size</td><td> 		Byte</td><td> 							0x01 by default</td></tr>
 * <tr><td>5+Length</td><td>		Reserved position</td><td> 		Byte</td><td> 							0x00 by default</td></tr>
 * <tr><td>6+Length</td><td>		Route path port</td><td> 		Byte</td><td> 							0x01 by default</td></tr>
 * <tr><td>7+Length</td><td>		Route path link address</td><td>Byte</td><td> 							0x00 by default</td></tr>
 * </table>
 *
 */
public class CipCommandSpecificDataRequest {
	public static final int SEGMENT_LENGTH = 8;
	public static final int SEGMENT_DATA_OFFSET = 4;
	private int msgReqLength;
	
	private static final int DEFAULT_OFFSET = 
		EthernetIpEncapsulationHeader.SEGMENT_LENGTH +
		EthernetIpCommandSpecificData.SEGMENT_LENGTH +
		EthernetIpItemStruct.SEGMENT_LENGTH +
		CipPacketRequest.SEGMENT_LENGTH;
	
	public static void fillBuffer(int msgReqLength, EthernetIpBufferUtil messageBuffer) {
		setPriority(messageBuffer, DEFAULT_OFFSET, (short)0x0A);
		setTimeout_ticks(messageBuffer, DEFAULT_OFFSET, (byte)0xF0);
		setMessageRequestSize(messageBuffer, DEFAULT_OFFSET, msgReqLength);
		setRoutePathSize(messageBuffer, DEFAULT_OFFSET, (byte)0x01, msgReqLength);
		setReservedPosition(messageBuffer, DEFAULT_OFFSET, (byte)0x00, msgReqLength);
		setRoutePathPort(messageBuffer, DEFAULT_OFFSET, (byte)0x01, msgReqLength);
		setRoutePathLinkAddress(messageBuffer, DEFAULT_OFFSET, (byte)0x00, msgReqLength);
	}	
	
	public static void fillBuffer(int msgReqLength, byte routePathLinkedAddress,
			EthernetIpBufferUtil messageBuffer ){
		setPriority(messageBuffer, DEFAULT_OFFSET, (short)0x0A);
		setTimeout_ticks(messageBuffer, DEFAULT_OFFSET, (byte)0xF0);
		setMessageRequestSize(messageBuffer, DEFAULT_OFFSET, msgReqLength);
		setRoutePathSize(messageBuffer, DEFAULT_OFFSET, (byte)0x01, msgReqLength);
		setReservedPosition(messageBuffer, DEFAULT_OFFSET, (byte)0x00, msgReqLength);
		setRoutePathPort(messageBuffer, DEFAULT_OFFSET, (byte)0x01, msgReqLength);
		setRoutePathLinkAddress(messageBuffer, DEFAULT_OFFSET, (byte)routePathLinkedAddress, msgReqLength);
	}
	
	public static void fillBuffer(int msgReqLength, byte routePathLinkedAddress, byte routePathPort,
			EthernetIpBufferUtil messageBuffer ){
		setPriority(messageBuffer, DEFAULT_OFFSET, (short)0x0A);
		setTimeout_ticks(messageBuffer, DEFAULT_OFFSET, (byte)0xF0);
		setMessageRequestSize(messageBuffer, DEFAULT_OFFSET, msgReqLength);
		setRoutePathSize(messageBuffer, DEFAULT_OFFSET, (byte)0x01, msgReqLength);
		setReservedPosition(messageBuffer, DEFAULT_OFFSET, (byte)0x00, msgReqLength);
		setRoutePathPort(messageBuffer, DEFAULT_OFFSET, (byte)routePathPort, msgReqLength);
		setRoutePathLinkAddress(messageBuffer, DEFAULT_OFFSET, (byte)routePathLinkedAddress, msgReqLength);
	}

	public int getPacketLength(){
		return msgReqLength + SEGMENT_LENGTH; //this.buffer.getBufferLength();
	}

	/* Static */
	private static void setPriority(EthernetIpBufferUtil buffer, int offset, short value) {
		buffer.putUSINT(0 + offset, value);
	}
	
	private static void setTimeout_ticks(EthernetIpBufferUtil buffer, int offset, byte value) {
		buffer.putByte(1 + offset, value);
	}
	
	private static void setMessageRequestSize(EthernetIpBufferUtil buffer, int offset, int value) {
		buffer.putUINT(2 + offset, value);
	}
	
	private static void setRoutePathSize(EthernetIpBufferUtil buffer, int offset, byte value, int msgReqLength) {
		buffer.putByte(4+msgReqLength + offset, value);
	}
	
	private static void setReservedPosition(EthernetIpBufferUtil buffer, int offset, byte value, int msgReqLength) {
		buffer.putByte(5+msgReqLength + offset, value);
	}
	
	private static void setRoutePathPort(EthernetIpBufferUtil buffer, int offset, byte port, int msgReqLength) {
		buffer.putByte(0 + 6 + msgReqLength + offset, (byte)port);
	}
	
	private static void setRoutePathLinkAddress(EthernetIpBufferUtil buffer, int offset, byte linkAddress, int msgReqLength) {
		buffer.putByte(1 + 6 + msgReqLength + offset,(byte) linkAddress);
	}
}
