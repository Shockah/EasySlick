package pl.shockah.easyslick.tests;

import pl.shockah.BinBuffer;
import pl.shockah.easyslick.App;
import pl.shockah.easyslick.Entity;
import pl.shockah.easyslick.Room;

public class TestBuffer {
	public static void main(String[] args) {
		App.start(new RoomTest(),"Test - Buffer");
	}
	
	public static class RoomTest extends Room {
		protected void setupRoom() {
			maxFPS = 60;
			size.y = viewSize.y = 400;
		}
		
		protected void onCreate() {
			new EntityTest().create();
		}
	}
	
	public static class EntityTest extends Entity {
		public BinBuffer binb;
		
		public void onCreate() {
			System.gc();
			System.out.println((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1024d/1024d);
			binb = new BinBuffer();
			int n = (int)(Math.pow(1024,2)*200);
			for (int i = 0; i < n ; i++) binb.writeByte(0);
			System.gc();
			System.out.println((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1024d/1024d);
		}
	}
}