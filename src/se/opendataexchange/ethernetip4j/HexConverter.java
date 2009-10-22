package se.opendataexchange.ethernetip4j;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/***
 * Class that contains utility function for conversions to hexadecimal strings.
 * 
 */
public class HexConverter {
	private static final char[] HEX_CHAR_ARRAY = { '0', '1', '2', '3', '4',
	'5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	private static final List<Character> HEX_CHAR_LIST;

	static {
		HEX_CHAR_LIST = new ArrayList<Character>();
		HEX_CHAR_LIST.add(new Character('0'));
		HEX_CHAR_LIST.add(new Character('1'));
		HEX_CHAR_LIST.add(new Character('2'));
		HEX_CHAR_LIST.add(new Character('3'));
		HEX_CHAR_LIST.add(new Character('4'));
		HEX_CHAR_LIST.add(new Character('5'));
		HEX_CHAR_LIST.add(new Character('6'));
		HEX_CHAR_LIST.add(new Character('7'));
		HEX_CHAR_LIST.add(new Character('8'));
		HEX_CHAR_LIST.add(new Character('9'));
		HEX_CHAR_LIST.add(new Character('A'));
		HEX_CHAR_LIST.add(new Character('B'));
		HEX_CHAR_LIST.add(new Character('C'));
		HEX_CHAR_LIST.add(new Character('D'));
		HEX_CHAR_LIST.add(new Character('E'));
		HEX_CHAR_LIST.add(new Character('F'));
	}

	/***
	 * Converts a byte to a hex string and puts the result in a @link {@link StringBuffer}.
	 * @param b The byte to convert.
	 * @param buf {@link StringBuffer} where the resulting two characters are put.
	 */
	public static void byte2hex(byte b, StringBuffer buf) {
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);
		buf.append(HEX_CHAR_ARRAY[high]);
		buf.append(HEX_CHAR_ARRAY[low]);
	}
	
	/***
	 * Converts a byte to a hex string.
	 * @param b The byte to convert.
	 * @return The hex string.
	 */
	public static String byte2hex(byte b) {
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);
		return new String(new char[]{HEX_CHAR_ARRAY[high], HEX_CHAR_ARRAY[low]});		
	}

	/***
	 * Converts a (2 character) string to a hex byte.
	 * @param s The string
	 * @return The byte
	 */
	public static byte hex2byte(String s) {
		int high = HEX_CHAR_LIST.indexOf(new Character(s.charAt(0))) << 4;
		int low = HEX_CHAR_LIST.indexOf(new Character(s.charAt(1)));
		return (byte) (high + low);
	}
	
	/***
	 * Converta a hex string to byte array.
	 * @param hex The hex string.
	 * @return The byte array.
	 */
	public static byte[] toByteArray(String hex) {
		int len = (hex.length() + 1) / 3;
		byte[] rtn = new byte[len];
		for (int i = 0; i < len; i++) {
			rtn[i] = hex2byte(hex.substring(i * 3, i * 3 + 2));
		}
		return rtn;
	}
	
	/***
	 * Prints a byte buffer as hex string to {@link System#out}
	 * @param buffer
	 */
	public static void printByteBuffer(ByteBuffer buffer){
		byte[] b = buffer.array();
		for(int i=0 ; i<b.length ; i++){
			System.out.print(HexConverter.byte2hex(b[i])+" ");
		}
	}
}

