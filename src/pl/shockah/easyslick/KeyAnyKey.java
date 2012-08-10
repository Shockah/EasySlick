package pl.shockah.easyslick;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class KeyAnyKey extends Key implements KeyListener {
	protected boolean tmpPressed = false, tmpReleased = false;
	
	protected void register(Input input) {
		input.addKeyListener(this);
	}
	protected void unregister(Input input) {
		input.removeKeyListener(this);
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
		tmpPressed = true;
	}
	public void keyReleased(int arg0, char arg1) {
		tmpReleased = true;
	}
}