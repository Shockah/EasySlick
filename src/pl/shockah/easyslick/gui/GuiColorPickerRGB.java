package pl.shockah.easyslick.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Render;
import pl.shockah.easyslick.Room;
import pl.shockah.easyslick.effects.EffectScreenBlurBox;
import pl.shockah.easyslick.gui.elements.GuiElButton;
import pl.shockah.easyslick.gui.elements.GuiElCaption;
import pl.shockah.easyslick.gui.elements.GuiElPanel;
import pl.shockah.easyslick.gui.elements.GuiElRenderable;
import pl.shockah.easyslick.gui.elements.GuiElSliderInt;

public class GuiColorPickerRGB extends Gui {
	protected String caption;
	protected Color color;
	protected GuiElSliderInt sR, sG, sB;
	
	public GuiColorPickerRGB(String caption, Color color) {
		this.caption = caption;
		this.color = color;
	}
	
	protected void createElements() {
		final GuiElPanel panel;
		
		Render r = new EffectScreenBlurBox(true,true,1,1,5); r.create(); elements.add(r);
		(panel = new GuiElPanel(this,null,new Vector2f(Room.get().viewSize.x/2f-128,Room.get().viewSize.y/2f-64),new Vector2f(256,128))).create();
		new GuiElCaption(this,panel,new Vector2f(panel.pos.x+16,panel.pos.y+16),256-32,caption).create();
		(sR = new GuiElSliderInt(this,panel,new Vector2f(panel.pos.x+32,panel.pos.y+32),new Vector2f(192,16),new Vector2f(16,16),color.getRed(),255)).create();
		(sG = new GuiElSliderInt(this,panel,new Vector2f(panel.pos.x+32,panel.pos.y+48),new Vector2f(192,16),new Vector2f(16,16),color.getGreen(),255)).create();
		(sB = new GuiElSliderInt(this,panel,new Vector2f(panel.pos.x+32,panel.pos.y+64),new Vector2f(192,16),new Vector2f(16,16),color.getBlue(),255)).create();
		new GuiElRenderable(this,panel){
			protected void onRender(GraphicsHelper gh) {
				gh.g().setColor(((GuiColorPickerRGB)gui).getValue());
				gh.g().fill(new Rectangle(xOnView(panel.pos.x+224),yOnView(panel.pos.y+16),16,16));
			}
		}.create();
		new GuiElButton(this,panel,new Vector2f(panel.pos.x+96,panel.pos.y+96),new Vector2f(64,16),"OK"){
			protected void onMouseClicked() {
				gui.destroy();
			}
		}.create();
	}
	
	public Color getValue() {
		return new Color(sR.getValue(),sG.getValue(),sB.getValue());
	}
}