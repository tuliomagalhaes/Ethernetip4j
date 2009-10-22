package se.opendataexchange.ethernetip4j.services;

import java.nio.ByteBuffer;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.segments.EthernetIpEncapsulationHeader;

public class EthernetIpRegisterSessionRequest {
	private EthernetIpBufferUtil buffer;
	public EthernetIpRegisterSessionRequest(){
		buffer = new EthernetIpBufferUtil(EthernetIpEncapsulationHeader.SEGMENT_LENGTH + EthernetIpRegisterSessionData.SEGMENT_LENGTH);
		EthernetIpEncapsulationHeader.setCommand(buffer, EthernetIpCommandServices.REGISTER_SESSION);
		EthernetIpEncapsulationHeader.setLength(buffer, 4);
		EthernetIpEncapsulationHeader.setSessionHandle(buffer, 0);
		EthernetIpEncapsulationHeader.setSenderContext(buffer, new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
		EthernetIpEncapsulationHeader.setOptions(buffer, 0);
		EthernetIpRegisterSessionData.fillBuffer(buffer);
	}
	
	public ByteBuffer getByteBuffer(){
		return buffer.getBuffer();
	}
}
