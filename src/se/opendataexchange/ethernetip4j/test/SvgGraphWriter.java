package se.opendataexchange.ethernetip4j.test;

import java.io.FileWriter;
import java.io.IOException;

public class SvgGraphWriter {
	long tStart; 
	double maxMem;
	FileWriter fw;
	double height = 1000;
	long tNext;
	public SvgGraphWriter(String file){
		try {
			fw = new FileWriter(file);
			fw.write("<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"100%\" height=\"100%\">\r\n");
			//fw.write("<g transform=\"translate(0,0)\">\r\n");
			fw.write("<polyline style=\"fill:white;stroke:blue;stroke-width:1\" points=\"");
			tStart = System.currentTimeMillis();
			tNext = tStart + 1000;
			maxMem = Runtime.getRuntime().maxMemory();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void append(){
		if (tNext < System.currentTimeMillis())
		{			
			double mem = (Runtime.getRuntime().freeMemory()/maxMem)*1000;
			double t = (tNext - tStart)/1000;
			try {
				fw.write(""+t+","+mem+" ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tNext += 100;
		}
	}
	
	public void close(){
		try {
			fw.write("\"/>\r\n</svg>\r\n");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
