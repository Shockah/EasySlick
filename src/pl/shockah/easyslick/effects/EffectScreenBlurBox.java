package pl.shockah.easyslick.effects;

import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Image;

public class EffectScreenBlurBox extends EffectScreen {
	protected int hRadius,vRadius,iterations;
	
	public EffectScreenBlurBox(boolean inGui, boolean once) {this(inGui,once,1,1);}
	public EffectScreenBlurBox(boolean inGui, boolean once, int hRadius, int vRadius) {this(inGui,once,hRadius,vRadius,1);}
	public EffectScreenBlurBox(boolean inGui, boolean once, int hRadius, int vRadius, int iterations) {
		super(inGui,once);
		this.hRadius = hRadius;
		this.vRadius = vRadius;
		this.iterations = iterations;
	}
	
	protected void onRender(GraphicsHelper gh) {
		if (!once || !done) {
			gh.g().copyArea(image,0,1);
			int w = image.getWidth(), h = image.getHeight();
			image = Image.fromIntArray(effect(image.toIntArray(),w,h),w,h);
			done = true;
		}
		gh.g().drawImage(image,0,0);
	}
	
	protected int[] effect(int[] in, int width, int height) {
        int[] out = new int[in.length];

        for (int i = 0; i < iterations; i++ ) {
            process(in,out,width,height,hRadius);
            process(out,in,height,width,vRadius);
        }
        
        return in;
	}
	private void process(int[] in, int[] out, int width, int height, int radius) {
        int widthMinus1 = width-1;
        int tableSize = 2*radius+1;
        int divide[] = new int[256*tableSize];

        for (int i = 0; i < 256*tableSize; i++) divide[i] = i/tableSize;

        int inIndex = 0;
        
        for ( int y = 0; y < height; y++ ) {
            int outIndex = y;
            int ta = 0, tr = 0, tg = 0, tb = 0;

            for ( int i = -radius; i <= radius; i++ ) {
                int rgb = in[inIndex + clamp(i, 0, width-1)];
                ta += (rgb >> 24) & 0xff;
                tr += (rgb >> 16) & 0xff;
                tg += (rgb >> 8) & 0xff;
                tb += rgb & 0xff;
            }

            for ( int x = 0; x < width; x++ ) {
                out[ outIndex ] = (divide[ta] << 24) | (divide[tr] << 16) | (divide[tg] << 8) | divide[tb];

                int i1 = x+radius+1;
                if (i1 > widthMinus1) i1 = widthMinus1;
                int i2 = x-radius;
                if (i2 < 0) i2 = 0;
                int rgb1 = in[inIndex+i1];
                int rgb2 = in[inIndex+i2];
                
                ta += ((rgb1 >> 24) & 0xff)-((rgb2 >> 24) & 0xff);
                tr += ((rgb1 & 0xff0000)-(rgb2 & 0xff0000)) >> 16;
                tg += ((rgb1 & 0xff00)-(rgb2 & 0xff00)) >> 8;
                tb += (rgb1 & 0xff)-(rgb2 & 0xff);
                outIndex += height;
            }
            inIndex += width;
        }
    }
	
	private static int clamp(int x, int a, int b) {
		return (x < a) ? a : (x > b) ? b : x;
	}
}