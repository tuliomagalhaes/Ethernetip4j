package se.opendataexchange.ethernetip4j.ucmm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import se.opendataexchange.ethernetip4j.EthernetIpPacket;
import se.opendataexchange.ethernetip4j.ThreadPool;

public class UnconnectedMessageManager{	
	private HashMap<String, UnconnectedConnection> unitConnections;
	private ThreadPool threadPool;

	public UnconnectedMessageManager(){
		unitConnections = new HashMap<String, UnconnectedConnection>();
		//threadPool = new ThreadPool(20);
	}

	public void addConnection(String host, int port){
		try {
			UnconnectedConnection connection = new UnconnectedConnection(this, host, port);
			unitConnections.put(host, connection);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void executeFromThreadPool(Runnable r){		
		threadPool.execute(r);        
	}

	public ArrayList<EthernetIpPacket> checkForData(String host, ArrayList<String> tagList){
		synchronized (unitConnections) {
			UnconnectedConnection connection = unitConnections.get(host);
			synchronized(connection){
				return connection.checkAndProcessData(tagList);
			}
			/*Set set = unitConnections.entrySet();
			 * printObject(CONNECTOR.read("rdTag1"));
					printObject(CONNECTOR.read("tag"));
					printObject(CONNECTOR.read("tag1234"));
					printObject(CONNECTOR.read("float1"));
					printObject(CONNECTOR.read("test"));
			for (Object obj : set) {
				((UnconnectedConnection)((Entry)obj).getValue()).checkAndProcessData();
			}*/
		}
		
	}

	public HashMap<String, UnconnectedConnection> getUnitConnections() {
		return unitConnections;
	}

	public void setUnitConnections(HashMap<String, UnconnectedConnection> unitConnections) {
		this.unitConnections = unitConnections;
	}
}


