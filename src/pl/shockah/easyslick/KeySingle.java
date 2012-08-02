package pl.shockah.easyslick;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class KeySingle extends Key implements KeyListener {
	private int key;
	private boolean tmpPressed = false, tmpReleased = false;
	
	public KeySingle(int key) {
		this.key = key;
	}
	protected void register(Input input) {
		input.addKeyListener(this);
	}
	
	protected void updateStates(Input input) {
		if (tmpPressed) down = true;
		if (tmpReleased) down = false;
		pressed = tmpPressed;
		released = tmpReleased;
		
		tmpPressed = tmpReleased = false;
	}
	
	public void inputEnded() {}
	public void inputStarted() {}
	public void setInput(Input arg0) {}
	public boolean isAcceptingInput() {return true;}
	
	public void keyPressed(int arg0, char arg1) {
		if (arg0 == key) tmpPressed = true;
	}
	public void keyReleased(int arg0, char arg1) {
		if (arg0 == key) tmpReleased = true;
	}
}