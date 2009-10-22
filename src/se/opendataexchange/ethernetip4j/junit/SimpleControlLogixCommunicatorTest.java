package se.opendataexchange.ethernetip4j.junit;

import java.io.IOException;
import java.nio.ByteBuffer;

import junit.framework.Assert;
import se.opendataexchange.ethernetip4j.HexConverter;
import se.opendataexchange.ethernetip4j.clx.SimpleLogixCommunicator;
import se.opendataexchange.ethernetip4j.exceptions.EmbeddedServiceException;
import se.opendataexchange.ethernetip4j.exceptions.InsufficientCommandException;
import se.opendataexchange.ethernetip4j.exceptions.InsufficientNrOfAttributesException;
import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.ItemNotFoundException;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;
import se.opendataexchange.ethernetip4j.exceptions.OtherWithExtendedCodeException;
import se.opendataexchange.ethernetip4j.exceptions.PathSegmentException;
import se.opendataexchange.ethernetip4j.exceptions.ProcessingAttributesException;
import se.opendataexchange.ethernetip4j.exceptions.ResponseBufferOverflowException;

public class SimpleControlLogixCommunicatorTest {
	
	@org.junit.Test
	public void testReadDint() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator connector = new SimpleLogixCommunicator(
				"192.168.200.51", 0xAF12);
		String[] tagNames = new String[1];
		//tagNames[0]="TestVar1";
		tagNames[0]="TestVar1";
		
		connector.read(tagNames);
		
		byte[] exreq = 
			new byte[]{(byte)0x6F, (byte)0x00, (byte)0x38, (byte)0x00, (byte)0x00, (byte)0x1B, (byte)0x02, (byte)0x0C, (byte)0x00, (byte)0x00, 
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
			(byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xB2, (byte)0x00, (byte)0x28, (byte)0x00, 
			(byte)0x52, (byte)0x02, (byte)0x20, (byte)0x06, (byte)0x24, (byte)0x01, (byte)0x0A, (byte)0xF0, (byte)0x1A, (byte)0x00, 
			(byte)0x0A, (byte)0x02, (byte)0x20, (byte)0x02, (byte)0x24, (byte)0x01, (byte)0x01, (byte)0x00, (byte)0x04, (byte)0x00, 
			(byte)0x4C, (byte)0x05, (byte)0x91, (byte)0x08, (byte)0x54, (byte)0x65, (byte)0x73, (byte)0x74, (byte)0x56, (byte)0x61, 
			(byte)0x72, (byte)0x31, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01, (byte)0x00 
			};
		
		byte[] exresp = 
			new byte[]{(byte)0x6F, (byte)0x00, (byte)0x22, (byte)0x00, (byte)0x00, (byte)0x1B, (byte)0x02, (byte)0x0C, (byte)0x00, (byte)0x00, 
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
			(byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xB2, (byte)0x00, (byte)0x12, (byte)0x00, 
			(byte)0x8A, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0xCC, (byte)0x00, 
			(byte)0x00, (byte)0x00, (byte)0xC4, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, };
		
		ByteBuffer req = connector.getLatestSent();
		ByteBuffer resp = connector.getLatestIncoming();
		
		Assert.assertEquals(exreq.length, req.limit());
		for (int i=0; i<req.limit(); i++)
		{
			if (i<4 || i > 7)
				Assert.assertEquals("Byte "+i +": expected 0x"+HexConverter.byte2hex(exreq[i]) +" got 0x"+HexConverter.byte2hex(req.get(i)), exreq[i], req.get(i));
		}
		for (int i=0; i<exresp.length; i++)
		{
			if ( (i<4 || i > 7) && (i < 54 || i > 57) )
				Assert.assertEquals("Byte "+i +": expected 0x"+HexConverter.byte2hex(exresp[i]) +" got 0x"+HexConverter.byte2hex(resp.get(i)), exresp[i], resp.get(i));
		}
		
	}
	
	@org.junit.Test
	public void testWrite() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator connector = new SimpleLogixCommunicator(
				"192.168.200.51", 0xAF12);
		
		connector.write("TestVar1", (int)0xABCD1234, 1);
		//connector.write("TestVar1", (int)0, 1);
		
		byte[] exreq = new byte[]{(byte)0x6F, (byte)0x00, (byte)0x38, (byte)0x00, (byte)0x00, (byte)0x69, (byte)0x02, (byte)0x0C, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xB2, (byte)0x00, (byte)0x28, (byte)0x00, 
				(byte)0x52, (byte)0x02, (byte)0x20, (byte)0x06, (byte)0x24, (byte)0x01, (byte)0x0A, (byte)0xF0, (byte)0x1A, (byte)0x00, 
				(byte)0x4D, (byte)0x05, (byte)0x91, (byte)0x08, (byte)0x54, (byte)0x65, (byte)0x73, (byte)0x74, (byte)0x56, (byte)0x61, 
				(byte)0x72, (byte)0x31, (byte)0xC4, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xAB, (byte)0xCD, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01, (byte)0x00};
		
		byte[] exresp = new byte[]{(byte)0x6F, (byte)0x00, (byte)0x14, (byte)0x00, (byte)0x00, (byte)0x69, (byte)0x02, (byte)0x0C, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xB2, (byte)0x00, (byte)0x04, (byte)0x00, 
				(byte)0xCD, (byte)0x00, (byte)0x00, (byte)0x00 };
		
		ByteBuffer req = connector.getLatestSent();
		ByteBuffer resp = connector.getLatestIncoming();
		
		Assert.assertEquals(exreq.length, req.limit());
		for (int i=0; i<req.limit(); i++)
		{
			if ( (i<4 || i > 7) && (i<66 || i>70))
				Assert.assertEquals("Byte "+i +": expected 0x"+HexConverter.byte2hex(exreq[i]) +" got 0x"+HexConverter.byte2hex(req.get(i)), exreq[i], req.get(i));
		}
		for (int i=0; i<exresp.length; i++)
		{
			if ( (i<4 || i > 7) && (i < 54 || i > 57) )
				Assert.assertEquals("Byte "+i +": expected 0x"+HexConverter.byte2hex(exresp[i]) +" got 0x"+HexConverter.byte2hex(resp.get(i)), exresp[i], resp.get(i));
		}
		
	}
	
