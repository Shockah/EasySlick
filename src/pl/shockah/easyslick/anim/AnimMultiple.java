package pl.shockah.easyslick.anim;

import java.util.ArrayList;

public class AnimMultiple implements IAnim<AnimState> {
	protected ArrayList<AnimMultipleLine> lines = new ArrayList<AnimMultipleLine>();
	protected ArrayList<AnimValueMultipleLine> valueLines = new ArrayList<AnimValueMultipleLine>();
	
	public AnimMultiple() {}
	public AnimMultiple(AnimMultiple other) {
		for (AnimMultipleLine line : other.lines) lines.add(new AnimMultipleLine(line));
	}
	
	public ArrayList<AnimMultipleLine> getLines() {return new ArrayList<AnimMultipleLine>(lines);}
	public void addLines(AnimMultipleLine... lines) {for (AnimMultipleLine line : lines) this.lines.add(line);}
	public void removeLines(AnimMultipleLine... lines) {for (AnimMultipleLine line : lines) this.lines.remove(line);};
	
	public ArrayList<AnimValueMultipleLine> getValueLines() {return new ArrayList<AnimValueMultipleLine>(valueLines);}
	public void addValueLines(AnimValueMultipleLine... lines) {for (AnimValueMultipleLine line : lines) this.valueLines.add(line);}
	public void removeValueLines(AnimValueMultipleLine... lines) {for (AnimValueMultipleLine line :lines) this.valueLines.remove(line);};
	
	public void updateStep() {updateStep(1f);}
	public void updateStep(float steps) {
		for (AnimMultipleLine line : lines) line.updateStep(steps);
		for (AnimValueMultipleLine line : valueLines) line.updateStep(steps);
	}
	public void setStep(float step) {
		for (AnimMultipleLine line : lines) line.setStep(step);
		for (AnimValueMultipleLine line : valueLines) line.setStep(step);
	}
	public void setFirstStep() {setStep(0);}
	public void setLastStep() {
		for (AnimMultipleLine line : lines) line.setLastStep();
		for (AnimValueMultipleLine line : valueLines) line.setLastStep();
	}
	
	public AnimState getCurrentState() {
		AnimState ret = new AnimState();
		for (AnimMultipleLine line : lines) {
			AnimState state = line.getCurrentState();
			if (line.myPos) ret.pos = state.pos;
			if (line.myColor) ret.color = state.color;
			if (line.myAngle) ret.angle = state.angle;
			if (line.myScale) {
				ret.scaleH = state.scaleH;
				ret.scaleV = state.scaleV;
			}
		}
		return ret;
	}
	public Double getValue(String name) {
		for (AnimValueMultipleLine line : valueLines) if (line.getName().equals(name)) return line.getCurrentState();
		return null;
	}
}