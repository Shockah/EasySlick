package pl.shockah.easyslick.gui;

import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Render;
import pl.shockah.easyslick.Room;
import pl.shockah.easyslick.effects.EffectScreenBlurBox;
import pl.shockah.easyslick.gui.elements.GuiElButton;
import pl.shockah.easyslick.gui.elements.GuiElCaption;
import pl.shockah.easyslick.gui.elements.GuiElPanel;

public class GuiMessage extends Gui {
	protected String caption;
	
	public GuiMessage(String caption) {
		this.caption = caption;
	}
	
	protected void createElements() {
		final GuiElPanel panel;
		
		Render r = new EffectScreenBlurBox(true,true,1,1,5); r.create(); elements.add(r);
		(panel = new GuiElPanel(this,null,new Vector2f(Room.get().viewSize.x/2f-128,Room.get().viewSize.y/2f-64),new Vector2f(256,128))).create();
		new GuiElCaption(this,panel,new Vector2f(panel.pos.x+16,panel.pos.y+16),256-32,caption).create();
		new GuiElButton(this,panel,new Vector2f(panel.pos.x+96,panel.pos.y+96),new Vector2f(64,16),"OK"){
			protected void onMouseClicked() {
				gui.destroy();
			}
		}.create();
	}
}