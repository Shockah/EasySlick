package pl.shockah.easyslick.anim;

import pl.shockah.Pair;

public class AnimMultipleLine extends Anim {
	protected boolean myPos = false, myColor = false, myAngle = false, myScale = false;
	
	public AnimMultipleLine() {}
	public AnimMultipleLine(Anim other) {
		for (Pair<Float,AnimState> pair : other.states) states.add(new Pair<Float,AnimState>(pair.get1(),new AnimState(pair.get2())));
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