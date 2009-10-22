package se.opendataexchange.ethernetip4j.test;

import java.io.IOException;

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

public class TestPerformance {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws EmbeddedServiceException 
	 * @throws InvalidTypeException 
	 * @throws ResponseBufferOverflowException 
	 * @throws OtherWithExtendedCodeException 
	 * @throws InsufficientNrOfAttributesException 
	 * @throws InsufficientCommandException 
	 * @throws ProcessingAttributesException 
	 * @throws ItemNotFoundException 
	 * @throws PathSegmentException 
	 * @throws NotImplementedException 
	 */
	public static void main(String[] args) throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException {
		TestPerformance tp = new TestPerformance();
		//String[] vars = new String[]{"TestREAL", "TestVar1", "TestBOOL", "TestSINT", "YetAnotherInt2", "YetAnotherDINT", "TestREAL", "TestVar1", "TestBOOL", "TestSINT" };
		String[] vars = new String[]{	
				"TestREAL", "TestVar1", "TestBOOL", "TestSINT", "YetAnotherInt2", "YetAnotherDINT", "TestREAL", "TestVar1", "TestBOOL", "TestSINT", 
				"TestREAL", "TestVar1", "TestBOOL"};
		tp.testMeanTimeArr(vars);
	}

	private SimpleLogixCommunicator connector;
	private long runTimeMs = 30000;
	StringBuilder log = new StringBuilder();
	
	private void testMeanTimeSingleVar() throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException {
		connector = new SimpleLogixCommunicator("192.168.200.51", 0xAF12);
		long tStart = System.currentTimeMillis();
		long intervalStart = tStart;
		int totalCount = 0;
		int intervalCount = 0;
		while (System.currentTimeMillis() - tStart < runTimeMs){
			connector.read("TestREAL");
			totalCount++;
			intervalCount++;
			if (System.currentTimeMillis() - intervalStart > 5000){
				log.append("Mean 5s: ").append(((System.currentTimeMillis() - intervalStart)/intervalCount)).append("\r\n");
				log.append("Mean total: ").append(((System.currentTimeMillis() - tStart)/totalCount)).append("\r\n");
				intervalCount = 0;
				intervalStart = System.currentTimeMillis();
			}
		}		
		log.append("Final mean 5s: ").append(((System.currentTimeMillis() - intervalStart)/intervalCount)).append("\r\n");;
		log.append("Final mean total: ").append(((System.currentTimeMillis() - tStart)/totalCount)).append("\r\n");;
		System.out.println(log.toString());
	}
	
	private void testMeanTimeArr(String[] vars) throws IOException, PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, EmbeddedServiceException, NotImplementedException {
		connector = new SimpleLogixCommunicator("192.168.200.51", 0xAF12);
		int totalCount = 0;
		int intervalCount = 0;		
		long tStart = System.currentTimeMillis();
		long intervalStart = tStart;
		while (System.currentTimeMillis() - tStart < runTimeMs){
			connector.read(vars);
			totalCount++;
			intervalCount++;
			if (System.currentTimeMillis() - intervalStart > 5000){
				log.append("Mean 5s: ").append(((System.currentTimeMillis() - intervalStart)/intervalCount)).append("\r\n");
				log.append("Mean total: ").append(((System.currentTimeMillis() - tStart)/totalCount)).append("\r\n");
				intervalCount = 0;
				intervalStart = System.currentTimeMillis();
			}
		}		
		log.append("Final mean 5s: ").append(((System.currentTimeMillis() - intervalStart)/intervalCount)).append("\r\n");;
		log.append("Final mean total: ").append(((System.currentTimeMillis() - tStart)/totalCount)).append("\r\n");;
		System.out.println(log.toString());
	}
}
