package pl.shockah.easyslick.gui.elements;

import java.util.ArrayList;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Window;
import pl.shockah.easyslick.gui.Gui;

public class GuiElTabs extends GuiEl {
	public Vector2f size;
	protected ArrayList<GuiElPanelTab> tabs = new ArrayList<GuiElPanelTab>();
	protected int current = 0;
	
	public GuiElTabs(Gui gui, GuiElPanel panel, Vector2f pos, Vector2f size) {
		super(gui,panel);
		this.pos = pos;
		this.size = size;
	}

	protected boolean isMouseOver() {
		return Window.mouseInRegion(pos,pos.copy().add(size));
	}
	
	public int getCurrentID() {return current;}
	public GuiElPanelTab getCurrentTab() {return tabs.get(current);}
}