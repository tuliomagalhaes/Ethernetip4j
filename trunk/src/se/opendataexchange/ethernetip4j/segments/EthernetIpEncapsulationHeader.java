package se.opendataexchange.ethernetip4j.segments;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.exceptions.InvalidEncapsulationPackageException;

/***
 * Represents the encapsulation packet header according to the EtherNet/IP Specification:
 * 
 * <table border="1">
 * <tr><th>Byte</th><th>	Name</th><th>			Type</th><th>			Description</th></tr>
 * <tr><td>0-1</td><td>		Command</td><td> 		UINT</td><td>			Encapsulation command</td></tr>
 * <tr><td>2-3</td><td>		Length</td><td>	 		UINT</td><td> 			Length, in bytes, of the data portion of the message, i.e., the number of bytes following the header</td></tr>
 * <tr><td>4-7</td><td>		Session handle</td><td> UDINT</td><td> 			Session identification (application dependent)</td></tr>
 * <tr><td>8-11</td><td>	Status</td><td> 		UDINT</td><td> 			Status code</td></tr>
 * <tr><td>12-19</td><td>	Sender Context</td><td> ARRAY of octet</td><td> Information pertinent only to the sender of an encapsulation command. Length of 8.</td></tr>
 * <tr><td>20-23</td><td>	Options</td><td> 		UDINT</td><td> 			Options flags</td></tr>
 * </table>
 * @see <a href="Ethernet_IP_CIP_Specification.pdf">EtherNet/IP Specification</a>
 */
public class EthernetIpEncapsulationHeader{
	public static final int SEGMENT_LENGTH = 24;
	private static final int DEFAULT_OFFSET = 0;
		
	public static void validate(EthernetIpBufferUtil buffer)throws InvalidEncapsulationPackageException{
		if(buffer.getBufferLength()<24) 
			throw new InvalidEncapsulationPackageException("Package can not be a valid Ethernet/IP package.");		
	}
	
	public static int getCommand(EthernetIpBufferUtil buffer){
		return buffer.getUINT(0 + DEFAULT_OFFSET);
	}

	public static void setCommand(EthernetIpBufferUtil buffer, int command){
		buffer.putUINT(0 + DEFAULT_OFFSET,(short) command);
	}

	public static int getLength(EthernetIpBufferUtil buffer){
		return buffer.getUINT(2 + DEFAULT_OFFSET);
	}
	
	public static void setLength(EthernetIpBufferUtil buffer, int length){
		buffer.putUINT(2 + DEFAULT_OFFSET,(short) length);
	}

	public static long getSessionHandle(EthernetIpBufferUtil buffer){
		return buffer.getUDINT(4 + DEFAULT_OFFSET);
	}

	public static void setSessionHandle(EthernetIpBufferUtil buffer, long sessionHandle){
		buffer.putUDINT(4 + DEFAULT_OFFSET, sessionHandle);
	}
	
	public static void setSessionHandleIncoming(EthernetIpBufferUtil buffer, long sessionHandle){
		buffer.putUDINT(4 + DEFAULT_OFFSET,(int)sessionHandle);
	}

	public static long getStatus(EthernetIpBufferUtil buffer){
		return buffer.getUDINT(8 + DEFAULT_OFFSET);
	}

	public static void setStatus(EthernetIpBufferUtil buffer, long status){
		buffer.putUDINT(8 + DEFAULT_OFFSET,(int) status);
	}

	public static byte[] getSenderContext(EthernetIpBufferUtil buffer){
		return buffer.getByteArray(12 + DEFAULT_OFFSET, 8);
	}

	public static void setSenderContext(EthernetIpBufferUtil buffer, byte[] data){
		buffer.putByteArray(12 + DEFAULT_OFFSET, data);
	}

	public static long getOptions(EthernetIpBufferUtil buffer){
		return buffer.getUDINT(20 + DEFAULT_OFFSET);
	}

	public static void setOptions(EthernetIpBufferUtil buffer, long options){
		buffer.putUDINT(20 + DEFAULT_OFFSET, (short)options);
	}
	
}
