package se.opendataexchange.ethernetip4j.segments;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;
import se.opendataexchange.ethernetip4j.exceptions.TooLongMessageException;

/***
 * 
 * <table border="1">
 * <tr><th>Byte</th><th>			Name</th><th>					Type</th><th>							Description</th></tr>
 * <tr><td>0-5</td><td>				Header</td><td> 				Byte[]</td><td>							{0x0A,0x02,0x20,0x02,0x24,0x01} by default</td></tr>
 * <tr><td>6-7</td><td>				Count</td><td>	 				UINT</td><td> 							Number of requests</td></tr>
 * <tr><td>8-(8+count*2)</td><td>	Request data offsets</td><td> 	UINT[]</td><td> 						Offset (from end of header) to each request</td></tr>
 * <tr><td>x-y</td><td>				Requests</td><td> 				{@link CipPacketRequest}[]</td><td> 	The requests</td></tr>
 * </table>
 * 
 */
public class CipMultipleMessageRouterRequest{

	private static int DEFAULT_OFFSET =
		EthernetIpEncapsulationHeader.SEGMENT_LENGTH + 
		EthernetIpCommandSpecificData.SEGMENT_LENGTH +
		EthernetIpItemStruct.SEGMENT_LENGTH +
		CipPacketRequest.SEGMENT_LENGTH +
		CipCommandSpecificDataRequest.SEGMENT_DATA_OFFSET;
	
	public static int getSegmentLength(String[] tagNames)
	{
		int length = 8 + (tagNames.length*2); // header + sizes of all messages
		for(String tagName : tagNames)
			length += CipMessageRouterRequest.getSegmentLength(0x4C, tagName);
		return length +2; //TODO: Remove after confirmation
	}
	
	public static int fillBuffer(String[] tagNames, Object[] values, EthernetIpBufferUtil buffer) throws TooLongMessageException, NotImplementedException{
		int bufferOffsetMessage = 8 + 2*tagNames.length;
		buildHeader(buffer, DEFAULT_OFFSET, tagNames.length);
		for(int x = 0; x<tagNames.length; x++)
		{			
			try {
				CipMessageRouterRequest.fillBuffer(0x4C, tagNames[x], null, 1, buffer, bufferOffsetMessage + DEFAULT_OFFSET);
			} catch (InvalidTypeException e) {
				e.printStackTrace();
			}
			buffer.putUINT(8 + x*2 + DEFAULT_OFFSET, bufferOffsetMessage - 6); // Header not included in the offset
			bufferOffsetMessage += CipMessageRouterRequest.getSegmentLength(0x4C, tagNames[x]);
		}
		return bufferOffsetMessage + 2;//TODO: Remove after confirmation
	}
	
	private static void buildHeader(EthernetIpBufferUtil buffer, int offset, int count){
		byte[] header = {0x0A,0x02,0x20,0x02,0x24,0x01};
		buffer.putByteArray(offset, header);
		buffer.putUINT(offset + 6, count);
	}
}
