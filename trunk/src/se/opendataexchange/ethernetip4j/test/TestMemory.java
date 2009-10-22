package se.opendataexchange.ethernetip4j.test;

import java.io.IOException;

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


public class TestMemory implements Runnable{
	private static boolean stop = false;
	public static void main(String[] args) throws IOException, InterruptedException{
		stop = false;
		Thread th = new Thread(new TestMemory());
		th.start();
		System.in.read();
		stop = true;
		while(th.isAlive())
			Thread.sleep(5);
	}
	
	SvgGraphWriter fw;
	
	String[] tagNames;
	private ControlLogixMockDataSource dataSource;
	public TestMemory(){
		tagNames = new String[4];
		tagNames[0]="MC.LadderStep";
		tagNames[1]="_90EXT_T.SignalFromSterilizer";
		tagNames[2]="FQ_10T010LoopMotor.In.SpeedFeedback";
		tagNames[3]="FQ_50T050MainMotor.In.SpeedFeedback";
		dataSource = new ControlLogixMockDataSource();
		fw = new SvgGraphWriter("C:\\file2.svg");
	}
	
	private void startMessaging() throws PathSegmentException, InvalidTypeException, ItemNotFoundException, ResponseBufferOverflowException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, IOException, EmbeddedServiceException, InterruptedException, NotImplementedException {
		while(!stop){
			dataSource.read(tagNames[0]);
			//dataSource.read(tagNames);
			dataSource.read(tagNames[0], 1);
			dataSource.write(tagNames[1], (short)2, 1);
			fw.append();
			Thread.sleep(1);
		}
		fw.close();
	}
	
	@Override
	public void run() {
		try {
			startMessaging();
		} catch (PathSegmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTypeException e) {
			// TODO Auto-generated catch block
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmbeddedServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotImplementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
