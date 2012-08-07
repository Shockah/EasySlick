package pl.shockah.easyslick;

public interface IAppHooks {
	public void onInit();
	public void onDeinit();
	public void onTick(int delta);
	public void preRender(GraphicsHelper gh);
	public void onRender(GraphicsHelper gh);
	public void onException(Throwable t);
}