package pl.shockah.easyslick.gui.elements;

import static pl.shockah.easyslick.GraphicsHelper.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Colors;
import pl.shockah.easyslick.Fonts;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Math2;
import pl.shockah.easyslick.Window;
import pl.shockah.easyslick.gui.Gui;

public abstract class GuiElButtonDropdown extends GuiElButton {
	protected final GuiElButton dropdown;
	protected boolean toggle = false;
	protected boolean dropBottom = true;
	
	public GuiElButtonDropdown(Gui gui, GuiElPanel panel, Vector2f pos, Vector2f size, String text) {
		super(gui,panel,pos,size,text);
		
		final GuiElButtonDropdown me = this;
		dropdown = new GuiElButton(gui,panel,new Vector2f(pos.x+size.x-size.y,pos.y),new Vector2f(size.y,size.y),null){
			protected GuiElButtonDropdown parent = me;
			
			protected void onMouseClicked() {
				parent.onMouseClickedDropdown();
			}
			
			protected void onRender(GraphicsHelper gh) {
				Rectangle rect = new Rectangle(xOnView(pos.x),yOnView(pos.y),size.x,size.y);
				
				gh.drawRectangleGradient(rect,cBgTop.multiply(color),cBgTop.multiply(color),cBgBottom.multiply(color),cBgBottom.multiply(color));
				gh.g().setColor(cBorder.multiply(color));
				gh.g().draw(rect);
				
				float mult = pressed || parent.toggle ? cMultPress : (over ? cMultOver : 0f);
				
				if (mult != 0f) {
					gh.setBlendMode(mult > 0 ? BM_ADD : BM_SUBTRACT);
					mult = Math.abs(mult);
					gh.g().setColor(Colors.merge(Color.black,Color.white,mult).multiply(color));
					gh.g().fill(rect);
					gh.setPreviousBlendMode();
				}
				
				Vector2f posCenter = new Vector2f(pos.x+size.x/2f,pos.y+size.y/2f);
				Vector2f
					pos1 = posCenter.copy().add(Math2.makeVector(size.x/4f,270f-(parent.dropBottom ? 0 : 180))),
					pos2 = posCenter.copy().add(Math2.makeVector(size.x/4f,270f-(parent.dropBottom ? 0 : 180)-360f/3f)),
					pos3 = posCenter.copy().add(Math2.makeVector(size.x/4f,270f-(parent.dropBottom ? 0 : 180)-360f/3f*2f));
				Polygon p = new Polygon(); p.setClosed(true);
				p.addPoint(pos1.x,pos1.y); p.addPoint(pos2.x,pos2.y); p.addPoint(pos3.x,pos3.y);
				
				gh.g().setAntiAlias(true);
				gh.g().fill(p,new ShapeFill(){
					public Color colorAt(Shape shape, float x, float y) {
						return Colors.merge(cBgTop,cBgBottom,(y-shape.getMinY())/(shape.getMaxY()-shape.getMinY())).multiply(color);
					}
					public Vector2f getOffsetAt(Shape shape, float x, float y) {
						return new Vector2f(0,0);
					}
				});
				gh.g().setColor(cBorder.multiply(color));
				gh.g().draw(p);
				gh.g().setAntiAlias(false);
			}
		};
	}
	
	protected void onTick(int tick) {
		boolean wasDropdownPressed = dropdown.pressed;
		dropdown.onTick(tick);
		
		if (isMouseOver()) {
			onMouseOver();
			if (Window.mbLeft.released() && !wasDropdownPressed) onMouseClicked();
		} else {
			onMouseNotOver();
			if (Window.mbLeft.released()) onMouseClickedOutside();
		}
	}
	protected abstract void onMouseClicked();
	protected abstract void onMouseClickedDropdown();
	protected void onMouseClickedOutside() {
		toggle = false;
	}

	protected void onRender(GraphicsHelper gh) {
		Rectangle rect = new Rectangle(xOnView(pos.x),yOnView(pos.y),size.x,size.y);
		
		gh.drawRectangleGradient(rect,cBgTop.multiply(color),cBgTop.multiply(color),cBgBottom.multiply(color),cBgBottom.multiply(color));
		gh.g().setColor(cBorder.multiply(color));
		gh.g().draw(rect);
		
		float mult = pressed && !dropdown.pressed ? cMultPress : (over && ! dropdown.over ? cMultOver : 0f);
		
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
			Fonts.drawString(gh,text,xOnView(pos.x)+(size.x-dropdown.size.x)/2f,yOnView(pos.y)+size.y/2f);
			Fonts.resetFontAlign();
		}
		
		dropdown.onRender(gh);
	}
	
	public GuiElButtonDropdown setDropToBottom(boolean dropBottom) {
		this.dropBottom = dropBottom;
		return this;
	}
	public boolean getDropToBottom() {
		return dropBottom;
	}
}