package pl.shockah.easyslick.gui.elements;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Window;
import pl.shockah.easyslick.gui.Gui;

public abstract class GuiElButtonDropdownMenu extends GuiElButtonDropdown {
	protected ArrayList<Button> buttons = new ArrayList<Button>();
	
	public GuiElButtonDropdownMenu(Gui gui, GuiElPanel panel, Vector2f pos, Vector2f size, String text) {
		super(gui,panel,pos,size,text);
	}
	
	protected void onTick(int tick) {
		boolean wasDropdownPressed = dropdown.pressed;
		dropdown.onTick(tick);
		for (Button button : buttons) button.onTick(tick);
		
		if (isMouseOver()) {
			onMouseOver();
			if (Window.mbLeft.released() && !wasDropdownPressed) {
				onMouseClicked();
				toggle = false;
			}
		} else {
			onMouseNotOver();
			if (Window.mbLeft.released()) onMouseClickedOutside();
		}
	}
	
	protected abstract void onMouseClicked();
	protected void onMouseClickedDropdown() {
		toggle = !toggle;
	}

	protected void onRender(GraphicsHelper gh) {
		super.onRender(gh);
		for (Button button : buttons) button.onRender(gh);
	}
	
	public void add(String text, Runnable runnable) {
		buttons.add(new Button(this,text,runnable));
	}
	
	public class Button extends GuiElButton {
		protected final GuiElButtonDropdownMenu menu;
		protected final Runnable runnable;
		protected final int buttonId;
		
		public Button(GuiElButtonDropdownMenu menu, String text, Runnable runnable) {
			super(menu.gui,menu.panel,menu.pos.copy().add(new Vector2f(0,Math.round((buttons.size()+(menu.dropBottom ? 0 : 1))*menu.size.y/4f*3f*(menu.dropBottom ? 1f : -1f)+(menu.dropBottom ? menu.size.y : 0)))),new Vector2f(menu.size.x,Math.round(menu.size.y/4f*3f)),text);
			this.menu = menu;
			this.runnable = runnable;
			buttonId = buttons.size();
		}

		protected boolean isMouseOver() {
			if (color.a < 1) return false;
			return super.isMouseOver();
		}
		protected void onTick(int tick) {
			color.a += tick/100f*(menu.toggle ? 1f : -1f);
			color.a = Math.min(Math.max(color.a,-buttonId*.2f),1+buttonId*.2f);
			super.onTick(tick);
		}
		protected void onMouseClicked() {
			if (color.a >= 1) {
				runnable.run();
				menu.toggle = false;
			}
		}
		protected void onRender(GraphicsHelper gh) {
			Color c = new Color(color);
			color.a = Math.min(color.a,1);
			
			if (color.a > 0) super.onRender(gh);
			color = c;
		}
	}
}