package se.opendataexchange.ethernetip4j.junit;

import java.io.IOException;

import junit.framework.Assert;
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

/***
 * More extensive Read/write tests
 * 
 * @author Maja Arvehammar
 *
 */
public class ExtensiveRWTest {
	
	private SimpleLogixCommunicator connector = null;
	
	@org.junit.Before
	public void initConnector() throws IOException{
		connector = new SimpleLogixCommunicator(
				"192.168.200.51", 0xAF12);
	}
	
	@org.junit.After
	public void tearDownConnector() throws IOException{
		connector.disconnect();
		connector = null;
	}
	
	@org.junit.Test
	public void testDINT() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		Integer[] a = new Integer[]{0x1234, 0x4567, 0xAABB};
		extensiveRW("TestVar1", a);
		extensiveRW("TestVar1", a);
		extensiveRW("TestVar1", a);
	}
	
	@org.junit.Test
	public void testBOOL() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		Boolean[] a = new Boolean[]{true, false, true};
		extensiveRW("TestBOOL", a);
		extensiveRW("TestBOOL", a);
		extensiveRW("TestBOOL", a);
	}
	
	@org.junit.Test
	public void testSINT() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		Character[] a = new Character[]{'q', 'a', 'z'};
		extensiveRW("TestSINT", a);
		extensiveRW("TestSINT", a);
		extensiveRW("TestSINT", a);
	}
	
	@org.junit.Test
	public void testINT() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		Short[] a = new Short[]{167, 233, -9215};
		extensiveRW("YetAnotherInt2", a);
		extensiveRW("YetAnotherInt2", a);
		extensiveRW("YetAnotherInt2", a);
	}
	
	@org.junit.Test
	public void testREAL() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		Float[] a = new Float[]{(float)13.3, (float)4.4, (float)-981.45};
		extensiveRW("TestREAL", a);		
	}
	
	@org.junit.Test
	public void testArrayREALShort() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		extensiveRWArr("ArrayReal", new Float[3]);
	}
	
	@org.junit.Test
	public void testArrayREAL() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		extensiveRWArr("ArrayReal", new Float[5000]);
	}
	
	@org.junit.Test
	public void testArrayDINT() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		extensiveRWArr("ArrayDINT", new Integer[5000]);
	}
	
	@org.junit.Test
	public void testArraySINT() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		extensiveRWArr("ArraySINT", new Character[500]);// TODO: 5000 does not work
	}
	
	@org.junit.Test
	public void testArrayINT() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		extensiveRWArr("ArrayINT", new Short[5000]);
	}
	
	/*TODO:
	@org.junit.Test
	public void testArrayBOOL() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		extensiveRWArr("ArrayBOOL", new Float[5000]);
	}*/
	/*
	@org.junit.Test
	public void testArrayBOOL() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException{
		Boolean[] a = new Boolean[1];
		Boolean[] b = new Boolean[500];
		Boolean[] c = new Boolean[4500];

		for (int i=0; i<a.length; i++){
			a[i] = i%2==0;
		}
		for (int i=1; i<b.length; i++){
			b[i] = i%3==0;
		}
		for (int i=1; i<c.length; i++){
			c[i] = i%5!=0;
		}
		//Boolean[][] x = new Boolean[][]{a, b, c};
		//extensiveRWArr("ArrayBool", x);
		connector.write("ArrayBool", a, a.length);
		Object o = connector.read("ArrayBool", a.length);
		
		if (o instanceof Object[]){
			Object[] oArr = (Object[])o;
			System.out.println("1. Class: "+oArr.getClass().toString());
			for (Object item : oArr){
				System.out.println("2. Class: "+item.getClass().toString());
				if (item instanceof Object[]){
					System.out.println("Is array!");
				}else if (item instanceof byte[]){
					System.out.println("is byte array "+((byte[])item).length);
					byte[] bitem = (byte[])item;
					for(byte bi : bitem){
						System.out.println(bi +", ");
					}
				}
			}
		}
	}
	*/
	/***
	 * Test extensively read/write sequences
	 * @param values Test objects to write
	 * @param expectedType
	 * @throws NotImplementedException 
	 * @throws EmbeddedServiceException 
	 * @throws IOException 
	 * @throws OtherWithExtendedCodeException 
	 * @throws InsufficientNrOfAttributesException 
	 * @throws InsufficientCommandException 
	 * @throws ProcessingAttributesException 
	 * @throws ResponseBufferOverflowException 
	 * @throws ItemNotFoundException 
	 * @throws InvalidTypeException 
	 * @throws PathSegmentException 
	 */
	private void extensiveRW(String tagName, Object[] v) throws PathSegmentException, InvalidTypeException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, IOException, EmbeddedServiceException, NotImplementedException{
		connector.write(tagName, v[0], 1);
		readAndValidate(tagName, v[0]);
		
		connector.write(tagName, v[1], 1);
		readAndValidate(tagName, v[1]);
		
		connector.write(tagName, v[0], 1);
		connector.write(tagName, v[1], 1);
		connector.write(tagName, v[2], 1);
		readAndValidate(tagName, v[2]);
		
		connector.read(tagName);
		connector.read(tagName);
		readAndValidate(tagName, v[2]);
		
		connector.write(tagName, v[0], 1);
		readAndValidate(tagName, v[0]);
	}
	
	private void extensiveRWArr(String tagName, Object[] v) throws PathSegmentException, InvalidTypeException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, IOException, EmbeddedServiceException, NotImplementedException{
		TestUtils.randomize(v);
		// Read, read, write, read
		connector.read(tagName, v.length);
		connector.read(tagName, v.length);
		connector.write(tagName, v, v.length);
		readAndValidateArr(tagName, v, v.length);
		
		// Write, write read
		TestUtils.randomize(v);
		connector.write(tagName, v, v.length);
		TestUtils.randomize(v);
		connector.write(tagName, v, v.length);
		readAndValidateArr(tagName, v,  v.length);
		
		// Read write read write, 5 times
		int interval = Math.max(1, v.length/5);
		for (int i=0; i<v.length; i+=interval){
			TestUtils.randomize(v);
			connector.write(tagName, v, i+1);
			readAndValidateArr(tagName, v, i+1);
		}
	}
	
	private void readAndValidate(String tagName, Object expected) throws PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException{
		Object o = connector.read(tagName);
		Assert.assertEquals(expected.getClass(), o.getClass());
		Assert.assertEquals(expected, o);
	}
	
	private void readAndValidateArr(String tagName, Object[] expected, int len) throws PathSegmentException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException{
		Object o = connector.read(tagName, len);
		Assert.assertNotNull(o);
		Assert.assertTrue(o instanceof Object[]);
		Object[] oArr = (Object[])o;
		Assert.assertEquals(len, oArr.length);
		for (int i=0; i<len; i++){
			Assert.assertEquals("index "+i, expected[i].getClass(), oArr[i].getClass());
			Assert.assertEquals("index "+i, expected[i], oArr[i]);
		}
	}
}
