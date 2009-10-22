package se.opendataexchange.ethernetip4j.test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;
import se.opendataexchange.ethernetip4j.junit.TestUtils;


public class Sandbox {

	public Sandbox() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws NotImplementedException 
	 */
	public static void main(String[] args) throws NotImplementedException {
		new Sandbox().play();
	}

	private void play() throws NotImplementedException {
		//int k = 0xabcdef12;
		int k = 0xabcd;
		// lefty 
		k >>=8; // Shift bits right. 0xabcd --> 0xab
		TestUtils.printBytes(k);
		l();
		k = 0xabcd;
		//righty
		k <<= 8;
		TestUtils.printBytes(k); // Shift bits left. 0xabcd --> 0xabcd00 INCREASES by a factor 2^n
		l();
		
		k = 0xabcd;
		//righty
		k <<= 8;
		TestUtils.printBytes(k);
		l();
		//lefty
		k >>= 8;
		TestUtils.printBytes(k);
		l();
		//lefty
		k >>= 8;
		TestUtils.printBytes(k);
		l();
		k = 0xabcdef12;
		TestUtils.printBytes(k);
		l();
		k >>= 8;
		TestUtils.printBytes(k);
		l();
		ByteBuffer bb = ByteBuffer.allocate(4);
		l(bb.order());
		l(ByteOrder.nativeOrder());
	}

	/**
	 * @param s
	 */
	private void l(Object s){
		System.out.println(s);
	}
	private void l(){
		System.out.println();
	}
	
}
