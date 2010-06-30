package se.opendataexchange.ethernetip4j;

import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;

/***
 * 
 */
public class EthernetIpDataTypeValidator {
	/***
	 * PLC data types.
	 */
	private static final byte BOOL = (byte) 0xC1;
	private static final byte BIT_ARRAY = (byte) 0xD3;
	private static final byte SINT = (byte) 0xC2;
	private static final byte INT = (byte) 0xC3;
	private static final byte DINT = (byte) 0xC4;
	//private static final byte LINT = (byte) 0xC5;
	private static final byte REAL = (byte) 0xCA;
	//private static final byte UDT = (byte) 0xA0;
	
	public static byte getType(Object value) throws NotImplementedException {
		if (value instanceof Boolean)
			return BOOL;
		else if (value instanceof byte[])
			return BIT_ARRAY;
		else if (value instanceof Character)
			return SINT;
		else if (value instanceof Short)
			return INT;
		else if (value instanceof Integer)
			return DINT;
		else if (value instanceof Float){
			return REAL;
		}
		else if (value instanceof Object[]){
			return getType(((Object[])value)[0]);
		}
		throw new NotImplementedException();
	}
	
	/***
	 * Get all values from a certain buffer, beginning with offset.
	 * 
	 * The expected buffer is:
	 * byte
	 * 0: Type
	 * 1: 0 (padding byte)
	 * 2-n: One or more values of the specified type. The buffer length (limit) is used for determining the number of values.
	 * 
	 * @param buffer
	 * @param offset
	 * @return Tag values
	 * @throws InvalidTypeException 
	 */
	public static Object getValues(EthernetIpBufferUtil buffer, int offset, int payload) throws InvalidTypeException{
		int count;
		switch (buffer.getByte(offset)){
		case BOOL:
			count = payload;
			if (count > 1){
				Boolean[] object = new Boolean[count];
				for (int x = 0; x < count; x++){
					object[x] = new Boolean(buffer.getByte(2 + x + offset) != 0);
				}
				return object;
			}else if (count==1){
				return new Boolean(buffer.getByte(2 + offset) != 0);
			}
			break;
		case BIT_ARRAY:
			return buffer.getByteArray(2 + offset, 1);
		case SINT:
			count = payload;
			if (count > 1){
				Character[] object = new Character[count];
				for (int x = 0; x < count; x++){
					char c = buffer.getSINT(2 + x + offset);
					object[x] = c;
				}
				return object;
			}else if (count==1){
				return buffer.getSINT(2 + offset);
			}
			break;
		case INT: 
			count = payload/2;
			if (count > 1){
				Short[] object = new Short[count];
				for (int x = 0; x < count; x++){
					object[x] = buffer.getINT(2 + 2*x + offset);
				}
				return object;
			}else if (count==1){
				return buffer.getINT(2 + offset);
			}
			break;
		case DINT:
			count = payload/4;
			if (count > 1){
				Integer[] object = new Integer[count];
				for(int x=0;x<count;x++){
					object[x] = buffer.getDINT(2 + 4*x + offset);
				}
				return object;
			}else if (count == 1){
				return  ((int)buffer.getDINT(2 + offset));
			}
			break;
		case REAL:
			count = payload/4;
			if (count > 1){
				Float[] object = new Float[count];
				for(int x=0;x<count;x++){
					object[x] = buffer.getREAL(offset + 4*x + 2);
				}
				return object;
			}else if (count == 1){
				return buffer.getREAL(offset + 2);
			}
			break;
		default:
			throw new InvalidTypeException("Unsupported type: 0x"+HexConverter.byte2hex(buffer.getByte(offset)));
		}
		return null;
	}

	public static void putValues(Object value, EthernetIpBufferUtil buffer, int offset, int arraySize, int dataOffset) throws NotImplementedException {
		if (value instanceof Object[]){
			int tmpOffset = offset;
			Object[] v = (Object[]) value;
			int vSize = sizeOf(v[0]);
			int startIdx = dataOffset/vSize;
			for (int i=startIdx; i<(startIdx + arraySize); i++){
				putValue(v[i], buffer, tmpOffset);
				tmpOffset += vSize;
			}
		}else{
			putValue(value, buffer, offset);
		}
	}
	
	public static void putValue(Object value, EthernetIpBufferUtil buffer, int offset) throws NotImplementedException {
		if (value instanceof Boolean)
			if ((Boolean)value)
				buffer.putByte(offset, (byte) 0x01);
			else
				buffer.putByte(offset, (byte) 0x00);
		else if (value instanceof byte[])
			buffer.putByteArray(offset, (byte[]) value);
		else if (value instanceof Character)
			buffer.putSINT((Character)value, offset);
		else if (value instanceof Short)
			buffer.putINT(offset, (Short)value);
		else if (value instanceof Integer)
			buffer.putDINT(offset, (Integer)value);
		else if (value instanceof Float)
			buffer.putREAL(offset, (Float)value);
		else if (value instanceof Object[])
			putValue(((Object[])value)[0], buffer, offset);
		else
		{
			throw new NotImplementedException();
		}
	}
	
	public static int sizeOf(Object value) throws NotImplementedException {
		switch(getType(value)){
		case BOOL: return 1;
		case BIT_ARRAY: return ((byte[])value).length;
		case SINT: return 1;
		case INT: return 2;
		case DINT: return 4;
		case REAL: return 4;
		}
		throw new NotImplementedException();
	}
	
	public static void putTypeAndValue(Object value, EthernetIpBufferUtil buffer, int offset) throws NotImplementedException {
		buffer.putByte(offset, getType(value));
		putValue(value, buffer, offset+2);
	}

	/***
	 * Get a certain number of values from a CipMessageRouterResponse.
	 * 
	 * The expected buffer is:
	 * byte
	 * 0: Type
	 * 1: 0 (padding byte)
	 * 2-n: "count" values of the specified type.
	 * 
	 * @param buffer
	 * @param offset
	 * @return Tag values
	 * @throws InvalidTypeException 
	 */	
	public static Object getValues(EthernetIpBufferUtil buffer, int offset) throws InvalidTypeException {
		return getValues(buffer, offset, buffer.getBufferLength());
	}

	public static Object getNumberedValues(EthernetIpBufferUtil buffer,	int offset, int count) throws InvalidTypeException, NotImplementedException {
		byte type = buffer.getByte(offset);
		if (type == BOOL || type == SINT)
			return getValues(buffer, offset, count);
		if (type == INT || type == REAL)
			return getValues(buffer, offset, count*2);
		if (type == DINT)
			return getValues(buffer, offset, count*4);
		throw new NotImplementedException();
	}
}
