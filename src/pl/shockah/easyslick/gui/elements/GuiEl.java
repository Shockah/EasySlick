package pl.shockah.easyslick.gui.elements;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import pl.shockah.easyslick.Entity;
import pl.shockah.easyslick.Fonts;
import pl.shockah.easyslick.Window;
import pl.shockah.easyslick.gui.Gui;

public abstract class GuiEl extends Entity {
	public static final float DEPTH = -100000f;
	
	protected final Gui gui;
	protected final GuiElPanel panel;
	protected Color color = new Color(0xffffff);
	protected Font font = Fonts.standard12;
	
	public GuiEl(Gui gui, GuiElPanel panel) {
		super();
		checkCollision = false;
		depth = DEPTH;
		this.gui = gui;
		this.panel = panel;
	}
	
	protected void onTick(int tick) {
		if (isMouseOver()) {
			onMouseOver();
			if (Window.mbLeft.released()) onMouseClicked();
		} else {
			onMouseNotOver();
			if (Window.mbLeft.released()) onMouseClickedOutside();
		}
	}
	
	protected abstract boolean isMouseOver();
	
	protected void onMouseOver() {}
	protected void onMouseNotOver() {}
	protected void onMouseClicked() {}
	protected void onMouseClickedOutside() {}
	
	protected void onCreate() {
		if (gui == null) return;
		gui.elementAdd(this);
	}
	protected void onDestroy() {
		if (gui == null) return;
		gui.elementRemove(this);
	}
	
	public GuiEl setFont(Font font) {
		this.font = font;
		return this;
	}
	public Font getFont() {
		return font;
	}
	
	protected Color getMyMultiply() {
		return Color.white;
	}
	public final Color getMultiply() {
		Color c = getMyMultiply();
		if (c.a > 0 && panel != null) c = c.multiply(panel.getMultiply());
		return c;
	}
}