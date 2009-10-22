package se.opendataexchange.ethernetip4j.junit;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import junit.framework.Assert;


public class EthernetIpBufferUtilTest {
	
	@org.junit.Test
	public void TestUSINT()
	{
		EthernetIpBufferUtil buffer = new EthernetIpBufferUtil(23);
		for (short i=0; i<255; i++)
		{
			buffer.putUSINT(i%20, i);
			Assert.assertEquals("",i, buffer.getUSINT(i%20));
		}
		
		buffer.putUSINT(1, (short) 256);
		Assert.assertEquals("",0, buffer.getUSINT(1));
	}
	
	@org.junit.Test
	public void TestUINT()
	{
		EthernetIpBufferUtil buffer = new EthernetIpBufferUtil(51);
		for (int i=0; i<=0xffff; i++) // Unsigned int max
		{
			buffer.putUINT(i%50, i);
			Assert.assertEquals("i="+i,i, buffer.getUINT(i%50));
		}
	}
	
	@org.junit.Test
	public void TestReal()
	{
		EthernetIpBufferUtil buffer = new EthernetIpBufferUtil(4);
		float f = (float)1.0;
		buffer.putREAL(0, f);
		Assert.assertEquals("i="+0,f,buffer.getREAL(0));
		/*
		for (int i=0; i<=1000000; i++) // Unsigned int max
		{
			float val = (float)Math.random()*Float.MAX_VALUE;
			buffer.putREAL(i%50, val);
			Assert.assertEquals("i="+i,val, buffer.getREAL(i%50));
			
		}*/
	}
	
	/* 
	 * int unInt = 0xabcd1234;
		byte[] buf = new byte[4];
		buf[0] = (byte)((unInt & 0xFF000000L) >> 24);
		buf[1] = (byte)((unInt & 0x00FF0000L) >> 16);
		buf[2] = (byte)((unInt & 0x0000FF00L) >> 8);
		buf[3] = (byte)(unInt & 0x000000FFL);			// Least signinficant bit
		for (int i=0; i<4; i++)
			Log.p("0x"+HexConverter.byte2hex(buf[i]));
		
		//EthernetIpBufferUtil buffer = new EthernetIpBufferUtil(10);
		//buffer.putREAL(7, (float)5.2);
		//float val = EthernetIpBufferUtil.swapBytes(buffer.getREAL(7));
		//Assert.assertEquals("",(float)5.2, val);
		byte a = 0x01;
		byte b = (byte)(a << 0x01);
		byte c = (byte)(a >> 0x01);
		Log.p("" +HexConverter.byte2hex(a));
		Log.p("" +HexConverter.byte2hex(b));
		Log.p("" +HexConverter.byte2hex(c));
		
	 */
	/*
	Log.p("0: offs "+offset+" 0x"+HexConverter.byte2hex(buffer.get(offset)));
	Log.p("1: offs "+offset+" 0x"+HexConverter.byte2hex(buffer.get(offset+1)));
	Log.p("0: offs "+offset+" 0x"+HexConverter.byte2hex(tmpBuffer.get(0)));
	Log.p("1: offs "+offset+" 0x"+HexConverter.byte2hex(tmpBuffer.get(1)));
	Log.p("2: offs "+offset+" 0x"+HexConverter.byte2hex(tmpBuffer.get(2)));
	Log.p("3: offs "+offset+" 0x"+HexConverter.byte2hex(tmpBuffer.get(3)));
	*/
	//buffer.put(offset, (byte)(value & 0x000000FFL));
	//buffer.put(offset+1, (byte)(value & 0x0000FF00L));
	/*
	for(int x=0;x<2;x++){
		buffer.put(offset+x,(byte)(value&0xff));
		value >>=8;
	}*/
	//Log.p(""+value);
	/*
	int result = buffer.get(offset);
	result <<= 8;
	result |= (0x00ff & buffer.get(offset+1));
	*/
	//Log.p(""+result);
}
