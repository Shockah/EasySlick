package pl.shockah.easyslick;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.Reflection;

public final class Window {
	public static Vector2f mouse = new Vector2f();
	public static Key
		mbLeft = new KeyMouse(Input.MOUSE_LEFT_BUTTON),
		mbRight = new KeyMouse(Input.MOUSE_RIGHT_BUTTON),
		mbMiddle = new KeyMouse(Input.MOUSE_MIDDLE_BUTTON);
	private static boolean init = false;
	
	protected static void addKeys() {
		Key.registerAllKeys();
		Key.keysList.add(mbLeft); mbLeft.register(App.getAppGameContainer().getInput());
		Key.keysList.add(mbRight); mbRight.register(App.getAppGameContainer().getInput());
		Key.keysList.add(mbMiddle); mbMiddle.register(App.getAppGameContainer().getInput());
	}
	
	public static void setup(Room room) {
		try {
			init = false;
			AppGameContainer acontainer = App.getAppGameContainer();
			acontainer.setDisplayMode((int)room.viewSize.x,(int)room.viewSize.y,false);
			acontainer.setTargetFrameRate(room.maxFPS <= 0 ? -1 : room.maxFPS);
			acontainer.setSmoothDeltas(true);
		} catch (Exception e) {App.getApp().handle(e);}
	}
	
	protected static void updateMouse() {
		if (!init) {
			try {
				Reflection.invokePrivateMethod(Reflection.getPrivateMethod(Input.class,"init",int.class),App.getAppGameContainer().getInput(),(int)Room.get().viewSize.y);
			} catch (Exception e) {App.getApp().handle(e);}
			init = true;
		}
		mouse.set(Mouse.getX()+Room.get().viewPos.x,(Room.get().viewSize.y-Mouse.getY())+Room.get().viewPos.y-1);
	}
	public static boolean mouseInRegion(Vector2f p1, Vector2f p2) {
		return mouse.x >= p1.x && mouse.y >= p1.y && mouse.x < p2.x && mouse.y < p2.y;
	}
}