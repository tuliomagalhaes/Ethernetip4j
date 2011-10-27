package se.opendataexchange.ethernetip4j.junit;

import java.nio.ByteBuffer;

import se.opendataexchange.ethernetip4j.HexConverter;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;


public class TestUtils {
	public static final boolean DISABLE_PRINTS = true;
	public static final boolean CRASHON_PRINTS = true;
	
	public static void printByteBuffer(ByteBuffer buffer) throws NotImplementedException {
		if (CRASHON_PRINTS)
			throw new NotImplementedException();
		
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
	
	/***
	 * Randomize all values in object array.
	 * Integer (DINT, 4 bytes)
	 * Short (INT, 2 bytes, [-32767, 32767])
	 * Boolean (0, 1)
	 * Character (SINT, 1 byte [0, 255]
	 * Float (REAL, 4 bytes [-1000, 1000]
	 * @param v
	 * @throws NotImplementedException 
	 */
	public static void randomize(Object[] v) throws NotImplementedException{
		if (v instanceof Integer[]){
			for(int i=0; i<v.length; i++){
				v[i] = getRandom(Integer.MIN_VALUE, Integer.MAX_VALUE);
			}
		}else if (v instanceof Short[]){
			for(int i=0; i<v.length; i++){
				v[i] = (short)getRandom(Short.MIN_VALUE, Short.MAX_VALUE);
			}
		}else if (v instanceof Boolean[]){
			for(int i=0; i<v.length; i++){
				v[i] = (getRandom(0, 1) == 1);
			}
		}else if (v instanceof Character[]){
			for(int i=0; i<v.length; i++){
				v[i] = (char)getRandom(0, 127); // 255
			}
		}else if (v instanceof Float[]){
			for(int i=0; i<v.length; i++){
				v[i] = (float)(-1000.0 + Math.random()*1000.0);
			}
		}else{
			throw new NotImplementedException();
		}
	}
	
	private static int getRandom(int min, int max){
		double a = min + Math.random()*(max - min);
		return (int)Math.round(a);
	}
}
