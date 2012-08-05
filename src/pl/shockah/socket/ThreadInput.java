package pl.shockah.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import pl.shockah.BinBuffer;
import pl.shockah.easyslick.App;

public class ThreadInput extends Thread {
	private final Socket socket;
	protected int packetSizeBytes;
	private BinBuffer receiving = new BinBuffer();
	
	public List<BinBuffer> packets = Collections.synchronizedList(new LinkedList<BinBuffer>());
	
	public ThreadInput(Socket socket, int packetSizeBytes) {
		this.socket = socket;
		this.packetSizeBytes = packetSizeBytes;
	}
	
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			while (is.available() > 0) receiving.writeByte(is.read());
			
			if (receiving.getSize() >= packetSizeBytes) {
				int bytes = (int)receiving.readUXBytes(packetSizeBytes);
				if (receiving.getSize() >= packetSizeBytes+bytes) {
					receiving.setPos(packetSizeBytes); BinBuffer binb = receiving.copy(bytes);
					receiving.setPos(packetSizeBytes+bytes); receiving = receiving.copy();
					packets.add(binb);
				}
			}
		} catch (IOException e) {App.getApp().handle(e);}
	}
}