package se.opendataexchange.ethernetip4j.test;

import java.nio.ByteOrder;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.Log;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;
import se.opendataexchange.ethernetip4j.junit.TestUtils;

public class Endianness {

	public Endianness() {
		
	}

	/**
	 * @param args
	 * @throws NotImplementedException 
	 */
	public static void main(String[] args) throws NotImplementedException {
		new Endianness().play2();
	}

	@SuppressWarnings("unused")
	private void play(){
		EthernetIpBufferUtil buffer = new EthernetIpBufferUtil(8);
		buffer.putByte(0, (byte) 0x01);
		int a = buffer.getUINT(0);
		buffer.putByte(4, (byte) 0x01);
		a = buffer.getUINT(4);
		
	}
	
	private void play2() throws NotImplementedException{
		EthernetIpBufferUtil buffer = new EthernetIpBufferUtil(8);
		buffer.putREAL(0, 0);
		TestUtils.printByteBuffer(buffer.getBuffer());
		int a = buffer.getUINT(0);
		Log.p(" " +a);
		
		buffer.putByte(4, (byte) 0x01);
		a = buffer.getUINT(4);
		Log.p(" " +a);
		
	}
	
	@SuppressWarnings("unused")
	private void play3() throws NotImplementedException{
		java.nio.ByteBuffer b = java.nio.ByteBuffer.allocate(4);
		b.order(ByteOrder.LITTLE_ENDIAN);
		//b.putFloat(999999);
		//b.putFloat(12345);
		b.putFloat(-123);
		//b.putInt(4);
		TestUtils.printByteBuffer(b);
	}
}
