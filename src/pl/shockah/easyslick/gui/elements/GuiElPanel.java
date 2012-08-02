package pl.shockah.easyslick.gui.elements;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Window;
import pl.shockah.easyslick.gui.Gui;

public class GuiElPanel extends GuiEl {
	public Vector2f size;
	protected Color cBgTop = new Color(0x333333), cBgBottom = new Color(0x111111), cBorder = new Color(0x444444);
	
	public GuiElPanel(Gui gui, GuiElPanel panel, Vector2f pos, Vector2f size) {
		super(gui,panel);
		this.pos = pos;
		this.size = size;
	}

	protected boolean isMouseOver() {
		return Window.mouseInRegion(pos,pos.copy().add(size));
	}

	protected void onRender(GraphicsHelper gh) {
		Rectangle rect = new Rectangle(xOnView(pos.x),yOnView(pos.y),size.x,size.y);
		
		gh.drawRectangleGradient(rect,cBgTop.multiply(color),cBgTop.multiply(color),cBgBottom.multiply(color),cBgBottom.multiply(color));
		gh.g().setColor(cBorder.multiply(color));
		gh.g().draw(rect);
	}
}