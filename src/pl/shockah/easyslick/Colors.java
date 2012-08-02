package pl.shockah.easyslick;

import org.newdawn.slick.Color;

public final class Colors {
	public static Color makeHSV(float H, float S, float V) {
		float i,f,p,q,t, R = 0f, G = 0f, B = 0f;
		
		if (V == 0) R = G = B = 0; else {
			i = (float)Math.floor(H/60f);
			f = H-i;
			p = V*(1-S);
			q = V*(1-(S*f));
			t = V*(1-(S*(1-f)));
			if (i == 0) {R = V; G = t; B = p;}
			else if (i == 1) {R = q; G = V; B = p;}
			else if (i == 2) {R = p; G = V; B = t;}
			else if (i == 3) {R = p; G = q; B = V;}
			else if (i == 4) {R = t; G = p; B = V;}
			else if (i == 5) {R = V; G = p; B = q;}
		}
		
		return new Color(R,G,B);
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