package pl.shockah.easyslick;

import java.util.ArrayList;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.transitions.Transition;

public class Room {
	private static Room current = null, changeTo;
	protected static Transition tOut = null, tIn = null;
	protected static ETransition transition = ETransition.None;
	
	public int maxFPS = 300;
	public Vector2f size = new Vector2f(640,480), viewPos = new Vector2f(), viewSize = size.copy();
	
	public static Room get() {return current;}
	public static void set(Room room) {set(room,null,null);}
	public static void set(Room room, Transition out, Transition in) {
		tOut = out;
		tIn = in;
		changeTo = room;
		transition = ETransition.Out;
		
		if (tOut == null) nextTransitionStep(); else tOut.create();
	}
	
	public static void nextTransitionStep() {
		switch (transition) {
			case Out: setRoomPart1(); break;
			case In: setRoomPart2(); break;
			default: break;
		}
	}
	protected static void setRoomPart1() {
		if (current != null) {
			ArrayList<Entity> entities = Entity.getEntities();
			for (Entity entity : entities) entity.onRoomEnd();
			current.onEnd();
			if (changeTo == null) for (Entity entity : entities) entity.onAppEnd();
			
			Render.clear();
			Entity.clear();
		}
		
		current = changeTo;
		transition = ETransition.In;
		
		if (current != null) {
			current.setupRoom();
			Window.setup(current);
			current.onCreate();
		}
		
		if (tIn == null) nextTransitionStep(); else tIn.create();
	}
	protected static void setRoomPart2() {
		ArrayList<Entity> entities = Entity.getEntities();
		for (Entity entity : entities) entity.onRoomStart();
		
		transition = ETransition.None;
	}
	
	protected void setupRoom() {}
	protected void onCreate() {}
	protected void onEnd() {}
	protected void onTick() {}
	protected void onRender(GraphicsHelper gh) {}
	
	protected final void setSize(int w, int h) {
		setSizeW(w);
		setSizeH(h);
	}
	protected final void setSizeW(int w) {
		size.x = viewSize.x = w;
	}
	protected final void setSizeH(int h) {
		size.y = viewSize.y = h;
	}
}