package pl.shockah.easyslick.gui.elements;

import pl.shockah.easyslick.gui.Gui;

public abstract class GuiElSelectable extends GuiEl {
	protected static GuiElSelectable selected = null;
	
	public GuiElSelectable(Gui gui, GuiElPanel panel) {
		super(gui,panel);
	}
	
	protected void onTick(int tick) {
		super.onTick(tick);
		if (isSelected()) onTickSelected(); else onTickNotSelected();
	}
	
	protected abstract void onTickSelected();
	protected abstract void onTickNotSelected();
	protected abstract void onSelect();
	protected abstract void onUnselect();
	
	protected void onMouseClicked() {if (!isSelected()) {if (selected != null) selected.onUnselect(); selected = this; onSelect();}}
	protected void onMouseClickedOutside() {if (isSelected()) {selected = null; onUnselect();}}
	
	protected final boolean isSelected() {return equals(selected);}
}