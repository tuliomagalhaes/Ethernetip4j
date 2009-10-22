package se.opendataexchange.ethernetip4j;

import java.util.Date;

public class EthernetIpPacket {

		private Date timestamp;
		private Object value;
		private String name;
		
		public EthernetIpPacket(Date timestamp, Object value, String name) {
			super();
			this.timestamp = timestamp;
			this.value = value;
			this.name = name;
		}

		public String getNamex() {
			return name;
		}
		
		public Date getTimestampx() {
			return timestamp;
		}
		
		public Object getValuex() {
			return value;
		}
		
		public void setNamez(String name) {
			this.name = name;
		}
		
		public void setTimestampz(Date timestamp) {
			this.timestamp = timestamp;
		}
		
		public void setValuez(Object value) {
			this.value = value;
		}
}
