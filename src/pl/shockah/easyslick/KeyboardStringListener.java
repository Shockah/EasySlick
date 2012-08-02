package pl.shockah.easyslick;

import org.lwjgl.Sys;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class KeyboardStringListener implements KeyListener {
	private Input input;
	private boolean acceptingInput = true;
	
	private String string = "", oldString = "";
	private int cursorPos = 0, oldCursorPos = 0, maxLen = 10000;
	private boolean enabled = false;
	
	private long repeatTimer;
	private int lastKey = -1;
	private char lastChar = '\000';
	
	public KeyboardStringListener(Input input) {
		this.input = input;
	}

	public void inputEnded() {}
	public void inputStarted() {}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isAcceptingInput() {
		return acceptingInput;
	}
	public void setAcceptingInput(boolean acceptingInput) {
		this.acceptingInput = acceptingInput;
	}
	public void setInput(Input input) {
		this.input = input;
	}
	
	public void tick() {
		if (lastKey != -1) {
			if (input.isKeyDown(lastKey)) {
				if (repeatTimer < System.currentTimeMillis()) {
					repeatTimer = (System.currentTimeMillis()+50L);
					keyPressed(lastKey,lastChar);
				}
			} else lastKey = -1;
		}
	}
	
	public void keyPressed(int key, char c) {
		if (enabled) {
			if (key != -1) {
				if (key == Input.KEY_V && (input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL))) {
					String text = Sys.getClipboard();
					if (text != null) doPaste(text);
					return;
				}
				if (key == Input.KEY_Z && (input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL))) {
					if (oldString != null) doUndo(oldCursorPos,oldString);
					return;
				}

				if (input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL)) return;
				if (input.isKeyDown(Input.KEY_LMENU) || input.isKeyDown(Input.KEY_RMENU)) return;
			}

			if (lastKey != key) {
				lastKey = key;
				repeatTimer = (System.currentTimeMillis()+400L);
			} else repeatTimer = (System.currentTimeMillis()+50L);
			lastChar = c;

			if (key == Input.KEY_LEFT) {
				if (cursorPos > 0) cursorPos--;
			} else if (key == Input.KEY_RIGHT) {
				if (cursorPos < string.length()) cursorPos++;
			} else if (key == Input.KEY_BACK) {
				if (cursorPos > 0 && string.length() > 0) {
					if (cursorPos < string.length()) {
						string = (string.substring(0,cursorPos-1)+string.substring(cursorPos));
					} else string = string.substring(0,cursorPos-1);
					cursorPos--;
				}
			} else if (key == Input.KEY_DELETE) {
				if (string.length() > cursorPos) string = (string.substring(0,cursorPos)+string.substring(cursorPos+1));
			} else if (c < '' && c > '\037' && string.length() < maxLen) {
				if (cursorPos < string.length()) {
					string = (string.substring(0, cursorPos) + c + string.substring(cursorPos));
				} else string = (string.substring(0, cursorPos) + c);
				cursorPos++;
			}
		}
	}
	public void keyReleased(int arg0, char arg1) {}
	
	public String getText() {
		return string;
	}
	public void setText(String text) {
		string = text;
		cursorPos = text.length();
	}
	public int getCursorPos() {
		return cursorPos;
	}
	public void setCursorPos(int pos) {
		cursorPos = Math.min(pos,string.length());
	}
	public void setMaxLength(int length) {
		maxLen = length;
		if (string.length() > maxLen) string = string.substring(0,maxLen);
	}

	protected void doPaste(String text) {
		recordOldPosition();
		for (int i = 0; i < text.length(); i++) keyPressed(-1,text.charAt(i));
	}

	protected void recordOldPosition() {
		oldString = string;
		oldCursorPos = cursorPos;
	}
	protected void doUndo(int oldCursorPos, String oldText) {
		if (oldText != null) {
			setText(oldText);
			setCursorPos(oldCursorPos);
		}
	}
}