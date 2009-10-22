package se.opendataexchange.ethernetip4j.services;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.segments.EthernetIpEncapsulationHeader;

public class EthernetIpRegisterSessionData {
	public static final int SEGMENT_LENGTH = 4;
	private static final int DEFAULT_OFFSET = EthernetIpEncapsulationHeader.SEGMENT_LENGTH;
	
	public static void fillBuffer(EthernetIpBufferUtil buffer){
		setProtocolVersion(buffer, 1);
		setOptionsFlag(buffer, 0);
	}
	
	private EthernetIpRegisterSessionData(){		
	}
	
	public static int getProtocolVersion(EthernetIpBufferUtil buffer){
		return buffer.getUSINT(0 + DEFAULT_OFFSET);
	}
	
	public static void setProtocolVersion(EthernetIpBufferUtil buffer, int protocolVersion){
		buffer.putUSINT(0 + DEFAULT_OFFSET, (short)protocolVersion);
	}
	
	public static int getOptionsFlag(EthernetIpBufferUtil buffer){
		return buffer.getUSINT(2 + DEFAULT_OFFSET);
	}
	
	public static void setOptionsFlag(EthernetIpBufferUtil buffer, int optionsFlag){
		buffer.putUSINT(2 + DEFAULT_OFFSET,(short) optionsFlag);
	}

}
