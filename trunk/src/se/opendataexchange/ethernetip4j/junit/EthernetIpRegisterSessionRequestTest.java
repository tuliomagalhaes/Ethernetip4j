package se.opendataexchange.ethernetip4j.junit;

import junit.framework.Assert;
import se.opendataexchange.ethernetip4j.HexConverter;
import se.opendataexchange.ethernetip4j.services.EthernetIpRegisterSessionRequest;


public class EthernetIpRegisterSessionRequestTest {
	
	@org.junit.Test
	public void test1(){
		EthernetIpRegisterSessionRequest eth = new EthernetIpRegisterSessionRequest();
		byte[] expected = new byte[]{(byte)0x65, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, };
		byte[] actual = eth.getByteBuffer().array();
		Assert.assertEquals(expected.length, actual.length);
		for (int i=0; i<expected.length; i++)
		{
			Assert.assertEquals("Byte "+i +": expected 0x"+HexConverter.byte2hex(expected[i]) +" got 0x"+HexConverter.byte2hex(actual[i]), expected[i], actual[i]);
		}
		
		//TestUtils.printByteBufferAsArray(eth.getByteBuffer());
	}
}
