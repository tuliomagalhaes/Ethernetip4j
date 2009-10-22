package se.opendataexchange.ethernetip4j.junit;

import java.io.IOException;

import se.opendataexchange.ethernetip4j.clx.ControlLogixConnector;
import se.opendataexchange.ethernetip4j.clx.UnconnectedMessagingRead;
import se.opendataexchange.ethernetip4j.clx.UnconnectedMessagingWrite;
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

import junit.framework.Assert;


public class UnconnectedMessagingTest {
	
	@org.junit.Test
	public void testReadWriteDINT() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		ControlLogixConnector connector = new ControlLogixConnector("192.168.200.51", 0xAF12);
		
		int a = 0x1234;
		UnconnectedMessagingWrite w = new UnconnectedMessagingWrite("TestVar1", a, connector.getSessionHandle());
		UnconnectedMessagingRead r = new UnconnectedMessagingRead("TestVar1", connector.getSessionHandle());
		
		connector.executeMessage(w);
		connector.executeMessage(r);
		Object o = r.getValues();
		
		Assert.assertTrue(o != null);
		Assert.assertTrue(r.getValues() instanceof Integer);
		Assert.assertEquals(a, r.getValues());
		
	}
	
	@org.junit.Test
	public void testReadWriteMultiple() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, InterruptedException, NotImplementedException{
		ControlLogixConnector connector = new ControlLogixConnector(
				"192.168.200.51", 0xAF12);
		
		String[] tags = {"TestVar1", "TestREAL", "QuitWhenUCan", "TestSINT", "TestSINT", "TestBOOL"};
		UnconnectedMessagingRead r = new UnconnectedMessagingRead(tags, connector.getSessionHandle());
		//long tStart = System.currentTimeMillis();
		for (int i=0; i<1000; i++){
			connector.executeMessage(r);
			Object[] o = r.getValue();
			Assert.assertNotNull("i="+i, o);
			Assert.assertEquals("i="+i, 6, o.length);		
			for (Object ob : o)
				Assert.assertNotNull("i="+i, ob);
		}
		//Log.p("Execution time "+(System.currentTimeMillis()-tStart));
	}	
}
