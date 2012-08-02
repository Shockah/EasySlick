package pl.shockah.easyslick.particles;

import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.EHandler;
import pl.shockah.easyslick.Image;
import pl.shockah.easyslick.anim.Anim;

public abstract class ParticleType {
	public static final Image
		imFuzzy = EHandler.newImage("particleFuzzy.png");
	
	static {
		imFuzzy.center();
	}
	
	public Image image = imFuzzy;
	
	public final Anim anim;
	
	public ParticleType(Anim anim) {
		this.anim = anim;
	}
	
	public void create(ParticleSystem ps, Vector2f pos) {
		ps.particles.add(new Particle(this,pos));
	}
	
	protected abstract float startSpeed();
	protected abstract float startDirection();
	protected abstract float startAngle(float startDirection);
	protected abstract float tickSpeed(float startSpeed, float currentSpeed);
	protected abstract float tickDirection(float startSpeed, float tickSpeed, float startDirection);
	protected abstract float tickAngle(float startAngle, float tickDirection);
}