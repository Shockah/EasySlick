package pl.shockah.easyslick;

public abstract class RoomImplementation extends Room {
	protected abstract void setupRoom();
	protected abstract void onCreate();
	protected abstract void onEnd();
	protected abstract void onTick();
	protected abstract void onRender(GraphicsHelper gh);
}