	@org.junit.Test
	public void testReadWriteDINT() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator connector = new SimpleLogixCommunicator(
				"192.168.200.51", 0xAF12);
		
		int a = 0x1234;
		connector.write("TestVar1", a, 1);
		Object o = connector.read("TestVar1");
		Assert.assertTrue(o instanceof Integer);
		Assert.assertEquals(a, o);		
	}
	
	@org.junit.Test
	public void testReadWriteBOOL() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator connector = new SimpleLogixCommunicator(
				"192.168.200.51", 0xAF12);
		
		boolean a = true;
		connector.write("TestBOOL", a, 1);
		Object o = connector.read("TestBOOL");
		Assert.assertTrue(o instanceof Boolean);
		Assert.assertEquals(a, o);	
		
		a = false;
		connector.write("TestBOOL", a, 1);
		o = connector.read("TestBOOL");
		Assert.assertTrue(o instanceof Boolean);
		Assert.assertEquals(a, o);	
		
	}
	
	@org.junit.Test
	public void testReadWriteSINT() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator connector = new SimpleLogixCommunicator(
				"192.168.200.51", 0xAF12);
		
		char a = (char)'q';
		connector.write("TestSINT", a, 1);
		Object o = connector.read("TestSINT");
		Assert.assertTrue(o instanceof Character);
		Assert.assertEquals(a, o);		
	}
	
	@org.junit.Test
	public void testReadWriteINT() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator connector = new SimpleLogixCommunicator(
				"192.168.200.51", 0xAF12);
		
		short a = 167;
		connector.write("YetAnotherInt2", a, 1);
		Object o = connector.read("YetAnotherInt2");
		Assert.assertNotNull(o);
		Assert.assertTrue(o instanceof Short);
		Assert.assertEquals(a, o);		
	}
	
	@org.junit.Test
	public void testReadWriteREAL() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator connector = new SimpleLogixCommunicator(
				"192.168.200.51", 0xAF12);
		
		float a = (float)13.3;
		connector.write("TestREAL", a, 1);
		Object o = connector.read("TestREAL");
		Assert.assertTrue(o instanceof Float);
		Assert.assertEquals(a, o);		
	}
	
	@org.junit.Test
	public void testReadArr() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator connector = new SimpleLogixCommunicator(
				"192.168.200.51", 0xAF12);
		
		Object o = connector.read("YetAnotherDINT", 3);
		Assert.assertNotNull(o);
		Assert.assertTrue(o instanceof Object[]);
		Assert.assertEquals(3, ((Object[])o).length);
		Assert.assertTrue(((Object[])o)[0] instanceof Integer);
		Assert.assertTrue(((Object[])o)[1] instanceof Integer);
		Assert.assertEquals(1, ((Object[])o)[0]);
		Assert.assertEquals(2, ((Object[])o)[1]);
		Assert.assertEquals(3, ((Object[])o)[2]);
		
		//Assert.assertTrue(o instanceof Float);
		//Assert.assertEquals(a, o);	
		String[] tst = {"Test5"};
		o = connector.read(tst);
	}
	
	@org.junit.Test
	public void testReadMultiple() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator connector = new SimpleLogixCommunicator(
				"192.168.200.51", 0xAF12);
		
		String[] tagNames = new String[2];
		tagNames[0]="TestVar1";
		tagNames[1]="TestSINT";
		Object o = connector.read(tagNames);
		Assert.assertNotNull(o);
		Assert.assertTrue(o instanceof Object[]);
		Object[] ob = (Object[])o;
		Assert.assertEquals(2, ob.length);
		Assert.assertNotNull(ob[0]);
		Assert.assertTrue(ob[0] instanceof Integer);
		Assert.assertNotNull(ob[1]);
		Assert.assertTrue(ob[1] instanceof Character);	
	}	
}
