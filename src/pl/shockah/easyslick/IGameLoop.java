package pl.shockah.easyslick;

public interface IGameLoop {
	public boolean canUpdate(Entity entity);
	public boolean canRender(Render render);
}