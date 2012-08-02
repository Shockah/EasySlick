package pl.shockah.easyslick.gui;

import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Render;
import pl.shockah.easyslick.Room;
import pl.shockah.easyslick.effects.EffectScreenBlurBox;
import pl.shockah.easyslick.gui.elements.GuiElButton;
import pl.shockah.easyslick.gui.elements.GuiElCaption;
import pl.shockah.easyslick.gui.elements.GuiElPanel;
import pl.shockah.easyslick.gui.elements.GuiElSliderInt;

public class GuiSliderInt extends Gui {
	protected String caption;
	protected int maxValue, defaultValue;
	protected GuiElSliderInt slider;
	
	public GuiSliderInt(String caption, int maxValue) {
		this(caption,maxValue,0);
	}
	public GuiSliderInt(String caption, int maxValue, int defaultValue) {
		this.caption = caption;
		this.maxValue = maxValue;
		this.defaultValue = defaultValue;
	}
	
	protected void createElements() {
		final GuiElPanel panel;
		
		Render r = new EffectScreenBlurBox(true,true,1,1,5); r.create(); elements.add(r);
		(panel = new GuiElPanel(this,null,new Vector2f(Room.get().viewSize.x/2f-128,Room.get().viewSize.y/2f-64),new Vector2f(256,128))).create();
		new GuiElCaption(this,panel,new Vector2f(panel.pos.x+16,panel.pos.y+16),256-32,caption).create();
		(slider = new GuiElSliderInt(this,panel,new Vector2f(panel.pos.x+32,panel.pos.y+56),new Vector2f(192,16),new Vector2f(16,16),defaultValue,maxValue)).create();
		new GuiElButton(this,panel,new Vector2f(panel.pos.x+96,panel.pos.y+96),new Vector2f(64,16),"OK"){
			protected void onMouseClicked() {
				gui.destroy();
			}
		}.create();
	}
	
	public int getValue() {
		return slider.getValue();
	}
}