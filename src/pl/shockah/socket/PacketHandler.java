package pl.shockah.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import pl.shockah.BinBuffer;
import pl.shockah.easyslick.App;

public class PacketHandler implements Runnable {
	private final Socket socket;
	private int packetSizeBytes = 2;
	private BinBuffer receiving = new BinBuffer();
	private List<BinBuffer> packetsRead = Collections.synchronizedList(new LinkedList<BinBuffer>()), packetsWrite = Collections.synchronizedList(new LinkedList<BinBuffer>());
	
	public PacketHandler(Socket socket) {
		this.socket = socket;
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void send(BinBuffer binb) {
		packetsWrite.add(binb);
	}
	public BinBuffer receive() {
		if (packetsRead.isEmpty()) return null;
		return packetsRead.remove(0);
	}
	
	public int getPacketSizeBytes() {
		return packetSizeBytes;
	}
	public void setPacketSizeBytes(int bytes) {
		packetSizeBytes = bytes;
	}
	public void setPacketSizeByte() {setPacketSizeBytes(1);}
	public void setPacketSizeUShort() {setPacketSizeBytes(2);}
	public void setPacketSizeUInt() {setPacketSizeBytes(4);}

	public void run() {
		while (socket.isConnected()) {
			try {
				InputStream is = socket.getInputStream();
				socket.setSoTimeout(1);
				while (true) receiving.writeByte(is.read());
			} catch (SocketTimeoutException e) {
				try {
					socket.setSoTimeout(0);
				} catch (Exception e2) {App.getApp().handle(e2);}
			} catch (IOException e) {App.getApp().handle(e);}
			
			if (receiving.getSize() >= packetSizeBytes) {
				int bytes = (int)receiving.readUXBytes(packetSizeBytes);
				if (receiving.getSize() >= packetSizeBytes+bytes) {
					receiving.setPos(packetSizeBytes); BinBuffer binb = receiving.copy(bytes);
					receiving.setPos(packetSizeBytes+bytes); receiving = receiving.copy();
					packetsRead.add(binb);
				}
			}
			
			try {
				while (!packetsWrite.isEmpty()) {
					BinBuffer binb = packetsWrite.remove(0);
					OutputStream os = socket.getOutputStream();
					
					BinBuffer t = new BinBuffer();
					t.writeUXBytes(binb.getSize(),packetSizeBytes); t.setPos(0);
					for (int i = 0; i < t.getSize(); i++) os.write(t.readByte());
					for (int i = 0; i < binb.getSize(); i++) os.write(binb.readByte());
				}
			} catch (IOException e) {App.getApp().handle(e);}
		}
	}
}