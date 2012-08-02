package pl.shockah.easyslick.transitions;

import pl.shockah.easyslick.Entity;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Room;

public abstract class Transition extends Entity {
	public static final float DEPTH = -1000000;
	
	public Transition() {
		super();
		checkCollision = false;
		persistent = true;
		depth = DEPTH;
	}
	
	protected final void finished() {
		destroy();
		Room.nextTransitionStep();
	}
	
	public boolean stopsGameplay() {return true;}
	protected void onTick() {}
	protected void onRender(GraphicsHelper gh) {}
}