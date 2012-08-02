package pl.shockah.easyslick.gui.elements;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Colors;
import pl.shockah.easyslick.gui.Gui;

public class GuiElPanelTab extends GuiElPanelBlank {
	protected final GuiElTabs tabs;
	
	public GuiElPanelTab(Gui gui, GuiElPanel panel, GuiElTabs tabs, Vector2f pos, Vector2f size) {
		super(gui,panel,pos,size);
		this.tabs = tabs;
	}
	
	protected Color getMyMultiply() {
		if (tabs.getCurrentTab() != this) return Colors.alpha(0);
		return Color.white;
	}
}