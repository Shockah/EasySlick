package pl.shockah.easyslick.gui.elements;

import static pl.shockah.easyslick.GraphicsHelper.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Colors;
import pl.shockah.easyslick.Fonts;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Window;
import pl.shockah.easyslick.gui.Gui;

public abstract class GuiElButton extends GuiEl {
	public Vector2f size;
	protected String text;
	protected boolean pressed = false, over = false;
	protected Color cBgTop = new Color(0xbbbbbb), cBgBottom = new Color(0x666666), cBorder = new Color(0x444444), cText = new Color(0x000000);
	protected float cMultOver = .2f, cMultPress = -.1f;
	
	public GuiElButton(Gui gui, GuiElPanel panel, Vector2f pos, Vector2f size, String text) {
		super(gui,panel);
		this.pos = pos;
		this.size = size;
		this.text = text;
	}
	
	protected boolean isMouseOver() {
		return Window.mouseInRegion(pos,pos.copy().add(size));
	}
	protected void onMouseOver() {
		pressed = Window.mbLeft.down();
		over = true;
	}
	protected void onMouseNotOver() {
		pressed = false;
		over = false;
	}
	protected abstract void onMouseClicked();

	protected void onRender(GraphicsHelper gh) {
		Rectangle rect = new Rectangle(xOnView(pos.x),yOnView(pos.y),size.x,size.y);
		
		gh.drawRectangleGradient(rect,cBgTop.multiply(color),cBgTop.multiply(color),cBgBottom.multiply(color),cBgBottom.multiply(color));
		gh.g().setColor(cBorder.multiply(color));
		gh.g().draw(rect);
		
		float mult = pressed ? cMultPress : (over ? cMultOver : 0f);
		
		if (mult != 0f) {
			gh.setBlendMode(mult > 0 ? BM_ADD : BM_SUBTRACT);
			mult = Math.abs(mult);
			gh.g().setColor(Colors.merge(Color.black,Color.white,mult).multiply(color));
			gh.g().fill(rect);
			gh.setPreviousBlendMode();
		}
		
		if (!text.isEmpty()) {
			gh.g().setFont(font);
			gh.g().setColor(cText.multiply(color));
			Fonts.setFontAlign(Fonts.MiddleCenter);
			Fonts.drawString(gh,text,xOnView(pos.x)+size.x/2f,yOnView(pos.y)+size.y/2f);
			Fonts.resetFontAlign();
		}
	}
}