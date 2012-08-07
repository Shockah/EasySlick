package pl.shockah.easyslick.anim;

import org.newdawn.slick.geom.Polygon;

public class AnimMultipleLine extends Anim {
	protected boolean myPos = false, myColor = false, myAngle = false, myScale = false;
	
	public AnimMultipleLine() {}
	public AnimMultipleLine(Polygon p, float pixelsPerTick) {
		super(p,pixelsPerTick);
		usePos(true);
	}
	public AnimMultipleLine(Anim other) {
		super(other);
	}
	public AnimMultipleLine(AnimMultipleLine other) {
		this((Anim)other);
		myPos = other.myPos;
		myColor = other.myColor;
		myAngle = other.myAngle;
		myScale = other.myScale;
	}
	
	public AnimMultipleLine usePos(boolean myPos) {
		this.myPos = myPos;
		return this;
	}
	public AnimMultipleLine useColor(boolean myColor) {
		this.myColor = myColor;
		return this;
	}
	public AnimMultipleLine useAngle(boolean myAngle) {
		this.myAngle = myAngle;
		return this;
	}
	public AnimMultipleLine useScale(boolean myScale) {
		this.myScale = myScale;
		return this;
	}
}