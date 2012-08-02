package pl.shockah.easyslick.gui.elements;

import static pl.shockah.easyslick.GraphicsHelper.BM_ADD;
import static pl.shockah.easyslick.GraphicsHelper.BM_SUBTRACT;
import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Colors;
import pl.shockah.easyslick.Fonts;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Window;
import pl.shockah.easyslick.gui.Gui;

public class GuiElRadio extends GuiEl {
	protected float r;
	protected String caption;
	protected final GuiElRadioSet radioSet;
	protected boolean pressed = false, over = false;
	protected Color cBgTop = new Color(0xbbbbbb), cBgBottom = new Color(0x666666), cBorder = new Color(0x444444), cText = new Color(0xffffff);
	protected float cMultOver = .2f, cMultPress = -.1f;
	
	public GuiElRadio(Gui gui, GuiElPanel panel, Vector2f pos, float r, String caption, GuiElRadioSet radioSet) {
		super(gui,panel);
		this.pos = pos;
		this.r = r;
		this.caption = caption;
		this.radioSet = radioSet;
		radioSet.addRadio(this);
	}
	
	protected boolean isMouseOver() {
		return new Circle(xOnView(pos.x)+2*r,yOnView(pos.y)+2*r,r,8).contains(Window.mouse.x,Window.mouse.y);
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
		radioSet.ret = this;
	}
	
	public final String getCaption() {return caption;}
	public final void setCaption(String caption) {this.caption = caption;}

	protected void onRender(GraphicsHelper gh) {
		gh.g().setAntiAlias(true);
		
		Circle c = new Circle(xOnView(pos.x)+r,yOnView(pos.y)+r,r,32);
		
		gh.g().fill(c,new ShapeFill(){
			public Color colorAt(Shape shape, float x, float y) {
				return Colors.merge(cBgTop,cBgBottom,(y-shape.getMinY())/(shape.getMaxY()-shape.getMinY())).multiply(color);
			}
			public Vector2f getOffsetAt(Shape shape, float x, float y) {
				return new Vector2f(0,0);
			}
		});
		gh.g().setColor(cBorder.multiply(color));
		gh.g().draw(c);
		
		float mult = pressed ? cMultPress : (over ? cMultOver : 0f);
		
		if (mult != 0f) {
			gh.setBlendMode(mult > 0 ? BM_ADD : BM_SUBTRACT);
			mult = Math.abs(mult);
			gh.g().setColor(Colors.merge(Color.black,Color.white,mult).multiply(color));
			gh.g().fill(c);
			gh.setPreviousBlendMode();
		}
		
		if (radioSet.getValue() == this) {
			c = new Circle(xOnView(pos.x)+r,yOnView(pos.y)+r,r/2f,32);
			gh.g().fill(c);
		}
		
		gh.g().setAntiAlias(false);
		
		gh.g().setFont(font);
		gh.g().setColor(cText.multiply(color));
		Fonts.setFontAlign(Fonts.MiddleLeft);
		Fonts.drawString(gh,caption,xOnView(pos.x)+2*r+2,yOnView(pos.y)+r);
		Fonts.resetFontAlign();
	}
}