package se.opendataexchange.ethernetip4j.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;

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

public class TestClass {

	public static void main(String[] args) throws NotImplementedException {
		SimpleLogixCommunicator connector = null;
		try {
			connector = new SimpleLogixCommunicator(
					"192.168.200.51", 0xAF12);
			long start = new Date().getTime();
			Thread.sleep(2000);
			String[] tagNames = new String[1];
			//tagNames[0]="TestDINT";
			tagNames[0]="TestREAL";

			Object[] objects = connector.read(tagNames);
			for(Object ob:(Object[])objects){
				System.out.println("Value read");
				System.out.println("==========");
				System.out.println("Type: " + ob.getClass().toString());
				System.out.println("Value: " + ob.toString());
			}
			long time = new Date().getTime()-start;
			System.out.println("All loops took ms: "+time);
		} catch (PathSegmentException e) {
			e.printStackTrace();
		} catch (InvalidTypeException e) {
			e.printStackTrace();
		} catch (ItemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResponseBufferOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProcessingAttributesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientNrOfAttributesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OtherWithExtendedCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}catch(EmbeddedServiceException e){
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (connector != null)
				connector.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	private static void printByteBuffer(ByteBuffer buffer) {
		byte[] b = buffer.array();
		for (int i = 0; i < b.length; i++) {
			// System.out.print((int)b[i]+" ");
			System.out.print(HexConverter.byte2hex(b[i]) + " ");
		}
	}
}
