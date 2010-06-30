package se.opendataexchange.ethernetip4j.segments;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;

/**
 * Represents a "Data and Address item"-segment of a message
 *
 * Type	ID		UINT
 * Length		UINT			Length in bytes of the Data field
 * Data			
 * 
 * @see <a href="Vol2_1.4.pdf">THE CIP NETWORKS LIBRARY Volume 2 EtherNet/IP Adaptation of CIP</a>
 *
 */
public class EthernetIpItemStruct {
	public static final int SEGMENT_LENGTH = 4;
	
	public static void fillBuffer(int cipLength, EthernetIpBufferUtil buffer, int offset) {
		setLength(buffer, offset, cipLength);
		if(cipLength == 0){
			setType(buffer, offset, 0);					
		}
		else {
			setType(buffer, offset, (short)0xB2);
		}
	}
	
	public static void fillDataItem(int commandSpecificLen, EthernetIpBufferUtil buffer) {
		fillBuffer(commandSpecificLen, buffer, EthernetIpEncapsulationHeader.SEGMENT_LENGTH + EthernetIpCommandSpecificData.SEGMENT_LENGTH);
	}
	
	private EthernetIpItemStruct() {		
	}
	
	public static int getType(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getUINT(0 + offset);
	}
	
	public static void setType(EthernetIpBufferUtil buffer, int offset, int value) {
		buffer.putUSINT(0 + offset,(short) value);
	}
	
	public static int getLength(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getUINT(2 + offset);
	}
	
	public static void setLength(EthernetIpBufferUtil buffer, int offset, int value) {
		buffer.putUSINT(2 + offset, (short)value);
	}
}
