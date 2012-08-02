package pl.shockah.easyslick;

import org.newdawn.slick.Color;
import pl.shockah.Reflection;

public class ImageBuffer extends org.newdawn.slick.ImageBuffer {
	protected byte[] raw = null;
	protected int texW = -1;
	
	public ImageBuffer(int width, int height) {
		super(width,height);
	}
	public static ImageBuffer getFromImage(Image image) {
		int w = image.getWidth(), h = image.getHeight();
		ImageBuffer ret = new ImageBuffer(w,h);
		
		for (int x = 0; x < w; x++)
		for (int y = 0; y < h; y++) {
			Color c = image.getColor(x,y);
			ret.setRGBA(x,y,c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
		}
		return ret;
	}
	
	public Color getRGBA(int x, int y) {
		if (raw == null) {
			try {
				raw = Reflection.getPrivateValue(ImageBuffer.class,"rawData",this);
				texW = Reflection.getPrivateValue(ImageBuffer.class,"texWidth",this);
			} catch (Exception e) {App.getApp().handle(e);}
		}
		
		int ofs = (x+y*texW)*4;
		return new Color(raw[ofs],raw[ofs+1],raw[ofs+2],raw[ofs+3]);
	}
}