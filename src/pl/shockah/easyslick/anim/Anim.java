package pl.shockah.easyslick.anim;

import java.util.ArrayList;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.Pair;

public class Anim implements IAnim<AnimState> {
	protected ArrayList<Pair<Float,AnimState>> states = new ArrayList<Pair<Float,AnimState>>();
	protected float step = 0f;
	public boolean looping = true;
	
	public Anim() {}
	public Anim(Polygon p, float pixelsPerTick) {
		float dist = 0;
		
		if (p == null) throw new RuntimeException("Polygon can't be null");
		if (pixelsPerTick <= 0) throw new RuntimeException("pixelsPerTick can't be <= 0");
		
		looping = p.closed();
		for (int i = 0; i < p.getPointCount(); i++) {
			float[] point = p.getPoint(i);
			if (i != 0) {
				float[] pointPrev = p.getPoint(i-1);
				dist += new Line(pointPrev[0],pointPrev[1],point[0],point[1]).length();
			}
			addState(dist/pixelsPerTick,new AnimState().setPos(new Vector2f(point[0],point[1])));
		}
		if (p.closed()) {
			float[] pointPrev = p.getPoint(p.getPointCount()-1);
			float[] point = p.getPoint(0);
			dist += new Line(pointPrev[0],pointPrev[1],point[0],point[1]).length();
			addState(dist/pixelsPerTick,new AnimState().setPos(new Vector2f(point[0],point[1])));
		}
	}
	public Anim(Anim other) {
		for (Pair<Float,AnimState> pair : other.states) states.add(new Pair<Float,AnimState>(pair.get1(),new AnimState(pair.get2())));
	}
	
	public void addState(AnimState state) {addState(0f,state);}
	public void addState(float step, AnimState state) {
		if (step < 0f) throw new RuntimeException("AnimState steptime can't be set below 0");
		for (int i = 0; i < states.size(); i++) {
			if (states.get(i).get1() > step) {
				states.add(i,new Pair<Float,AnimState>(step,state));
				return;
			}
		}
		states.add(new Pair<Float,AnimState>(step,state));
	}
	public void copyFirstState(float step) {
		if (states.isEmpty()) return;
		addState(step,states.get(0).get2());
	}
	public void copyLastState(float step) {
		if (states.isEmpty()) return;
		AnimState state = states.get(states.size()-1).get2();
		addState(step,state);
	}
	
	public void updateStep() {updateStep(1f);}
	public void updateStep(float steps) {
		if (getLastStep() == 0f) {
			step = 0f;
			return;
		}
		
		step += steps;
		while (step > getLastStep()) {
			if (looping) step -= getLastStep();
			else step = getLastStep();
		}
		while (step < 0f) {
			if (looping) step += getLastStep();
			else step = 0f;
		}
	}
	public void setStep(float step) {
		while (step > getLastStep()) {
			if (looping) step -= getLastStep();
			else step = getLastStep();
		}
		if (step < 0) throw new RuntimeException("steptime can't be set below 0");
		this.step = step;
	}
	public void setFirstStep() {setStep(0);}
	public void setLastStep() {setStep(getLastStep());}
	
	public float getStep() {return step;}
	public float getLastStep() {
		if (states.isEmpty()) return 0f;
		return states.get(states.size()-1).get1();
	}
	public boolean isLastStep() {return getStep() == getLastStep();}
	
	public AnimState getCurrentState() {
		if (states.size() == 1) return new AnimState(states.get(0).get2());
		for (int i = 1; i < states.size(); i++) {
			Pair<Float,AnimState> state1 = states.get(i-1), state2 = states.get(i);
			if (step >= state1.get1() && step <= state2.get1()) {
				return new AnimState(state1.get2(),state2.get2(),(step-state1.get1())/(state2.get1()-state1.get1()));
			}
		}
		return new AnimState();
	}
}