package pl.shockah.easyslick.anim;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Colors;

public class AnimState {
	public Vector2f pos;
	public Color color;
	public float angle = 0f;
	public float scaleH = 1f, scaleV = 1f;
	
	public AnimState() {
		setPos(new Vector2f(0,0));
		setColor(Color.white);
	}
	public AnimState(AnimState state) {
		setPos(state.pos);
		setColor(state.color);
		setAngle(state.angle);
		setScale(state.scaleH,state.scaleV);
	}
	public AnimState(AnimState state1, AnimState state2, float f) {
		setPos(new Vector2f(state1.pos.x+((state2.pos.x-state1.pos.x)*f),state1.pos.y+((state2.pos.y-state1.pos.y)*f)));
		setColor(Colors.merge(state1.color,state2.color,f));
		setAngle(state1.angle+((state2.angle-state1.angle)*f));
		setScale(state1.scaleH+((state2.scaleH-state1.scaleH)*f),state1.scaleV+((state2.scaleV-state1.scaleV)*f));
	}
	
	public String toString() {
		return "[AnimState: "+pos+","+color+","+angle+","+scaleH+"x"+scaleV+"]";
	}
	
	public AnimState setPos(Vector2f pos) {
		this.pos = pos;
		return this;
	}
	public AnimState setColor(Color color) {
		this.color = color;
		return this;
	}
	public AnimState setAngle(float angle) {
		this.angle = angle;
		return this;
	}
	public AnimState setScale(float scaleH, float scaleV) {
		this.scaleH = scaleH;
		this.scaleV = scaleV;
		return this;
	}
}