package se.opendataexchange.ethernetip4j.junit;

import org.junit.Assert;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.HexConverter;
import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;
import se.opendataexchange.ethernetip4j.services.UnconnectedMessageManagerRequest;

/***
 * 
 * @author dbv
 *
 */
public class UnconnectedMessageManagerRequestTest {
	EthernetIpBufferUtil messageBuffer = new EthernetIpBufferUtil(1500);
	
	@org.junit.Test
	public void testGetReadRequestBufferArr() throws NotImplementedException{
		UnconnectedMessageManagerRequest req = new UnconnectedMessageManagerRequest(messageBuffer);
		String[] tagNames = new String[]{"AsDf", "AnotherTagName"};
		req.asReadRequestBuffer(tagNames, 101009991172L);
		byte[] actual = req.getByteBuffer().array();
		byte[] expected = new byte[]{(byte)0x6F, (byte)0x00, (byte)0x4A, (byte)0x00, (byte)0x04, (byte)0x26, (byte)0xAA, (byte)0x84, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xB2, (byte)0x00, (byte)0x3A, (byte)0x00, 
				(byte)0x52, (byte)0x02, (byte)0x20, (byte)0x06, (byte)0x24, (byte)0x01, (byte)0x0A, (byte)0xF0, (byte)0x2C, (byte)0x00, 
				(byte)0x0A, (byte)0x02, (byte)0x20, (byte)0x02, (byte)0x24, (byte)0x01, (byte)0x02, (byte)0x00, (byte)0x06, (byte)0x00, 
				(byte)0x10, (byte)0x00, (byte)0x4C, (byte)0x03, (byte)0x91, (byte)0x04, (byte)0x41, (byte)0x73, (byte)0x44, (byte)0x66, 
				(byte)0x01, (byte)0x00, (byte)0x4C, (byte)0x08, (byte)0x91, (byte)0x0E, (byte)0x41, (byte)0x6E, (byte)0x6F, (byte)0x74, 
				(byte)0x68, (byte)0x65, (byte)0x72, (byte)0x54, (byte)0x61, (byte)0x67, (byte)0x4E, (byte)0x61, (byte)0x6D, (byte)0x65, 
				(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01, (byte)0x00};
		//Assert.assertEquals(expected.length, actual.length);
		for (int i=0; i<expected.length; i++)
		{
			Assert.assertEquals("Byte "+i +": expected 0x"+HexConverter.byte2hex(expected[i]) +" got 0x"+HexConverter.byte2hex(actual[i]), expected[i], actual[i]);
		}
	}
	
	@org.junit.Test
	public void testGetReadRequestBuffer2() throws NotImplementedException{
		UnconnectedMessageManagerRequest req = new UnconnectedMessageManagerRequest(messageBuffer);
		req.asReadRequestBuffer("Qwerty123", 33320814456621284L, 4);
		byte[] actual = req.getByteBuffer().array();
		byte[] expected = new byte[]{(byte)0x6F, (byte)0x00, (byte)0x2E, (byte)0x00, (byte)0xE4, (byte)0x48, (byte)0x50, (byte)0xAD, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xB2, (byte)0x00, (byte)0x1E, (byte)0x00, 
				(byte)0x52, (byte)0x02, (byte)0x20, (byte)0x06, (byte)0x24, (byte)0x01, (byte)0x0A, (byte)0xF0, (byte)0x10, (byte)0x00, 
				(byte)0x4C, (byte)0x06, (byte)0x91, (byte)0x09, (byte)0x51, (byte)0x77, (byte)0x65, (byte)0x72, (byte)0x74, (byte)0x79, 
				(byte)0x31, (byte)0x32, (byte)0x33, (byte)0x04, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01, (byte)0x00};
		//Assert.assertEquals(expected.length, actual.length);
		for (int i=0; i<expected.length; i++)
		{
			Assert.assertEquals("Byte "+i +": expected 0x"+HexConverter.byte2hex(expected[i]) +" got 0x"+HexConverter.byte2hex(actual[i]), expected[i], actual[i]);
		}
	}
	
	@org.junit.Test
	public void testGetReadRequestBuffer3() throws NotImplementedException{
		UnconnectedMessageManagerRequest req = new UnconnectedMessageManagerRequest(messageBuffer);
		req.asReadRequestBuffer("pOILkJhGG", 123456L, 1, 14);
		byte[] actual = req.getByteBuffer().array();
		byte[] expected = new byte[]{(byte)0x6F, (byte)0x00, (byte)0x32, (byte)0x00, (byte)0x40, (byte)0xE2, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xB2, (byte)0x00, (byte)0x22, (byte)0x00, 
				(byte)0x52, (byte)0x02, (byte)0x20, (byte)0x06, (byte)0x24, (byte)0x01, (byte)0x0A, (byte)0xF0, (byte)0x14, (byte)0x00, 
				(byte)0x52, (byte)0x06, (byte)0x91, (byte)0x09, (byte)0x70, (byte)0x4F, (byte)0x49, (byte)0x4C, (byte)0x6B, (byte)0x4A, 
				(byte)0x68, (byte)0x47, (byte)0x47, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x0E, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x01, (byte)0x00, (byte)0x01, (byte)0x00};
		//Assert.assertEquals(expected.length, actual.length);
		for (int i=0; i<expected.length; i++)
		{
			Assert.assertEquals("Byte "+i +": expected 0x"+HexConverter.byte2hex(expected[i]) +" got 0x"+HexConverter.byte2hex(actual[i]), expected[i], actual[i]);
		}
	}
	
	@org.junit.Test
	public void testGetWriteRequestBuffer() throws InvalidTypeException, NotImplementedException{
		UnconnectedMessageManagerRequest req = new UnconnectedMessageManagerRequest(messageBuffer);
		req.asWriteRequestByteBuffer("QxLLKjbmr", 0xFABCDFABCDFABCDDL, (char)14, 25);
		byte[] actual = req.getByteBuffer().array();
		byte[] expected = new byte[]{(byte)0x6F, (byte)0x00, (byte)0x3A, (byte)0x00, (byte)0xDD, (byte)0xBC, (byte)0xFA, (byte)0xCD, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xB2, (byte)0x00, (byte)0x2A, (byte)0x00, 
				(byte)0x52, (byte)0x02, (byte)0x20, (byte)0x06, (byte)0x24, (byte)0x01, (byte)0x0A, (byte)0xF0, (byte)0x1C, (byte)0x00, 
				(byte)0x4D, (byte)0x06, (byte)0x91, (byte)0x09, (byte)0x51, (byte)0x78, (byte)0x4C, (byte)0x4C, (byte)0x4B, (byte)0x6A, 
				(byte)0x62, (byte)0x6D, (byte)0x72, (byte)0xC2, (byte)0x00, (byte)0x19, (byte)0x00, (byte)0x0E, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, 
				(byte)0x01, (byte)0x00};
		//Assert.assertEquals(expected.length, actual.length);
		for (int i=0; i<expected.length; i++)
		{
			Assert.assertEquals("Byte: "+i+": expected 0x"+HexConverter.byte2hex(expected[i]) +" got 0x"+HexConverter.byte2hex(actual[i]), expected[i], actual[i]);
		}
		//printByteBufferAsArray(result);
	}
	
	@org.junit.Test
	public void testGetPacketLength() throws InvalidTypeException, NotImplementedException{
		UnconnectedMessageManagerRequest req = new UnconnectedMessageManagerRequest(messageBuffer);
		req.asWriteRequestByteBuffer("QxLLKjbmr", 0xFABCDFABCDFABCDDL, (short)14, 25);
		//int len = req.getPacketLength();
		//Assert.assertEquals(82, len);
		//printByteBufferAsArray(result);
	}	
}
