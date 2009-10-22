package se.opendataexchange.ethernetip4j.segments;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;

/**
 * A segment according to the EtherNet/IP Specification.
 * 
 * <table border="1">
 * <tr><th>Byte</th><th>	Name</th><th>				Type</th><th>							Description</th></tr>
 * <tr><td>0-3</td><td>		Interface handle</td><td> 	UDINT</td><td>							</td></tr>
 * <tr><td>4-5</td><td>		Timeout</td><td>	 		UINT</td><td> 							</td></tr>
 * <tr><td>6-7</td><td>		Item count</td><td> 		UINT</td><td> 							</td></tr>
 * <tr><td>8-11</td><td>	Address item</td><td> 		{@link EthernetIpItemStruct}</td><td> 	Filled with 0 by default.</td></tr>
 * </table>
 * 
 * @see <a href="Vol2_1.4.pdf">THE CIP NETWORKS LIBRARY Volume 2 EtherNet/IP Adaptation of CIP</a>
 * @see <a href="Ethernet_IP_CIP_Specification.pdf">EtherNet/IP Specification</a>
 * 
 * 
 */
public class EthernetIpCommandSpecificData {
	public static final int SEGMENT_LENGTH = 12;
	private static final int DEFAULT_OFFSET = EthernetIpEncapsulationHeader.SEGMENT_LENGTH;
	
	public static void fillBuffer(EthernetIpBufferUtil buffer, int offset){
		setInterfaceHandle(buffer, offset, 0);
		setTimeout(buffer, offset, 0);
		setItemCount(buffer, offset, 2);
		EthernetIpItemStruct.fillBuffer(0, buffer, offset + 8); // address item
	}
	public static void fillBuffer(EthernetIpBufferUtil buffer){
		fillBuffer(buffer, DEFAULT_OFFSET);
	}
	
	public static long getInterfaceHandle(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getUDINT(0 + offset);
	}
	
	public static void setInterfaceHandle(EthernetIpBufferUtil buffer, int offset, long interfaceHandle) {
		buffer.putUDINT(0 + offset, interfaceHandle);
	}
	
	public static int getTimeout(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getUINT(4 + offset);
	}
	
	public static void setTimeout(EthernetIpBufferUtil buffer, int offset, int timeout) {
		buffer.putUINT(4 + offset, timeout);
	}
	
	public static int getItemCount(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getUINT(6 + offset);
	}
	
	public static void setItemCount(EthernetIpBufferUtil buffer, int offset, int itemCount) {
		buffer.putUINT(6 + offset, itemCount);
	}	
}