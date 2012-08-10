package pl.shockah.easyslick;

public abstract class EntityEvent {
	public final boolean tick, render;
	
	public EntityEvent() {this(true,false);}
	public EntityEvent(boolean tick, boolean render) {
		this.tick = tick;
		this.render = render;
	}
	
	protected final void run(Entity e) {
		if (eventCheck(e)) onEvent(e);
	}
	
	protected abstract void onEvent(Entity e);
	protected abstract boolean eventCheck(Entity e);
}