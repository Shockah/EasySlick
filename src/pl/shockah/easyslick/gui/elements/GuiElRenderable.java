package pl.shockah.easyslick.gui.elements;

import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.gui.Gui;

public abstract class GuiElRenderable extends GuiEl {
	public GuiElRenderable(Gui gui, GuiElPanel panel) {
		super(gui,panel);
	}
	
	protected boolean isMouseOver() {return false;}
	protected abstract void onRender(GraphicsHelper gh);
}