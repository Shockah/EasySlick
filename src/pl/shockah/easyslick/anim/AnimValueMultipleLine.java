package pl.shockah.easyslick.anim;

import pl.shockah.Pair;

public class AnimValueMultipleLine extends AnimValue {
	protected final String name;
	
	public AnimValueMultipleLine(String name) {
		this.name = name;
	}
	public AnimValueMultipleLine(String name, AnimValue other) {
		this.name = name;
		for (Pair<Float,Double> pair : other.states) states.add(new Pair<Float,Double>(pair.get1(),pair.get2()));
	}
	
	public String getName() {
		return name;
	}
}