package pl.shockah.easyslick.gui;

import java.util.ArrayList;
import pl.shockah.easyslick.App;
import pl.shockah.easyslick.Entity;
import pl.shockah.easyslick.GameLoop;
import pl.shockah.easyslick.IGameLoop;
import pl.shockah.easyslick.Render;
import pl.shockah.easyslick.gui.elements.GuiEl;

public abstract class Gui implements IGameLoop {
	protected GameLoop loop;
	protected ArrayList<Render> elements = new ArrayList<Render>();
	
	public boolean canUpdate(Entity entity) {
		return elements.contains(entity);
	}
	public boolean canRender(Render render) {return true;}
	
	public final void create() {
		if (!shouldCreate()) return;
		createElements();
		(loop = new GameLoop(this)).run();
	}
	protected abstract void createElements();
	public void destroy() {
		if (loop != null) loop.stop();
		while (!elements.isEmpty()) {
			Render r = elements.get(0);
			if (!(r instanceof GuiEl)) elements.remove(0);
			r.destroy();
		}
		loop = null;
	}
	
	protected boolean shouldCreate() {
		return App.getAppGameContainer().getRunning();
	}
	
	public void elementAdd(GuiEl element) {
		elements.add(element);
	}
	public void elementRemove(GuiEl element) {
		elements.remove(element);
	}
}