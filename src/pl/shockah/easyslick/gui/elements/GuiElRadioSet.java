package pl.shockah.easyslick.gui.elements;

public class GuiElRadioSet {
	protected GuiElRadio ret = null;
	protected int wait;
	
	public GuiElRadioSet() {
		this(0);
	}
	public GuiElRadioSet(int wait) {
		this.wait = wait;
	}
	
	protected void addRadio(GuiElRadio radio) {
		if (wait == 0 && ret == null) ret = radio;
		if (--wait == 0) ret = radio;
	}
	
	public final GuiElRadio getValue() {
		return ret;
	}
}