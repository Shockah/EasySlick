package pl.shockah.easyslick;

import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

public class KeyMouse extends Key implements MouseListener {
	private int button;
	private boolean tmpPressed = false, tmpReleased = false;
	
	public KeyMouse(int button) {
		this.button = button;
	}
	protected void register(Input input) {
		input.addMouseListener(this);
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
	
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {}
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {}
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {}
	public void mouseWheelMoved(int arg0) {}

	public void mousePressed(int arg0, int arg1, int arg2) {
		if (arg0 == button) tmpPressed = true;
	}
	public void mouseReleased(int arg0, int arg1, int arg2) {
		if (arg0 == button) tmpReleased = true;
	}
}