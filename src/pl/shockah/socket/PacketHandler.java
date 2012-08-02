package pl.shockah.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import pl.shockah.BinBuffer;

public class PacketHandler {
	private final Socket socket;
	private int packetSizeBytes = 2;
	private BinBuffer receiving = new BinBuffer();
	
	public PacketHandler(Socket socket) {
		this.socket = socket;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void send(BinBuffer binb) throws IOException {
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		BinBuffer t = new BinBuffer();
		t.writeUXBytes(binb.getSize(),packetSizeBytes);
		dos.write(t.getByteBuffer().array());
		dos.write(binb.getByteBuffer().array());
		dos.flush();
	}
	public BinBuffer receive() throws IOException {
		DataInputStream dos = new DataInputStream(socket.getInputStream());
		int b; while ((b = dos.read()) != -1) receiving.writeByte(b);
		
		if (receiving.getSize() >= packetSizeBytes) {
			int bytes = (int)receiving.readUXBytes(packetSizeBytes);
			if (receiving.getSize() >= packetSizeBytes+bytes) {
				receiving.setPos(packetSizeBytes); BinBuffer binb = receiving.copy(bytes);
				receiving.setPos(packetSizeBytes+bytes); receiving = receiving.copy();
				return binb;
			}
		}
		return null;
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
}