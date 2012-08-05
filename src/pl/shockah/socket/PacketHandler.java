package pl.shockah.socket;

import java.net.Socket;
import pl.shockah.BinBuffer;

public class PacketHandler {
	private final Socket socket;
	private int packetSizeBytes = 2;
	private final ThreadInput ti;
	private final ThreadOutput to;
	
	public PacketHandler(Socket socket) {
		this.socket = socket;
		(ti = new ThreadInput(socket,packetSizeBytes)).start();
		(to = new ThreadOutput(socket,packetSizeBytes)).start();
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void send(BinBuffer binb) {
		to.packets.add(binb);
	}
	public BinBuffer receive() {
		if (ti.packets.isEmpty()) return null;
		return ti.packets.remove(0);
	}
	
	public int getPacketSizeBytes() {
		return packetSizeBytes;
	}
	public void setPacketSizeBytes(int bytes) {
		packetSizeBytes = ti.packetSizeBytes = to.packetSizeBytes = bytes;
	}
	public void setPacketSizeByte() {setPacketSizeBytes(1);}
	public void setPacketSizeUShort() {setPacketSizeBytes(2);}
	public void setPacketSizeUInt() {setPacketSizeBytes(4);}
}