package pl.shockah.easyslick.gui.elements;

import pl.shockah.easyslick.gui.Gui;

public abstract class GuiElTickable extends GuiEl {
	public GuiElTickable(Gui gui, GuiElPanel panel) {
		super(gui,panel);
	}
	
	protected boolean isMouseOver() {return false;}
	protected abstract void onTick(int tick);
}