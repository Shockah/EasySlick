package pl.shockah.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import pl.shockah.BinBuffer;
import pl.shockah.easyslick.App;

public class ThreadOutput extends Thread {
	private final Socket socket;
	protected int packetSizeBytes;
	
	public List<BinBuffer> packets = Collections.synchronizedList(new LinkedList<BinBuffer>());
	
	public ThreadOutput(Socket socket, int packetSizeBytes) {
		this.socket = socket;
		this.packetSizeBytes = packetSizeBytes;
	}
	
	public void run() {
		try {
			while (!packets.isEmpty()) {
				BinBuffer binb = packets.remove(0);
				OutputStream os = socket.getOutputStream();
				
				BinBuffer t = new BinBuffer();
				t.writeUXBytes(binb.getSize(),packetSizeBytes); t.setPos(0);
				for (int i = 0; i < t.getSize(); i++) os.write(t.readByte());
				for (int i = 0; i < binb.getSize(); i++) os.write(binb.readByte());
			}
		} catch (IOException e) {App.getApp().handle(e);}
	}
}