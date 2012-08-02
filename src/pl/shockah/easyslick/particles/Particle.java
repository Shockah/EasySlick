package pl.shockah.easyslick.particles;

import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Math2;
import pl.shockah.easyslick.anim.AnimState;

public class Particle {
	protected final ParticleType type;
	protected float angle, angleStart, tick = 0;
	protected Vector2f pos, vel, velStart;
	
	public Particle(ParticleType type, Vector2f pos) {
		this.type = type; this.pos = pos;
		vel = Math2.makeVector(type.startSpeed(),type.startDirection());
		velStart = vel.copy();
		angle = angleStart = type.startAngle(Math2.getAngle(velStart));
	}
	
	protected void onTick(ParticleSystem ps) {
		tick++;
		if (type.anim.getLastStep() <= tick) {
			ps.toRemove.add(this);
			return;
		}
		
		float dir, spd, spd2;
		spd2 = type.tickSpeed(spd = velStart.length(),vel.length());
		dir = type.tickDirection(velStart.length(),spd2,Math2.getAngle(velStart));
		
		angle += type.tickAngle(angleStart,dir);
		vel = Math2.makeVector(spd+spd2,Math2.getAngle(vel)+dir);
		pos.add(vel);
	}
	protected void onRender(ParticleSystem ps, GraphicsHelper gh) {
		type.anim.setStep(tick);
		AnimState state = type.anim.getCurrentState();
		state.angle += angle;
		state.drawImage(gh,type.image,pos.x,pos.y,1f/type.image.getWidth(),1f/type.image.getHeight());
	}
}