package se.opendataexchange.ethernetip4j;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/***
 * A Wrapper around a {@link ByteBuffer}. Provides methods to get/put the primitive
 * types of the PLC system:
 * 
 * USINT  	1 byte (unsigned small integer)
 * UINT  	2 byte (unsigned integer)
 * INT		2 byte (integer)
 * REAL		4 byte (floating point value)
 * UDINT  	4 byte (unsigned double integer)
 * DINT 	4 byte (double integer)
 * LINT		8 byte (long integer)
 * 
 * This buffer puts/gets the bytes according to the LITTLE_ENDIAN format.
 * 
 */
public class EthernetIpBufferUtil {
	private ByteBuffer tmpBuffer = ByteBuffer.allocate(8);
	
	private ByteBuffer buffer;
	
	/***
	 * Swaps the byte of a {@link Short} (2 byte) value.
	 * @param data The value
	 * @return The swapped value
	 */
	static public short swapBytes(short data){
		ByteBuffer buff = ByteBuffer.allocate(2).putShort(data).order(ByteOrder.LITTLE_ENDIAN);
		buff.flip();
		return buff.getShort();
		//return (short)((data>>8)&0xff)+(((data << 8)&0xff00));
	}

	/***
	 * Swaps the byte of a {@link Integer} (4 byte) value.
	 * @param data The value
	 * @return The swapped value
	 */
	static public int swapBytes(int data){
		ByteBuffer buff=ByteBuffer.allocate(4).putInt(data).order(ByteOrder.LITTLE_ENDIAN);
		buff.flip();
		return buff.getInt();
	}
	
	/***
	 * Swaps the byte of a {@link Long} (8 byte) value.
	 * @param data The value
	 * @return The swapped value
	 */
	static public long swapBytes(long data){
		ByteBuffer buff=ByteBuffer.allocate(8).putLong(data).order(ByteOrder.LITTLE_ENDIAN);
		buff.flip();
		return buff.getLong();
	}

	/***
	 * Swaps the byte of a {@link Float} (2 byte) value.
	 * @param data The value
	 * @return The swapped value
	 */
	static public float swapBytes(float data){
		ByteBuffer buff=ByteBuffer.allocate(4).putFloat(data).order(ByteOrder.LITTLE_ENDIAN);
		buff.flip();
		return buff.getFloat();
	}
	
	/***
	 * Allocate a buffer.
	 * @param capacity Buffer capacity.
	 */
	public EthernetIpBufferUtil(int capacity){
		buffer = ByteBuffer.allocate(capacity);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		tmpBuffer.order(ByteOrder.LITTLE_ENDIAN);		
	}
    
	/***
	 * Wrap this buffer utility around an existing byte buffer.
	 * @param buffer The existing buffer.
	 */
    public EthernetIpBufferUtil(ByteBuffer buffer){
        this.buffer = buffer; 
    }
    
    /***
     * Wrap this buffer utility around an existing byte array.
     * @param buffer The existing buffer.
     */
    public EthernetIpBufferUtil(byte[] buffer){
    	this.buffer = ByteBuffer.wrap(buffer);
    }
    
    /***
     * Put a byte into the buffer.
     * @param offset Offset from beginning of buffer.
     * @param value The byte to put.
     */
    public void putByte(int offset, byte value){
    	buffer.put(offset,value);
    }
    
    /***
     * Get a byte from the buffer.
     * @param offset Offset from beginning of buffer. 
     * @return The byte.
     */
    public byte getByte(int offset){
    	return buffer.get(offset);
    }
	
    /***
     * Put unsigned small integer (1 byte) into the buffer.
     * @param offset The offset from beginning of buffer.
     * @param value The value to put (0-255).
     */
    public void putUSINT(int offset, short value){
		buffer.put(offset,(byte)(value&0xff));
	}
	
    /***
     * Get unsigned small integer (1 byte) from a buffer.
     * @param offset The offset from beginning of buffer.
     */
	public short getUSINT(int offset){
		short result=0;
		result |= 0xff &buffer.get(offset);
		return result;
	}
	
