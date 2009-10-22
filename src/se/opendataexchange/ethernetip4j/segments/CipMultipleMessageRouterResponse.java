package se.opendataexchange.ethernetip4j.segments;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.exceptions.InvalidEncapsulationPackageException;
import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;

/***
 * 
 * <table border="1">
 * <tr><th>Byte</th><th>			Name</th><th>					Type</th><th>							Description</th></tr>
 * <tr><td>0-1</td><td>				Count</td><td> 					UINT</td><td>							{0x0A,0x02,0x20,0x02,0x24,0x01} by default</td></tr>
 * <tr><td>6-7</td><td>				Count</td><td>	 				UINT</td><td> 							Number of requests</td></tr>
 * <tr><td>8-(8+count*2)</td><td>	Request data offsets</td><td> 	UINT[]</td><td> 						Offset (from end of header) to each request</td></tr>
 * <tr><td>x-y</td><td>				Requests</td><td> 				{@link CipPacketRequest}[]</td><td> 	The requests</td></tr>
 * </table>
 * 
 */
public class CipMultipleMessageRouterResponse{

	private CipMessageRouterResponse[] cips = null;
	
	private static final int DEFAULT_OFFSET = EthernetIpEncapsulationHeader.SEGMENT_LENGTH + 
	EthernetIpCommandSpecificData.SEGMENT_LENGTH +
	EthernetIpItemStruct.SEGMENT_LENGTH + 
	CipPacketResponse.SEGMENT_LENGTH;
	
	public static CipMultipleMessageRouterResponse createPackage(EthernetIpBufferUtil incomingBuffer) throws InvalidTypeException, InvalidEncapsulationPackageException{
		return new CipMultipleMessageRouterResponse(incomingBuffer); 
	}
	
	private CipMultipleMessageRouterResponse(EthernetIpBufferUtil incomingBuffer)
			throws InvalidTypeException, InvalidEncapsulationPackageException {
		
		int count = incomingBuffer.getUINT(DEFAULT_OFFSET);
		cips = new CipMessageRouterResponse[count];
		int nextOffset = incomingBuffer.getUINT(2 + DEFAULT_OFFSET);
		int offset;
		for(int x=0;x<count;x++){
			offset = incomingBuffer.getUINT((x*2)+2 + DEFAULT_OFFSET);
			nextOffset = (x==(count-1)) ?  incomingBuffer.getBuffer().limit()-DEFAULT_OFFSET : incomingBuffer.getUINT(((x+1)*2)+DEFAULT_OFFSET + 2);
			cips[x]= CipMessageRouterResponse.createPackage(incomingBuffer, DEFAULT_OFFSET+offset, DEFAULT_OFFSET+nextOffset);
		}
	}

	public Object[] getValue() throws InvalidTypeException {
		Object[] objects = new Object[cips.length];
		for(int x=0;x<objects.length;x++){
			objects[x]=cips[x].getValue();
		}
		return objects;
	}
}
