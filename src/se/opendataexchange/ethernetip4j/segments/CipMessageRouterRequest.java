package se.opendataexchange.ethernetip4j.segments;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.EthernetIpDataTypeValidator;
import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;
import se.opendataexchange.ethernetip4j.services.CipCommandServices;

/**
 * 
 * <table border="1">
 * <tr><th>Byte</th><th>		Name</th><th>					Type</th><th>							Description</th></tr>
 * <tr><td>0</td><td>			Service</td><td> 				USINT</td><td>							{@link CipCommandServices}</td></tr>
 * <tr><td>1</td><td>			Request path size</td><td>	 	USINT</td><td> 							Tag name length (+1 if odd)</td></tr>
 * <tr><td>2 - x</td><td>		EPATH</td><td> 					Byte[]</td><td> 						Tag name (special if structure)</td></tr>
 * <tr><td>(x+1) - y</td><td>	Request data</td><td> 			Byte[]</td><td>							Depends on service</td></tr>
 * </table>
 * 
 */
public class CipMessageRouterRequest {
	private static int DEFAULT_OFFSET = 
		EthernetIpEncapsulationHeader.SEGMENT_LENGTH + 
		EthernetIpCommandSpecificData.SEGMENT_LENGTH +
		EthernetIpItemStruct.SEGMENT_LENGTH +
		CipPacketRequest.SEGMENT_LENGTH +
		CipCommandSpecificDataRequest.SEGMENT_DATA_OFFSET;

	EthernetIpBufferUtil buffer;

	/***
	 * Fills input buffer. Uses default offset.
	 * @param serviceId
	 * @param tagName
	 * @param value
	 * @param arraySize
	 * @param messageBuffer
	 * @return Segment length.
	 * @throws InvalidTypeException 
	 * @throws NotImplementedException 
	 */
	public static int fillBuffer(int serviceId, String tagName, Object value,int arraySize, EthernetIpBufferUtil messageBuffer) throws InvalidTypeException, NotImplementedException {
		return fillBuffer(serviceId, tagName, value, arraySize, messageBuffer, DEFAULT_OFFSET);
	}
	
	public static int fillBuffer(int serviceId, String tagName, Object value,int arraySize, EthernetIpBufferUtil messageBuffer, int offset) throws InvalidTypeException, NotImplementedException {
		//int segmentLength = getSegmentLength(serviceId, tagName, value, arraySize);
		int segmentLength = 0;
		segmentLength += fillService(messageBuffer, offset + segmentLength, (short) serviceId);
		segmentLength += fillRequestPath(messageBuffer, offset + segmentLength, tagName);
		segmentLength += fillRequestData(messageBuffer, offset + segmentLength, serviceId, tagName, value, arraySize);
		//setService(messageBuffer, offset, (short)serviceId);
		//setRequestPathSize(messageBuffer, offset, serviceId, (short)tagName.length());
		//setEPATH(messageBuffer, offset, tagName);
		//setRequestData(messageBuffer, offset, serviceId, tagName, value, arraySize);
		return segmentLength;
	}
	
	public static int fillBuffer(int serviceId, String tagName, Object value,int arraySize, int dataOffset, EthernetIpBufferUtil messageBuffer) throws InvalidTypeException, NotImplementedException {
		return fillBuffer(serviceId, tagName, value, arraySize, dataOffset, messageBuffer, DEFAULT_OFFSET);
	}
	
	public static int fillBuffer(int serviceId, String tagName, Object value,int arraySize, int dataOffset, EthernetIpBufferUtil messageBuffer, int offset) throws InvalidTypeException, NotImplementedException {
		//int segmentLength = getSegmentLength(serviceId, tagName, value, arraySize);
		int segmentLength=0;
		segmentLength += fillService(messageBuffer, offset + segmentLength, (short) serviceId);
		segmentLength += fillRequestPath(messageBuffer, offset + segmentLength, tagName);
		segmentLength += fillRequestData(messageBuffer, offset + segmentLength, serviceId, tagName, value, arraySize, dataOffset);
		//setService(messageBuffer, offset, (short)serviceId);
		//setRequestPathSize(messageBuffer, offset, serviceId, (short)tagName.length());
		//setEPATH(messageBuffer, offset, tagName);
		//setRequestData(messageBuffer, offset, serviceId, tagName, value, arraySize, dataOffset);
		return segmentLength;
	}
	
	/***
	 * 
	 * @param serviceId
	 * @param tagName
	 * @param value
	 * @param arraySize
	 * @param dataOffset
	 * @param writeCount when writing segmented arrays, this is how many values from the array to be put into the message
	 * @param messageBuffer
	 * @return CIP message length (segment length).
	 * @throws InvalidTypeException
	 * @throws NotImplementedException
	 */
	public static int fillBuffer(int serviceId, String tagName, Object value,int arraySize, int dataOffset, int writeCount, EthernetIpBufferUtil messageBuffer) throws InvalidTypeException, NotImplementedException {
		return fillBuffer(serviceId, tagName, value, arraySize, dataOffset, writeCount, messageBuffer, DEFAULT_OFFSET);
	}
	
