package pl.shockah.easyslick;

import org.newdawn.slick.Color;

public final class Colors {
	public static Color makeHSV(float H, float S, float V) {
		java.awt.Color awtc = java.awt.Color.getHSBColor(H,S,V);
		return new Color(awtc.getRed()/255f,awtc.getGreen()/255f,awtc.getBlue()/255f);
	}
	
	public static Color alpha(float alpha) {return alpha(Color.white,alpha);}
	public static Color alpha(Color color, float alpha) {
		return new Color(color.r,color.g,color.b,color.a*alpha);
	}
	
	public static Color merge(Color c1, Color c2, float value) {
		value = Math.min(Math.max(value,0),1);
		float R = c1.r-((c1.r-c2.r)*value);
		float G = c1.g-((c1.g-c2.g)*value);
		float B = c1.b-((c1.b-c2.b)*value);
		float A = c1.a-((c1.a-c2.a)*value);
		return new Color(R,G,B,A);
	}
}