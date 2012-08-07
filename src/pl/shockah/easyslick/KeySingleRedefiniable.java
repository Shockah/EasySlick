package pl.shockah.easyslick;

public class KeySingleRedefiniable extends KeySingle {
	protected boolean redefine = false;
	
	public KeySingleRedefiniable(int key) {
		super(key);
	}
	
	public boolean getRedefine() {
		return redefine;
	}
	public void setRedefine(boolean set) {
		redefine = set;
	}
	
	public void keyPressed(int arg0, char arg1) {
		if (redefine) {
			key = arg0;
			redefine = false;
		}
		if (arg0 == key) tmpPressed = true;
	}
}