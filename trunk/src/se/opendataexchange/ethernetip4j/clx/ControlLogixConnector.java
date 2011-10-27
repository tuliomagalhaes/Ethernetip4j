package se.opendataexchange.ethernetip4j.clx;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import se.opendataexchange.ethernetip4j.EthernetIpBufferUtil;
import se.opendataexchange.ethernetip4j.segments.EthernetIpEncapsulationHeader;
import se.opendataexchange.ethernetip4j.services.EthernetIpRegisterSessionRequest;

/***
 * Access data from a Logix5000 controller by using CIP Services.
 * 
 */
public class ControlLogixConnector{
	EthernetIpBufferUtil incomingBuffer = new EthernetIpBufferUtil(1500);
	
	private ByteBuffer sending;
	private ByteBuffer receiving;
	
	protected SocketChannel socketChannel;

	private String host;

	private int port;

	protected long sessionHandle;
	
	/***
	 * Create a connection to a Logix5000 controller.
	 * 
	 * Creates a ({@link java.nio.channels.SocketChannel} and connects.
	 * 
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	public ControlLogixConnector(String host, int port) throws IOException {
		this.host = host;
		this.port = port;
		try {
			this.socketChannel = this.createSocketChannel();
			while (!socketChannel.finishConnect()) {
				try {
					Thread.sleep(10);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.sessionHandle = this.registerSession();
		} catch (IOException e) {
			this.sessionHandle = 0;
			e.printStackTrace();
			throw e;
		} 
	}
	
	public String getHost() {
		return this.host;
	}

	public void setHost(String host) throws IOException {
		this.host = host;
		if (socketChannel != null) {
			disconnect();
			try {
				this.socketChannel = this.createSocketChannel();
				this.sessionHandle = this.registerSession();
			} catch (IOException ex) {
				ex.printStackTrace();
				throw ex;
			}
		} else {
			try {
				this.socketChannel = this.createSocketChannel();
				this.sessionHandle = this.registerSession();
			} catch (IOException ex) {
				ex.printStackTrace();
				throw ex;
			}
		}
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isConnected() {
		if (this.socketChannel != null) {
			return this.socketChannel.isConnected();
		} else
			return false;
	}

	public void connect() throws IOException {
		if (this.socketChannel != null) {
			if (isConnected())
				return;
			else {
				try {
					this.socketChannel.connect(new InetSocketAddress(this.host,
							this.port));
					this.sessionHandle = this.registerSession();
				} catch (IOException ex) {
					ex.printStackTrace();
					throw ex;
				}
			}
		} else {
			try {
				this.socketChannel = this.createSocketChannel();
				this.sessionHandle = this.registerSession();
			} catch (IOException ex) {
				ex.printStackTrace();
				throw ex;
			}
		}
	}

	public void disconnect() throws IOException {
		if (this.socketChannel != null)
			try {
				this.socketChannel.close();
				this.socketChannel = null;
			} catch (IOException ex) {
				ex.printStackTrace();
				throw ex;
			}
	}

	private long registerSession() throws IOException {
		// Send register session request
		this.sendData(new EthernetIpRegisterSessionRequest().getByteBuffer());

		// Receive register session response
		this.receiveData(incomingBuffer.getBuffer());
		// Extract and return the session handle
		return EthernetIpEncapsulationHeader.getSessionHandle(incomingBuffer);
	}

	private SocketChannel createSocketChannel() throws IOException {
		SocketChannel sChannel = SocketChannel.open();
		sChannel.configureBlocking(true);
		sChannel.connect(new InetSocketAddress(this.host, this.port));
		return sChannel;
	}

	protected void sendData(ByteBuffer buffer) throws IOException {
		try {
			this.socketChannel.write(buffer);
			sending = buffer;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	protected int receiveData(ByteBuffer buffer) throws IOException {
		int dataLength=-1;
		try {
			buffer.clear();
			dataLength = 0;
			dataLength += this.socketChannel.read(buffer);
			buffer.limit(dataLength);
			buffer.limit(dataLength);
			//TestUtils.printByteBuffer(buffer, 0, 100);
			receiving = buffer;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return dataLength;
	}
	
	public ByteBuffer getLatestSent(){
		return sending;
	}
	
	public ByteBuffer getLatestIncoming(){
		return receiving;
	}

	public long getSessionHandle() {
		return sessionHandle;
	}
	
	public void executeMessage(UnconnectedMessaging message) throws IOException{
		this.sendData(message.getSendRequest());
		message.request.getByteBuffer().rewind();
		this.receiveData(message.getResponseBuffer());
	}	
}
