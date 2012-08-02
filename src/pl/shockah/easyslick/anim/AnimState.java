package pl.shockah.easyslick.anim;

import org.newdawn.slick.Color;
import pl.shockah.easyslick.Colors;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Image;

public class AnimState {
	public Color color;
	public float angle;
	public float scaleH,scaleV;
	
	public AnimState() {this(Color.white,0f,1f,1f);}
	public AnimState(Color color) {this(color,0f,1f,1f);}
	public AnimState(float angle) {this(Color.white,angle,1f,1f);}
	public AnimState(float scaleH, float scaleV) {this(Color.white,0f,scaleH,scaleV);}
	public AnimState(Color color, float scaleH, float scaleV) {this(color,0f,scaleH,scaleV);}
	public AnimState(AnimState state) {this(state.color,state.angle,state.scaleH,state.scaleV);}
	public AnimState(AnimState state1, AnimState state2, float f) {
		this(
			Colors.merge(state1.color,state2.color,f),
			state1.angle+((state2.angle-state1.angle)*f),
			state1.scaleH+((state2.scaleH-state1.scaleH)*f),
			state1.scaleV+((state2.scaleV-state1.scaleV)*f)
		);
	}
	public AnimState(Color color, float angle, float scaleH, float scaleV) {this(color,angle,scaleH,scaleV,true);}
	public AnimState(Color color, float angle, float scaleH, float scaleV, boolean visible) {
		this.color = color;
		this.angle = angle;
		this.scaleH = scaleH;
		this.scaleV = scaleV;
	}
	
	public String toString() {
		return "[AnimState: "+color+","+angle+","+scaleH+"x"+scaleV+"]";
	}
	
	public void drawImage(GraphicsHelper gh, Image image, float x, float y) {drawImage(gh,image,x,y,1f,1f);}
	public void drawImage(GraphicsHelper gh, Image image, float x, float y, float baseScaleH, float baseScaleV) {
		float old = image.getRotation();
		image.setRotation(old+angle);
		gh.g().drawImage(image,x,y,x+(image.getWidth()*scaleH*baseScaleH),y+(image.getHeight()*scaleV*baseScaleV),0,0,image.getWidth(),image.getHeight(),color);
		image.setRotation(old);
	}
}