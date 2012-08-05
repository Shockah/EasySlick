package pl.shockah.easyslick;

public abstract class EntityImplementation extends Entity {
	protected abstract void onCreate();
	protected abstract void onAfterCreate();
	protected abstract void onDestroy();
	protected abstract void onTick(int delta);
	protected abstract void onRender(GraphicsHelper gh);
	protected abstract void onRoomStart();
	protected abstract void onRoomEnd();
	protected abstract void onAppEnd();
}