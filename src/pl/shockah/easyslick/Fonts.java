package pl.shockah.easyslick;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import pl.shockah.Helper;

public class Fonts {
	public static final int
		TopLeft = 0, TopCenter = 1, TopRight = 2,
		MiddleLeft = 3, MiddleCenter = 4, MiddleRight = 5,
		BottomLeft = 6, BottomCenter = 7, BottomRight = 8;
	private static int fontAlign = TopLeft;
	
	public static final UnicodeFont
		standard12 = newUnicodeFontFile("fontExpresswayFree.ttf",12,false,false),
		standard16 = newUnicodeFontFile("fontExpresswayFree.ttf",16,false,false),
		standard24 = newUnicodeFontFile("fontExpresswayFree.ttf",24,false,false),
		standard36 = newUnicodeFontFile("fontExpresswayFree.ttf",36,false,false);
	
	public static UnicodeFont newUnicodeFontFile(String fontPath, int size, boolean bold, boolean italic) {
		try {
			UnicodeFont font = new UnicodeFont(fontPath,size,bold,italic);
			return prepareUnicodeFont(font);
		} catch (Exception e) {App.getApp().handle(e);}
		return null;
	}
	public static UnicodeFont newUnicodeFontSystem(String fontName, int size, boolean bold, boolean italic) {
		int style = java.awt.Font.PLAIN;
		if (bold) style += java.awt.Font.BOLD;
		if (italic) style += java.awt.Font.ITALIC;
		
		UnicodeFont font = new UnicodeFont(new java.awt.Font(fontName,style,size),size,bold,italic);
		return prepareUnicodeFont(font);
	}
	
	@SuppressWarnings("unchecked") private static UnicodeFont prepareUnicodeFont(UnicodeFont font) {
		try {
			font.getEffects().add(new ColorEffect(java.awt.Color.white));
			font.addAsciiGlyphs();
			font.loadGlyphs();
		} catch (Exception e) {App.getApp().handle(e);}
		return font;
	}
	
	public static void drawStringShadow(GraphicsHelper gh, String text, float x, float y) {
		drawString(gh,text,x-1,y-1);
		drawString(gh,text,x+1,y-1);
		drawString(gh,text,x-1,y+1);
		drawString(gh,text,x+1,y+1);
	}
	public static void drawStringShadowed(GraphicsHelper gh, String text, float x, float y, Color shadowColor) {
		Color color = gh.g().getColor();
		gh.g().setColor(shadowColor);
		drawStringShadow(gh,text,x,y);
		gh.g().setColor(color);
		drawString(gh,text,x,y);
	}
	
	public static void resetFontAlign() {fontAlign = TopLeft;}
	public static void setFontAlign(int fontAlign) {Fonts.fontAlign = fontAlign;}
	public static int getFontAlign() {return fontAlign;}
	public static void drawString(GraphicsHelper gh, String text, float x, float y) {
		text = text.replace("\t","    ");
		String[] lines = text.split("\\n");
		int[] widths = new int[lines.length];
		float w = 0, h = 0, hh = gh.g().getFont().getHeight("y");
		for (int i = 0; i < lines.length; i++) {
			widths[i] = gh.g().getFont().getWidth(lines[i]);
			if (w < widths[i]) w = widths[i];
			h += hh;
		}
		
		float _x, _y = y;
		if (Helper.equalsOR(fontAlign,BottomLeft,BottomCenter,BottomRight)) _y -= h;
		if (Helper.equalsOR(fontAlign,MiddleLeft,MiddleCenter,MiddleRight)) _y -= h/2f;
		
		for (int i = 0; i < lines.length; i++) {
			_x = x;
			if (Helper.equalsOR(fontAlign,TopRight,MiddleRight,BottomRight)) _x -= widths[i];
			if (Helper.equalsOR(fontAlign,TopCenter,MiddleCenter,BottomCenter)) _x -= widths[i]/2f;
			
			gh.g().drawString(lines[i],Math.round(_x),Math.round(_y));
			_y += hh;
		}
	}
	
	public static String getStringWordwrap(Font font, String text, float maxWidth) {
		String ret = "";
		text = text.replace("\t","    ");
		String[] lines = text.split("\\n");
		
		for (int i = 0; i < lines.length; i++) {
			float xx = 0;
			if (i != 0) ret += "\n";
			String[] words = lines[i].split(" ",-1);
			for (int i2 = 0; i2 < words.length; i2++) {
				float ww = font.getWidth(words[i2]);
				if (xx+ww <= maxWidth) {
					ret += words[i2]+" ";
					xx += ww;
				} else {
					ret += "\n"+words[i2];
					xx = ww;
				}
			}
		}
		
		return ret;
	}
}