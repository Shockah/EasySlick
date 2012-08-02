package pl.shockah.easyslick.gui.elements;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Fonts;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.gui.Gui;

public class GuiElCaption extends GuiEl {
	protected float maxW;
	public String text;
	protected Color color = Color.white;
	protected int fontAlign = Fonts.TopLeft;
	
	public GuiElCaption(Gui gui, GuiElPanel panel, Vector2f pos, String text) {
		this(gui,panel,pos,-1,text);
	}
	public GuiElCaption(Gui gui, GuiElPanel panel, Vector2f pos, float maxW, String text) {
		super(gui,panel);
		this.pos = pos;
		this.maxW = maxW;
		this.text = text;
	}
	
	protected boolean isMouseOver() {
		return false;
	}

	protected void onRender(GraphicsHelper gh) {
		gh.g().setColor(color);
		gh.g().setFont(font);
		Fonts.setFontAlign(fontAlign);
		Fonts.drawString(gh,maxW == -1 ? text : Fonts.getStringWordwrap(gh.g().getFont(),text,maxW),xOnView(pos.x),yOnView(pos.y));
		Fonts.resetFontAlign();
	}
	
	public GuiEl setColor(Color color) {
		this.color = color;
		return this;
	}
	public Color getColor() {
		return color;
	}
	
	public GuiElCaption setFontAlign(int fontAlign) {
		this.fontAlign = fontAlign;
		return this;
	}
	public int getFontAlign() {
		return fontAlign;
	}
}