package pl.shockah.easyslick.gui.elements;

import static pl.shockah.easyslick.GraphicsHelper.BM_ADD;
import static pl.shockah.easyslick.GraphicsHelper.BM_SUBTRACT;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Colors;
import pl.shockah.easyslick.Fonts;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Window;
import pl.shockah.easyslick.gui.Gui;

public class GuiElCheckbox extends GuiEl {
	public boolean isSet;
	
	protected float s;
	protected String caption;
	protected boolean pressed = false, over = false;
	protected Color cBgTop = new Color(0xbbbbbb), cBgBottom = new Color(0x666666), cSetTop = new Color(0x666666), cSetBottom = new Color(0x333333), cBorder = new Color(0x444444), cText = new Color(0xffffff);
	protected float cMultOver = .2f, cMultPress = -.1f;
	
	public GuiElCheckbox(Gui gui, GuiElPanel panel, Vector2f pos, float s, String caption) {this(gui,panel,pos,s,caption,false);}
	public GuiElCheckbox(Gui gui, GuiElPanel panel, Vector2f pos, float s, String caption, boolean isSet) {
		super(gui,panel);
		this.pos = pos;
		this.s = s;
		this.caption = caption;
		this.isSet = isSet;
	}
	
	protected boolean isMouseOver() {
		return Window.mouseInRegion(pos,pos.copy().add(new Vector2f(s,s)));
	}
	protected void onMouseOver() {
		pressed = Window.mbLeft.down();
		over = true;
	}
	protected void onMouseNotOver() {
		pressed = false;
		over = false;
	}
	protected void onMouseClicked() {
		isSet = !isSet;
	}
	
	public final String getCaption() {return caption;}
	public final void setCaption(String caption) {this.caption = caption;}

	protected void onRender(GraphicsHelper gh) {
		Rectangle r = new Rectangle(xOnView(pos.x),yOnView(pos.y),s,s);
		
		gh.drawRectangleGradient(r,cBgTop.multiply(color),cBgTop.multiply(color),cBgBottom.multiply(color),cBgBottom.multiply(color));
		gh.g().setColor(cBorder.multiply(color));
		gh.g().draw(r);
		
		float mult = pressed ? cMultPress : (over ? cMultOver : 0f);
		
		if (mult != 0f) {
			gh.setBlendMode(mult > 0 ? BM_ADD : BM_SUBTRACT);
			mult = Math.abs(mult);
			gh.g().setColor(Colors.merge(Color.black,Color.white,mult).multiply(color));
			gh.g().fill(r);
			gh.setPreviousBlendMode();
		}
		
		if (isSet) {
			r = new Rectangle(xOnView(pos.x)+(s/4f),yOnView(pos.y)+(s/4f),s/2f,s/2f);
			gh.drawRectangleGradient(r,cSetTop.multiply(color),cSetTop.multiply(color),cSetBottom.multiply(color),cSetBottom.multiply(color));
			gh.g().setColor(cBorder.multiply(color));
			gh.g().draw(r);
		}
		
		gh.g().setFont(font);
		gh.g().setColor(cText.multiply(color));
		Fonts.setFontAlign(Fonts.MiddleLeft);
		Fonts.drawString(gh,caption,xOnView(pos.x)+s+4,yOnView(pos.y)+s/2f);
		Fonts.resetFontAlign();
	}
}