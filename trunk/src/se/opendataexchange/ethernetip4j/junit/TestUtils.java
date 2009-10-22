package se.opendataexchange.ethernetip4j.junit;

import java.nio.ByteBuffer;

import se.opendataexchange.ethernetip4j.HexConverter;
import se.opendataexchange.ethernetip4j.Log;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;


public class TestUtils {
	public static final boolean DISABLE_PRINTS = true;
	public static final boolean CRASHON_PRINTS = true;
	
	public static void printByteBuffer(ByteBuffer buffer) throws NotImplementedException {
		if (CRASHON_PRINTS)
			throw new NotImplementedException();
		
		Log.p("Limit: "+buffer.limit());
		printByteBuffer(buffer, 0, buffer.limit());
	}
	public static void printByteBuffer(ByteBuffer buffer, int offset, int count) throws NotImplementedException {
		if (CRASHON_PRINTS)
			throw new NotImplementedException();
		
		byte[] b = buffer.array();
		for (int i = offset; i < buffer.limit() && i < offset + count; i++) {
			// System.out.print((int)b[i]+" ");
			System.out.print("0x"+HexConverter.byte2hex(b[i]) + " ");
			if (i%10 == 9)
				System.out.print("\r\n");
		}
	}
	public static void printByteBufferAsArray(ByteBuffer buffer) throws NotImplementedException {
		if (CRASHON_PRINTS)
			throw new NotImplementedException();
		
		byte[] b = buffer.array();
		System.out.print("new byte[]{");
		for (int i = 0; i < buffer.limit(); i++) {
			// System.out.print((int)b[i]+" ");
			System.out.print("(byte)0x"+HexConverter.byte2hex(b[i]) + ", ");
			if (i%10 == 9)
				System.out.print("\r\n");
		}
		System.out.print("};");
	}
	
	public static void printBytes(float f) throws NotImplementedException{
		if (CRASHON_PRINTS)
			throw new NotImplementedException();
		
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putFloat(f);
		printByteBuffer(bb);
	}
	public static void printBytes(int i) throws NotImplementedException{
		if (CRASHON_PRINTS)
			throw new NotImplementedException();
		
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(i);
		printByteBuffer(bb);
	}
	public static void printBytes(long i) throws NotImplementedException{
		if (CRASHON_PRINTS)
			throw new NotImplementedException();
		
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.putLong(i);
		printByteBuffer(bb);
	}
	public static void printBytes(short i) throws NotImplementedException{
		if (CRASHON_PRINTS)
			throw new NotImplementedException();
		
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.putShort(i);
		printByteBuffer(bb);
	}
}