	public static int fillBuffer(int serviceId, String tagName, Object value,int arraySize, int dataOffset, int writeCount, EthernetIpBufferUtil messageBuffer, int offset) throws InvalidTypeException, NotImplementedException {
		//int segmentLength = getSegmentLength(serviceId, tagName, value, writeCount);
		int segmentLength = 0;
		segmentLength += fillService(messageBuffer, offset + segmentLength, (short) serviceId);
		segmentLength += fillRequestPath(messageBuffer, offset + segmentLength, tagName);
		segmentLength += fillRequestData(messageBuffer, offset + segmentLength, serviceId, tagName, value, arraySize, dataOffset, writeCount);
		//setService(messageBuffer, offset, (short)serviceId);
		//setRequestPathSize(messageBuffer, offset, serviceId, (short)tagName.length());
		//setEPATH(messageBuffer, offset, tagName);
		//setRequestData(messageBuffer, offset, serviceId, tagName, value, arraySize, dataOffset, writeCount);
		return segmentLength;
	}
	
	/*** Region: Getters and setters ***/
	private static int fillService(EthernetIpBufferUtil buffer, int offset, short value) {
		buffer.putUSINT(0 + offset, value);
		return 1;
	 }

	private static int fillRequestPath(EthernetIpBufferUtil buffer, int offset, String tagName) throws NotImplementedException {
		int xoffset = offset + 1; //Skip a byte to make room for the path size
		String[] tagNames = tagName.split("\\.");
		for (String tag : tagNames) {
			boolean arr = false;
			long[] arrIndices = new long[0];
			if (tag.contains("[")) {
				arr = true;
				String[] parts = tag.split("\\[");
				tag = parts[0];

				String[] idx = parts[1].replace("]", "").split(",");
				arrIndices = new long[idx.length];
				for (int i = 0; i < idx.length; i++) {
					arrIndices[i] = Long.parseLong(idx[i].trim());
				}
			}

			buffer.putByte(xoffset, (byte) 0x91); //Extended symbol
			xoffset++;
			buffer.putByte(xoffset, (byte) tag.length()); //Length of tag name
			xoffset++;
			buffer.putByteArray(xoffset, tag.getBytes()); //Tag name
			xoffset += tag.length();
			if (isOdd(tag.length())) {
				buffer.putByteArray(xoffset, new byte[]{0x00}); //Pad with extra 00 byte if odd
 				xoffset++;
			}

			if (arr) {
				for (int i = 0; i < arrIndices.length; i++) {
					xoffset += fillArrayIndexSegment(buffer, xoffset, arrIndices[i]);
				}
			}
 		}
		int size = xoffset-(offset + 1);
		buffer.putByte(offset, (byte)(size/2)); //Size in words

		return size+1;
 	}
	
	private static int fillArrayIndexSegment(EthernetIpBufferUtil buffer, int offset, long arrIdx) {
		int xoffset = offset;
		if (arrIdx > 0xFFFF) {
		//4 Byte Index
			buffer.putByte(xoffset, (byte) 0x2A);
			xoffset++;
			buffer.putByte(xoffset, (byte) 0x00);
			xoffset++;
			buffer.putUINT(xoffset, (short) arrIdx);
			xoffset++;
		} else if (arrIdx > 0xFF) {
		//2 Byte Index
			buffer.putByte(xoffset, (byte) 0x29);
			xoffset++;
			buffer.putByte(xoffset, (byte) 0x00);
			xoffset++;
			buffer.putUINT(xoffset, (short) arrIdx);
			xoffset++;
		} else {
		//1 Byte Index
			buffer.putByte(xoffset, (byte) 0x28);
			xoffset++;
			buffer.putByte(xoffset, (byte) arrIdx);
			xoffset++;
		}
		return xoffset-offset;
	} 		


	private static int fillRequestData(EthernetIpBufferUtil buffer, int offset, int serviceId, String tagName, Object value, int arraySize) throws InvalidTypeException, NotImplementedException {
		int size = 0;
		if (serviceId == CipCommandServices.CIP_READ_DATA) {
			buffer.putUINT(0 + offset, arraySize);
			size += 2;
		} else if (serviceId == CipCommandServices.CIP_WRITE_DATA) {
			buffer.putINT(0 + offset, EthernetIpDataTypeValidator.getType(value));
			size += 2;
			buffer.putUINT(2 + offset, arraySize);
			size += 2;
				if (arraySize > 1) {
					size += EthernetIpDataTypeValidator.putValues(value, buffer, 4 + offset, arraySize, 0);
				} else {
					size += EthernetIpDataTypeValidator.putValue(value, buffer, 4 + offset);
				}

		} else {
 			throw new NotImplementedException();
 		}
		return size;
 	}

