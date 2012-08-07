package pl.shockah.easyslick.anim;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.Colors;

public class AnimState {
	public Vector2f pos;
	public Color color;
	public float angle;
	public float scaleH,scaleV;
	
	public AnimState() {this(new Vector2f(0,0),Color.white,0f,1f,1f);}
	public AnimState(Vector2f pos) {this(pos,Color.white,0f,1f,1f);}
	public AnimState(Color color) {this(new Vector2f(0,0),color,0f,1f,1f);}
	public AnimState(float angle) {this(new Vector2f(0,0),Color.white,angle,1f,1f);}
	public AnimState(Vector2f pos, float angle) {this(pos,Color.white,angle,1f,1f);}
	public AnimState(float scaleH, float scaleV) {this(new Vector2f(0,0),Color.white,0f,scaleH,scaleV);}
	public AnimState(Vector2f pos, float scaleH, float scaleV) {this(pos,Color.white,0f,scaleH,scaleV);}
	public AnimState(Color color, float scaleH, float scaleV) {this(new Vector2f(0,0),color,0f,scaleH,scaleV);}
	public AnimState(AnimState state) {this(state.pos.copy(),new Color(state.color),state.angle,state.scaleH,state.scaleV);}
	public AnimState(AnimState state1, AnimState state2, float f) {
		this(
			new Vector2f(state1.pos.x+((state2.pos.x-state1.pos.x)*f),state1.pos.y+((state2.pos.y-state1.pos.y)*f)),
			Colors.merge(state1.color,state2.color,f),
			state1.angle+((state2.angle-state1.angle)*f),
			state1.scaleH+((state2.scaleH-state1.scaleH)*f),
			state1.scaleV+((state2.scaleV-state1.scaleV)*f)
		);
	}
	public AnimState(Vector2f pos, Color color, float angle, float scaleH, float scaleV) {
		this.pos = pos;
		this.color = color;
		this.angle = angle;
		this.scaleH = scaleH;
		this.scaleV = scaleV;
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