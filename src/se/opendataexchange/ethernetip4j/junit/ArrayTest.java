package se.opendataexchange.ethernetip4j.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import se.opendataexchange.ethernetip4j.clx.ControlLogixConnector;
import se.opendataexchange.ethernetip4j.clx.SimpleLogixCommunicator;
import se.opendataexchange.ethernetip4j.clx.UnconnectedMessagingRead;
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


public class ArrayTest {
	public static String IP = "192.168.200.51";
	
	public static String DINT_1000 = "FirstArrayTest"; // DINT array with 1000 values
	public static String SINT_200 = "ThirdArrayTest"; // SINT array with 200 values
	public static String REAL_5000 = "ArrayReal";
	public static String BOOL_128 = "ArrayBool128";
	public static String INT_500 = "ArrInt500";
	public static String INT_30 = "ArrInt30";
	
	public static int ARRAY_SIZE = 1000;
	
	/***
	 * Test connection to the controller
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void connectToController() throws IOException, InterruptedException{
		ControlLogixConnector comm = new ControlLogixConnector(IP, 0xAF12);
		comm.connect();
		assertTrue("Connection failed", comm.isConnected());
		comm.disconnect();
	}
	
	/***
	 * Test that an array can be read without errors.
	 * @throws IOException
	 * @throws NotImplementedException
	 */
	@Test
	public void readPlcArray() throws IOException, NotImplementedException{
		ControlLogixConnector comm = new ControlLogixConnector(IP, 0xAF12);
		comm.connect();
		UnconnectedMessagingRead read = new UnconnectedMessagingRead(DINT_1000, comm.getSessionHandle());
		comm.executeMessage(read);
		
		Object o = read.getValues();
		assertNotNull(o);
		comm.disconnect();
	}
	
	/***
	 * Tests the DINT[] segmented read/write functions, validating the written data.
	 * - Writes 1000 values to the ARRAY_NAME array.
	 * - Reads 1000 values from the ARRAY_NAME array.
	 * - Compares the data written with the read.
	 * 
	 * @throws IOException
	 * @throws NotImplementedException
	 * @throws PathSegmentException
	 * @throws ItemNotFoundException
	 * @throws ResponseBufferOverflowException
	 * @throws ProcessingAttributesException
	 * @throws InsufficientCommandException
	 * @throws InsufficientNrOfAttributesException
	 * @throws OtherWithExtendedCodeException
	 * @throws InvalidTypeException
	 * @throws EmbeddedServiceException
	 */
	@Test
	public void readWriteDINTArray() throws IOException, NotImplementedException, PathSegmentException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, InvalidTypeException, EmbeddedServiceException{
		Integer[] in = new Integer[1000];
		for (int i=0; i<in.length; i++ ){
			in[i] = new Integer(0xaaa000+i);
		}
		writeArray(in, DINT_1000);
		readArray(in, DINT_1000);
		
	}
	
	@Test
	public void readWriteSINTArray() throws IOException, PathSegmentException, InvalidTypeException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, EmbeddedServiceException, NotImplementedException{
		Character[] in = new Character[200];
		for (int i=0; i<in.length; i++ ){
			in[i] = new Character((char) (i%120));
		}
		writeArray(in, SINT_200);
		readArray(in, SINT_200);
	}
	
	@Test
	public void readWriteREALArray() throws IOException, PathSegmentException, InvalidTypeException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, EmbeddedServiceException, NotImplementedException{
		Float[] in = new Float[5000];
		for (int i=0; i<in.length; i++)
			in[i] = new Float(0);
		
		writeArray(in, REAL_5000);
		readArray(in, REAL_5000);
		
		for (int i=0; i<in.length; i++)
			in[i] = new Float(i + Math.random());
		
		writeArray(in, REAL_5000);
		readArray(in, REAL_5000);
	}
	
	@Test
	public void readWriteREALArray2() throws IOException, PathSegmentException, InvalidTypeException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, EmbeddedServiceException, NotImplementedException{
		Float[] in = new Float[2];
		for (int i=0; i<in.length; i++)
			in[i] = new Float(i + Math.random());
		writeArray(in, REAL_5000);
		readArray(in, REAL_5000);
	}
	
	@Test
	public void readWriteINTArray() throws IOException, PathSegmentException, InvalidTypeException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, EmbeddedServiceException, NotImplementedException{
		Short[] in = new Short[500];
		for (int i=0; i<in.length; i++)
			in[i] = new Short((short)(Math.random()*100));
		writeArray(in, INT_500);
		readArray(in, INT_500);
	}
	
	@Test
	public void readWriteINTArray2() throws IOException, PathSegmentException, InvalidTypeException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, EmbeddedServiceException, NotImplementedException{
		Short[] in = new Short[30];
		for (int i=0; i<in.length; i++)
			in[i] = new Short((short)(Math.random()*100));
		writeArray(in, INT_30);
		readArray(in, INT_30);
	}
	
	private void writeArray(Object[] in, String name) throws IOException, PathSegmentException, InvalidTypeException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator comm = new SimpleLogixCommunicator(IP, 0xAF12);
		comm.connect();
		comm.write(name, in, in.length);
		comm.disconnect();
	}
	
	private void readArray(Object[] in, String name) throws IOException, NotImplementedException, PathSegmentException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, InvalidTypeException, EmbeddedServiceException{
		SimpleLogixCommunicator comm = new SimpleLogixCommunicator(IP, 0xAF12);
		comm.connect();
		Object o = comm.read(name, in.length);
		assertTrue("Wrong return type from read: "+o.getClass().toString(), o instanceof Object[]);
		Object[] arr = (Object[]) o;
		//assertEquals("Unexpected array size", in.length, arr.length);
		for (int i=0; i<arr.length; i++){
			//assertTrue(arr[i].equals(in[i]));
			assertEquals("Wrong array value on index "+i , in[i], arr[i]);
		}
		comm.disconnect();
	}
	
	@Test
	public void writeSimpleDINTVariable() throws IOException, PathSegmentException, InvalidTypeException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator comm = new SimpleLogixCommunicator(IP, 0xAF12);
		comm.connect();
		Integer[] in = new Integer[53];
		for (int i=0; i<in.length; i++ ){
			in[i] = new Integer(0xaaa000 + i);
		}
		comm.write(DINT_1000, in[0], 1);
		comm.disconnect();
	}
	
	@Test
	public void writeSimpleSINTVariable() throws IOException, PathSegmentException, InvalidTypeException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator comm = new SimpleLogixCommunicator(IP, 0xAF12);
		comm.connect();
		Character[] in = new Character[53];
		for (int i=0; i<in.length; i++ ){
			in[i] = new Character((char) (i+103));
		}
		comm.write(SINT_200, in[0], 1);
		comm.disconnect();
	}
	
	@Test
	public void writeFirstElement() throws IOException, PathSegmentException, InvalidTypeException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, EmbeddedServiceException, NotImplementedException{
		SimpleLogixCommunicator comm = new SimpleLogixCommunicator(IP, 0xAF12);
		comm.connect();
		//Integer[] i = new Integer[]{new Integer(0xaaa000), new Integer(0xaaa001)};
		Integer[] in = new Integer[53];
		for (int i=0; i<in.length; i++ ){
			in[i] = new Integer(i);
		}
		comm.write(DINT_1000, in, in.length);
		//comm.write(ARRAY_NAME, in, in.length);
		comm.disconnect();
	}	
	
	
	
	
	
}
