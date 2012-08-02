package pl.shockah.easyslick.gui.elements;

import org.newdawn.slick.Input;
import pl.shockah.easyslick.Key;
import pl.shockah.easyslick.gui.Gui;

public class GuiElEscape extends GuiElTickable {
	protected final int key;
	
	public GuiElEscape(Gui gui, GuiElPanel panel) {
		this(gui,panel,Input.KEY_ESCAPE);
	}
	public GuiElEscape(Gui gui, GuiElPanel panel, int key) {
		super(gui,panel);
		this.key = key;
	}

	protected void onTick(int tick) {
		if (Key.pressed(key)) gui.destroy();
	}
}