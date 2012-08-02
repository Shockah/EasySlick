package pl.shockah.easyslick.effects;

import org.newdawn.slick.Color;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Image;
import pl.shockah.easyslick.ImageBuffer;

public class EffectScreenInvert extends EffectScreen {
	public EffectScreenInvert(boolean inGui, boolean once) {
		super(inGui,once);
	}
	
	protected void onRender(GraphicsHelper gh) {
		if (!once || !done) {
			gh.g().copyArea(image,0,0);
			image = new Image(effect(ImageBuffer.getFromImage(image)));
			done = true;
		}
		gh.g().drawImage(image,0,0);
	}
	
	protected ImageBuffer effect(ImageBuffer buf) {
		int w = buf.getWidth(), h = buf.getHeight();
		
		for (int x = 0; x < w; x++)
		for (int y = 0; y < h; y++) {
            Color c = buf.getRGBA(x,y);
            buf.setRGBA(x,y,255-c.getRed(),255-c.getGreen(),255-c.getBlue(),c.getAlpha());
        }
        
        return buf;
	}
}