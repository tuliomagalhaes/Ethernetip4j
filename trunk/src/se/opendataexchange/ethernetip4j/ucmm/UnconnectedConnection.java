package se.opendataexchange.ethernetip4j.ucmm;

import java.io.IOException;
import java.util.ArrayList;

import se.opendataexchange.ethernetip4j.EthernetIpPacket;
import se.opendataexchange.ethernetip4j.clx.SimpleLogixCommunicator;

public class UnconnectedConnection {
	private static SimpleLogixCommunicator CONNECTOR;
	
	public UnconnectedConnection(UnconnectedMessageManager manager, String host, int port) throws IOException{
		CONNECTOR = new SimpleLogixCommunicator(host, port);
	}
	
	public ArrayList<EthernetIpPacket> checkAndProcessData(final ArrayList<String> tagList) {
		final ArrayList<EthernetIpPacket> list = new ArrayList<EthernetIpPacket>(tagList.size());
		/*manager.executeFromThreadPool(new Runnable() {
			public void run() {
				
			}
		});*/
		try{
			for (String string : tagList) {
				Object obj = CONNECTOR.read(string,1);
				//printObject(obj);
				list.add(new EthernetIpPacket(null, obj, string));
			}					
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	/*
	private static void printObject(Object object){
		System.out.println("Value read");
		System.out.println("==========");
		System.out.println("Type: "+object.getClass().toString());
		System.out.println("Value: "+object);
	}*/
}
