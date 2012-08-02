package pl.shockah.easyslick.effects;

import pl.shockah.easyslick.App;
import pl.shockah.easyslick.EHandler;
import pl.shockah.easyslick.Entity;
import pl.shockah.easyslick.Image;
import pl.shockah.easyslick.Room;
import pl.shockah.easyslick.gui.elements.GuiEl;

public abstract class EffectScreen extends Entity {
	public final boolean inGui, once;
	protected boolean done = false;
	protected Image image;
	
	public EffectScreen(boolean inGui, boolean once) {
		checkCollision = false;
		this.inGui = inGui;
		this.once = once;
		depth = GuiEl.DEPTH+10;
		
		image = EHandler.newImage((int)Room.get().viewSize.x,(int)Room.get().viewSize.y);
	}
	
	public void onDestroy() {
		try {
			image.destroy();
		} catch (Exception e) {App.getApp().handle(e);}
	}
}