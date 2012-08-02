package pl.shockah.easyslick.transitions;

import org.newdawn.slick.Color;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Room;
import pl.shockah.easyslick.anim.Anim;
import pl.shockah.easyslick.anim.AnimState;

public class TransitionBlack extends Transition {
	protected final Anim anim = new Anim();
	
	public TransitionBlack(boolean out, int ticks) {
		super();
		anim.addState(new AnimState(new Color(0f,0f,0f,out ? 0f : 1f)));
		anim.addState(ticks,new AnimState(new Color(0f,0f,0f,out ? 1f : 0f)));
	}
	
	protected void onTick(int tick) {
		anim.updateStep();
		if (anim.isLastStep()) finished();
	}
	protected void onRender(GraphicsHelper gh) {
		gh.g().setColor(anim.getCurrentState().color);
		gh.g().fillRect(0,0,Room.get().size.x,Room.get().size.y);
	}
}