	/***
     * Put unsigned integer (2 bytes) into the buffer.
     * @param offset The offset from beginning of buffer.
     * @param value The value to put.
     */
    public void putUINT(int offset, int value){
    	tmpBuffer.clear();
    	tmpBuffer.putInt(value);
    	buffer.put(offset, tmpBuffer.get(0));
    	buffer.put(offset+1, tmpBuffer.get(1));
	}
    
	/***
     * Get unsigned integer (2 byte) from a buffer.
     * @param offset The offset from beginning of buffer.
     * @return the integer.
	 */
	public int getUINT(int offset){
		tmpBuffer.clear();
		tmpBuffer.put(buffer.get(offset));
		tmpBuffer.put(buffer.get(offset+1));
		tmpBuffer.put((byte)0x00);
		tmpBuffer.put((byte)0x00);
		int result = tmpBuffer.getInt(0);
		return result;
	}
	
	/***
	 * Put an array of bytes into the buffer.
	 * @param offset Where to put the array, offset from beginning of buffer.
	 * @param data The byte array.
	 */
	public void putByteArray(int offset, byte[] data){
		for(int i=0 ; i<data.length ;i++){
			buffer.put(offset+i, data[i]);
		}
	}
	
	/***
	 * Get a byte array from a buffer.
     * @param offset The offset from beginning of buffer.
     * @param size The number of bytes to get.
	 * @return The byte array.
	 */
	public byte[] getByteArray(int offset, int size){
		byte[] arr = new byte[size];
		for(int i=offset ; i<offset+size ; i++){
			arr[i-offset] = buffer.get(i);
		}
		return arr;
	}
	
	/***
	 * Put a real (4 byte floating point) value into the buffer.
	 * @param offset Offset from beginning of buffer.
	 * @param value The float value to put.
	 */
	public void putREAL(int offset, float value){
		buffer.putFloat(offset, value);
	}
	
	/***
	 * Get a real (4 byte floating point) value from the buffer.
	 * @param offset Offset from beginning of buffer.
	 * @return The real value.
	 */
	public float getREAL(int offset){
		return buffer.getFloat(offset);
	}
	
	/***
	 * Put an unsigned double integer (4 byte) value into the buffer.
	 * @param offset Offset from beginning of buffer.
	 * @param value The value to put.
	 */
	public void putUDINT(int offset, long value){
		tmpBuffer.clear();
		tmpBuffer.putLong(value);
		for (int i=0; i<4; i++){
			buffer.put(offset+i, tmpBuffer.get(i));
		}
	}
	
	/***
	 * Get an unsigned double integer (4 byte) value from the buffer.
	 * @param offset Offset from beginning of buffer.
	 * @return The value.
	 */
	public long getUDINT(int offset){
		tmpBuffer.clear();
		for (int i=0; i<4; i++)
			tmpBuffer.put(buffer.get(offset+i));
		tmpBuffer.put((byte)0x00);
		tmpBuffer.put((byte)0x00);
		tmpBuffer.put((byte)0x00);
		tmpBuffer.put((byte)0x00);
		return tmpBuffer.getLong(0);		
	}
	
	/***
	 * @return The byte buffer.
	 */
	public ByteBuffer getBuffer(){
		return buffer;
	}
	
	/***
	 * @return The buffer limit.
	 */
	public int getBufferLength(){
		return buffer.limit();
	}

	/***
	 * Put a double integer value (4 byte) into the buffer.
	 * @param offset Offset from beginning of buffer.
	 * @param value The value to put.
	 */
	public void putDINT(int offset, int value) {
		buffer.putInt(offset, value);
	}
	/***
	 * Get 4 byte signed value from buffer
	 * @param offset
	 * @return
	 */
	public int getDINT(int offset) {
		return buffer.getInt(offset);
	}
	public char getSINT(int offset) {
		return (char)buffer.get(offset);
	}

	public short getINT(int offset) { /* 2 byte signed value */
		tmpBuffer.clear();
		tmpBuffer.put(buffer.get(offset));
		tmpBuffer.put(buffer.get(offset+1));
		tmpBuffer.flip();
		return tmpBuffer.getShort();
	}

	public void putSINT(char value, int offset) {
		buffer.put(offset, (byte) value);
	}
	/***
	 * 2 byte signed integer
	 * @param va
	 */
	public void putINT(int offset, short value) {
		buffer.putShort(offset, value);		
	}
}
