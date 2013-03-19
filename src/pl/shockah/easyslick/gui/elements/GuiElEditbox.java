package pl.shockah.easyslick.gui.elements;

import static pl.shockah.easyslick.GraphicsHelper.BM_ADD;
import static pl.shockah.easyslick.GraphicsHelper.BM_SUBTRACT;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.App;
import pl.shockah.easyslick.Colors;
import pl.shockah.easyslick.Fonts;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.KeyboardStringListener;
import pl.shockah.easyslick.Window;
import pl.shockah.easyslick.gui.Gui;

public class GuiElEditbox extends GuiElSelectable {
	protected static final float padding = 2;
	
	public Vector2f size;
	protected KeyboardStringListener listener;
	protected boolean pressed = false, over = false;
	protected int timer = 0;
	protected final String defaultText, placeholder;
	protected Class<?> typeInput = String.class;
	protected EDisplayType typeDisplay = EDisplayType.Standard;
	protected Color cBgTop = new Color(0x888888), cBgBottom = new Color(0x222222), cBorder = new Color(0x444444), cText = new Color(0xffffff);
	protected float cMultOver = .2f, cMultPress = -.1f;
	
	public GuiElEditbox(Gui gui, GuiElPanel panel, Vector2f pos, Vector2f size) {this(gui,panel,pos,size,"");}
	public GuiElEditbox(Gui gui, GuiElPanel panel, Vector2f pos, Vector2f size, String defaultText) {this(gui,panel,pos,size,defaultText,null);}
	public GuiElEditbox(Gui gui, GuiElPanel panel, Vector2f pos, Vector2f size, String defaultText, String placeholder) {
		super(gui,panel);
		this.pos = pos;
		this.size = size;
		this.defaultText = defaultText;
		this.placeholder = placeholder;
	}
	
	protected void onCreate() {
		super.onCreate();
		listener = new KeyboardStringListener(App.getAppGameContainer().getInput());
		listener.setText(defaultText);
	}
	protected void onDestroy() {
		super.onDestroy();
		App.getAppGameContainer().getInput().removeKeyListener(listener);
		listener.setEnabled(false);
	}
	
	protected void onTick(int tick) {
		super.onTick(tick);
		if (isSelected()) {
			timer++;
			timer %= 40;
			listener.tick();
		} else timer = 0;
	}
	
	protected void onMouseOver() {
		if (Window.mbLeft.down()) pressed = true;
		over = true;
	}
	protected void onMouseNotOver() {over = false;}
	protected void onTickSelected() {pressed = true;}
	protected void onTickNotSelected() {pressed = false;}
	protected void onSelect() {
		App.getAppGameContainer().getInput().addKeyListener(listener);
		listener.setEnabled(true);
		listener.setCursorPos(listener.getText().length());
	}
	protected void onUnselect() {
		App.getAppGameContainer().getInput().removeKeyListener(listener);
		listener.setEnabled(false);
	}

	protected boolean isMouseOver() {
		return Window.mouseInRegion(pos,pos.copy().add(size));
	}

	protected void onRender(GraphicsHelper gh) {
		Rectangle rect = new Rectangle(xOnView(pos.x),yOnView(pos.y),size.x,size.y);
		
		gh.drawRectangleGradient(rect,cBgTop.multiply(color),cBgTop.multiply(color),cBgBottom.multiply(color),cBgBottom.multiply(color));
		gh.g().setColor(cBorder.multiply(color));
		gh.g().draw(rect);
		
		float mult = pressed ? cMultPress : (over ? cMultOver : 0f);
		
		if (mult != 0f) {
			gh.setBlendMode(mult > 0 ? BM_ADD : BM_SUBTRACT);
			mult = Math.abs(mult);
			gh.g().setColor(Colors.merge(Color.black,Color.white,mult).multiply(color));
			gh.g().fill(rect);
			gh.setPreviousBlendMode();
		}
		
		gh.g().setFont(font);
		Fonts.setFontAlign(Fonts.MiddleLeft);
		
		String text = getValue();
		if (typeDisplay == EDisplayType.Password) {
			StringBuilder tmp = new StringBuilder();
			for (int i = 0; i < text.length(); i++) tmp.append("*");
			text = tmp.toString();
		}
		
		gh.setClip(new Rectangle(pos.x+padding,pos.y,size.x-padding*2,size.y));
		
		float wText = gh.g().getFont().getWidth(text), wSelect = gh.g().getFont().getWidth(text.substring(0,listener.getCursorPos()));
		float shift = wText < size.x-padding*2 ? 0 : Math.min(Math.max(wSelect-size.x/2f,0),wText-size.x+padding*2);
		
		if (text.isEmpty() && placeholder != null) {
			gh.g().setColor(Colors.alpha(cText,.5f).multiply(color));
			Fonts.drawString(gh,placeholder,xOnView(pos.x)+padding-shift,yOnView(pos.y)+size.y/2f);
		} else {
			gh.g().setColor(cText.multiply(color));
			Fonts.drawString(gh,text,xOnView(pos.x)+padding-shift,yOnView(pos.y)+size.y/2f);
		}
		if (isSelected() && timer < 20) {
			float xx = 0;
			if (!text.isEmpty()) xx = gh.g().getFont().getWidth(text.substring(0,listener.getCursorPos()))-2;
			Fonts.drawString(gh,"|",xOnView(pos.x+xx)+padding-shift,yOnView(pos.y)+size.y/2f);
		}
		gh.resetClip();
		Fonts.resetFontAlign();
	}
	
	public final String getValue() {
		if (typeInput != String.class) {
			int pos = listener.getCursorPos();
			boolean dot = false;
			for (int i = 0; i < listener.getText().length(); i++) {
				char c = listener.getText().charAt(i);
				if (i == 0 && c == '-') continue;
				if (i == 0 && c == '.' && typeInput == Float.class) {
					dot = true;
					continue;
				}
				if (i != 0 && c == '.' && typeInput == Float.class && !dot && (""+listener.getText().charAt(i-1)).matches("[0-9]")) continue;
				if ((""+c).matches("[0-9]")) continue;
				listener.setText(new StringBuilder(listener.getText()).deleteCharAt(i).toString());
				if (i-- > pos) listener.setCursorPos(--pos);
			}
			
			if (!pressed && listener.getText().isEmpty()) listener.setText("0");
		}
		return listener.getText();
	}
	public final int getIntValue() {
		if (typeInput == Integer.class) return Integer.parseInt(listener.getText());
		throw new RuntimeException("int value requested, but typeInput isn't set to Integer");
	}
	public final float getFloatValue() {
		if (typeInput == Float.class) return Float.parseFloat(listener.getText());
		throw new RuntimeException("int value requested, but typeInput isn't set to Float/Double");
	}
	public final double getDoubleValue() {
		if (typeInput == Double.class) return Double.parseDouble(listener.getText());
		throw new RuntimeException("int value requested, but typeInput isn't set to Float/Double");
	}
	
	public final GuiElEditbox setInputType(Class<?> type) {
		if (type == Double.class) type = Float.class;
		typeInput = type;
		return this;
	}
	public final GuiElEditbox setDisplayType(EDisplayType type) {
		typeDisplay = type;
		return this;
	}
	
	public static enum EDisplayType {
		Standard, Password;
	}
}