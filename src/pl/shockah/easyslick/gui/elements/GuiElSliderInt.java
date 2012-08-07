package pl.shockah.easyslick.gui.elements;

import static pl.shockah.easyslick.GraphicsHelper.BM_ADD;
import static pl.shockah.easyslick.GraphicsHelper.BM_SUBTRACT;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Colors;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Math2;
import pl.shockah.easyslick.Window;
import pl.shockah.easyslick.gui.Gui;

public class GuiElSliderInt extends GuiEl {
	protected Vector2f size, slider;
	protected int value, maxValue;
	protected boolean pressed = false, over = true;
	protected Color cBgTop = new Color(0xbbbbbb), cBgBottom = new Color(0x666666), cBorder = new Color(0x444444), cText = new Color(0x000000);
	protected float cMultOver = .2f, cMultPress = -.1f;
	
	public GuiElSliderInt(Gui gui, GuiElPanel panel, Vector2f pos, Vector2f size, Vector2f slider, int maxValue) {this(gui,panel,pos,size,slider,0,maxValue);}
	public GuiElSliderInt(Gui gui, GuiElPanel panel, Vector2f pos, Vector2f size, Vector2f slider, int value, int maxValue) {
		super(gui,panel);
		this.pos = pos;
		this.size = size;
		this.slider = slider;
		this.value = value;
		this.maxValue = maxValue;
	}
	
	protected boolean isMouseOver() {
		return new Rectangle(xOnView(pos.x),yOnView(pos.y),size.x,size.y).contains(Window.mouse.x,Window.mouse.y);
	}
	protected void onMouseOver() {
		if (Window.mbLeft.released()) pressed = false;
		if (Window.mbLeft.pressed()) pressed = true;
		over = true;
	}
	protected void onMouseNotOver() {
		if (Window.mbLeft.released()) pressed = false;
		over = false;
	}
	protected void onTick(int tick) {
		super.onTick(tick);
		if (pressed) {
			float posX = Window.mouse.x-(pos.x+(slider.x/2));
			value = Math2.limitI(Math.round((posX/(size.x-slider.x))*maxValue),0,maxValue);
		}
	}

	protected void onRender(GraphicsHelper gh) {
		Line l = new Line(xOnView(pos.x),yOnView(pos.y)+slider.y/2,xOnView(pos.x)+size.x,yOnView(pos.y)+slider.y/2);
		
		gh.g().setColor(pressed ? Color.gray : Color.darkGray);
		gh.g().draw(l);
		
		float posX = value/((float)maxValue)*(size.x-slider.x);
		Rectangle r = new Rectangle(xOnView(pos.x)+posX,yOnView(pos.y),slider.x,slider.y);
		
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
	}
	
	public final int getValue() {
		return value;
	}
}