	private static int fillRequestData(EthernetIpBufferUtil buffer, int offset, int serviceId, String tagName, Object value, int arraySize, int dataOffset) throws InvalidTypeException, NotImplementedException {
		int size = 0;
		if (serviceId == CipCommandServices.CIP_READ_FRAGMENT) {
			buffer.putUINT(0 + offset, arraySize);
			size += 2;
			buffer.putUDINT(2 + offset, dataOffset);
			size += 4;
		} else {
 			throw new NotImplementedException();
 		}
		return size;
 	}
	
	private static int fillRequestData(EthernetIpBufferUtil buffer, int offset, int serviceId, String tagName, Object value, int arraySize, int dataOffset, int writeCount) throws InvalidTypeException, NotImplementedException {
		int size = 0;
		if (serviceId == CipCommandServices.CIP_WRITE_FRAGMENT) {
			buffer.putINT(0 + offset, EthernetIpDataTypeValidator.getType(value));
			size += 2;
			buffer.putUINT(2 + offset, arraySize);
			size += 2;
			buffer.putDINT(4 + offset, dataOffset);
			size += 4;
			if (arraySize > 1) {
				size += EthernetIpDataTypeValidator.putValues(value, buffer, 8 + offset, writeCount, dataOffset);
			} else {
				size += EthernetIpDataTypeValidator.putValue(value, buffer, 8 + offset);
			}
		} else {
			throw new NotImplementedException();
		}
		return size;
 	}
	
	public static short getService(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getUSINT(0 + offset);
	}
		
/*	public static void setService(EthernetIpBufferUtil buffer, int offset, short value) {
		buffer.putUSINT(0 + offset, value);
	}
*/	
	public static short getRequestPathSize(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getUSINT(1 + offset);
	}
	
/*	public static void setRequestPathSize(EthernetIpBufferUtil buffer, int offset, int serviceId, short value) {
		if(isOdd(value)){
			buffer.putByte(1 + offset, (byte)((value+3)/2));
		}
		else{
			buffer.putByte(1 + offset, (byte)((value+2)/2));
		}
	}
*/	
	public static short getExtendedSymbolSegment(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getByte(2 + offset);
	}
	
/*	public static void setExtendedSymbolSegment(EthernetIpBufferUtil buffer, int offset, short value) {
		buffer.putByte(2 + offset,(byte) value);
	}
*/	
	public static short getRequestDataSize(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getByte(3 + offset);
	}
	
/*	public static void setRequestDataSize(EthernetIpBufferUtil buffer, int offset, short value) {
		buffer.putByte(3 + offset,(byte) value);
	}
*/	
	public static byte[] getRequestTagName(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getByteArray(4 + offset, getRequestDataSize(buffer, offset));
	}
	
/*	public static void setEPATH(EthernetIpBufferUtil buffer, int offset, String tagName) throws NotImplementedException {
		//Check for . in tag name. This means that we should add 
		//an extended symbol segment for each . and a size of the following tag
		if(tagName.contains(".")){
			if (true)
				throw new NotImplementedException(); //TODO: Test this!!
			String[] tagNames = tagName.split("\\.");
			short xoffset = 2;
			for(String tag : tagNames){
				buffer.putByte(xoffset + offset,(byte)0x91); //Extended symbol
				xoffset++;
				buffer.putByte(xoffset + offset,(byte)tag.length()); //Length of tag name
				xoffset++;
				buffer.putByteArray(xoffset + offset, tag.getBytes()); //Tag name
				xoffset+=tag.length();
				if(isOdd(tag.length())){
					buffer.putByteArray(xoffset + offset, new byte[] {0x00}); //Pad with extra 00 byte if odd
					xoffset++;
				}
			}
		}else{
			setExtendedSymbolSegment(buffer, offset, (short)0x91);
			setRequestDataSize(buffer, offset, (short)(tagName.length()));
			buffer.putByteArray(4 + offset, tagName.getBytes());
			if(isOdd(tagName.length())){
				buffer.putByteArray(4+tagName.length() + offset, new byte[] {0x00});
			}
		}
	}
*/	
	public static byte[] getRequestData(EthernetIpBufferUtil buffer, int offset) {
		return buffer.getByteArray(4 + offset, getRequestDataSize(buffer, offset));
	}
	
/*	public static void setRequestData(EthernetIpBufferUtil buffer, int offset, int serviceId, String tagName, Object value,int arraySize) throws InvalidTypeException, NotImplementedException {
		int tmpOffset = isOdd(tagName.length()) ? 5 : 4;
		tmpOffset += getIOILength(tagName) + offset;
		if(serviceId == CipCommandServices.CIP_READ_DATA){
			buffer.putUINT(0 + tmpOffset, arraySize);
		} else if(serviceId == CipCommandServices.CIP_WRITE_DATA){
			buffer.putINT(0 + tmpOffset, EthernetIpDataTypeValidator.getType(value));
			buffer.putUINT(2 + tmpOffset, arraySize);
			if (arraySize > 1)
				EthernetIpDataTypeValidator.putValues(value, buffer, 4 + tmpOffset, arraySize, 0);
			else
				EthernetIpDataTypeValidator.putValue(value, buffer, 4 + tmpOffset);
			
		}else{
			throw new NotImplementedException();
		}
	}
*/	
	public static void setRequestData(EthernetIpBufferUtil buffer, int offset, int serviceId, String tagName, Object value,int arraySize,int dataOffset) throws InvalidTypeException, NotImplementedException {
		int tmpOffset = isOdd(tagName.length()) ? 5 : 4;
		tmpOffset +=  getIOILength(tagName) + offset;
		if(serviceId == CipCommandServices.CIP_READ_FRAGMENT){
			buffer.putUINT(0 + tmpOffset, arraySize);
			buffer.putUDINT(2 + tmpOffset, dataOffset);			
		} else{
			throw new NotImplementedException();
		}
	}
	
/*	public static void setRequestData(EthernetIpBufferUtil buffer, int offset, int serviceId, String tagName, Object value,int arraySize,int dataOffset, int writeCount) throws InvalidTypeException, NotImplementedException {
		int tmpOffset = isOdd(tagName.length()) ? 5 : 4;
		tmpOffset +=  getIOILength(tagName) + offset;
		if (serviceId == CipCommandServices.CIP_WRITE_FRAGMENT){
			buffer.putINT(0 + tmpOffset, EthernetIpDataTypeValidator.getType(value));
			buffer.putUINT(2 + tmpOffset, arraySize);
			buffer.putDINT(4 + tmpOffset, dataOffset);
			if (arraySize > 1)
				EthernetIpDataTypeValidator.putValues(value, buffer, 8 + tmpOffset, writeCount, dataOffset);
			else
				EthernetIpDataTypeValidator.putValue(value, buffer, 8 + tmpOffset);			
		} else{
			throw new NotImplementedException();
		}
	}
*/	
	private static boolean isOdd(int tagNameLength){
		return (tagNameLength%2 != 0);
	}
	
