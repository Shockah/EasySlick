package pl.shockah.easyslick.particles;

import static pl.shockah.easyslick.GraphicsHelper.*;

import java.util.ArrayList;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.effects.Effect;
import pl.shockah.easyslick.gui.elements.GuiEl;

public class ParticleSystem extends Effect {
	protected ArrayList<Particle> particles = new ArrayList<Particle>(), toRemove = new ArrayList<Particle>();
	protected final int blendMode;
	
	public ParticleSystem() {this(GuiEl.DEPTH+15);}
	public ParticleSystem(float depth) {this(depth,BM_ADD);}
	public ParticleSystem(int blendMode) {this(GuiEl.DEPTH+15,blendMode);}
	public ParticleSystem(float depth, int blendMode) {
		this.depth = depth;
		this.blendMode = blendMode;
	}
	
	public void onTick() {
		for (Particle particle : particles) particle.onTick(this);
		particles.removeAll(toRemove);
		toRemove.clear();
	}
	public void onRender(GraphicsHelper gh) {
		gh.setBlendMode(blendMode);
		for (Particle particle : particles) particle.onRender(this,gh);
		gh.setPreviousBlendMode();
	}
	
	public void psClear() {
		particles.clear();
	}
}