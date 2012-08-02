package pl.shockah.easyslick.gui;

import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Render;
import pl.shockah.easyslick.Room;
import pl.shockah.easyslick.effects.EffectScreenBlurBox;
import pl.shockah.easyslick.gui.elements.GuiElButton;
import pl.shockah.easyslick.gui.elements.GuiElCaption;
import pl.shockah.easyslick.gui.elements.GuiElPanel;
import pl.shockah.easyslick.gui.elements.GuiElRadio;
import pl.shockah.easyslick.gui.elements.GuiElRadioSet;

public class Gui3Radio extends Gui {
	protected String caption, c0, c1, c2;
	protected int defaultSelected;
	protected GuiElRadioSet radioSet;
	
	public Gui3Radio(String caption, String caption0, String caption1, String caption2) {
		this(caption,caption0,caption1,caption2,0);
	}
	public Gui3Radio(String caption, String caption0, String caption1, String caption2, int defaultSelected) {
		this.caption = caption;
		c0 = caption0;
		c1 = caption1;
		c2 = caption2;
		this.defaultSelected = defaultSelected;
	}
	
	protected void createElements() {
		final GuiElPanel panel;
		
		Render r = new EffectScreenBlurBox(true,true,1,1,5); r.create(); elements.add(r);
		(panel = new GuiElPanel(this,null,new Vector2f(Room.get().viewSize.x/2f-128,Room.get().viewSize.y/2f-64),new Vector2f(256,128))).create();
		new GuiElCaption(this,panel,new Vector2f(panel.pos.x+16,panel.pos.y+16),256-32,caption).create();
		
		radioSet = new GuiElRadioSet();
		new GuiElRadio(this,panel,new Vector2f(panel.pos.x+32,panel.pos.y+32),8,c0,radioSet).create();
		new GuiElRadio(this,panel,new Vector2f(panel.pos.x+32,panel.pos.y+48),8,c1,radioSet).create();
		new GuiElRadio(this,panel,new Vector2f(panel.pos.x+32,panel.pos.y+64),8,c2,radioSet).create();
		
		new GuiElButton(this,panel,new Vector2f(panel.pos.x+96,panel.pos.y+96),new Vector2f(64,16),"OK"){
			protected void onMouseClicked() {
				gui.destroy();
			}
		}.create();
	}
	
	public String getValue() {
		return radioSet.getValue().getCaption();
	}
}