	/***
	 * Get length of CIP request segment.
	 * 
	 * @param serviceId
	 * @param tagName
	 * @param value Value to write. Null in case of read requests.
	 * @param writeCount How many values to write. 0 in case of read requests.
	 * @return CIP message length (segment length).
	 * @throws NotImplementedException 
	 */
	public static int getSegmentLength(int serviceId, String tagName, Object value, int writeCount) throws NotImplementedException {
		int length = 0;
		if(serviceId == CipCommandServices.CIP_READ_DATA){
			if(tagName.contains(".")){
				String[] tagNames = tagName.split("\\.");
				length = 6+tagName.length()+tagNames.length-1;
				for(String tag : tagNames){
					if(isOdd(tag.length())){
						length++;
					}
				}
			}else{
				length = 6+tagName.length();
				if(isOdd(tagName.length()))
					length++;
			}
		} else if(serviceId == CipCommandServices.CIP_WRITE_DATA || serviceId == CipCommandServices.CIP_WRITE_FRAGMENT){
			if(tagName.contains(".")){
				String[] tagNames = tagName.split("\\.");
				length = 8+tagName.length()+tagNames.length-1;
				for(String tag : tagNames){
					if(isOdd(tag.length())){
						length++;
					}
				}
			}else{
				length = 8+tagName.length();
				if(isOdd(tagName.length()))
					length++;
			}
			// Add data value size			
			length += EthernetIpDataTypeValidator.sizeOf(value)*writeCount;
			if (serviceId == CipCommandServices.CIP_WRITE_FRAGMENT)
				length += 4; // For the data offset
			if (isOdd(length)) length++;
			
		}else if(serviceId == CipCommandServices.CIP_READ_FRAGMENT){
			if(tagName.contains(".")){
				String[] tagNames = tagName.split("\\.");
				length = 10+tagName.length()+tagNames.length-1;
				for(String tag : tagNames){
					if(isOdd(tag.length())){
						length++;
					}
				}
			}else{
				length = 10+tagName.length();
				if(isOdd(tagName.length()))
					length++;
			}
		}
		return length;
	}
	
	private static int getIOILength(String tagName){
		if(tagName.contains(".")){
			int length = 0;
			String[] tagNames = tagName.split("\\.");
			for(String tag : tagNames){
				length += tag.length()+2;
				if(isOdd(tag.length()))
					length++;
			}
			return length-2;
		}
		return tagName.length();
	}	
}
