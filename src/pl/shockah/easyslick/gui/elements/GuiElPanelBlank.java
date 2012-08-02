package pl.shockah.easyslick.gui.elements;

import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.gui.Gui;

public class GuiElPanelBlank extends GuiElPanel {
	public GuiElPanelBlank(Gui gui, GuiElPanel panel, Vector2f pos, Vector2f size) {
		super(gui,panel,pos,size);
	}
	
	protected void onRender(GraphicsHelper gh) {}
}