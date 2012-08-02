package pl.shockah.easyslick.effects;

import java.awt.image.Kernel;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Image;

public class EffectScreenBlurGaussian extends EffectScreen {
	protected float radius;
	
	public EffectScreenBlurGaussian(boolean inGui, boolean once) {this(inGui,once,1f);}
	public EffectScreenBlurGaussian(boolean inGui, boolean once, float radius) {
		super(inGui,once);
		this.radius = radius;
	}
	
	protected void onRender(GraphicsHelper gh) {
		if (!once || !done) {
			gh.g().copyArea(image,0,0);
			int w = image.getWidth(), h = image.getHeight();
			image = Image.fromIntArray(effect(image.toIntArray(),w,h),w,h);
			done = true;
		}
		gh.g().drawImage(image,0,0);
	}
	
	protected int[] effect(int[] in, int width, int height) {
        int[] out = new int[in.length];

        Kernel kernel = makeKernel(radius);
        process(kernel,in,out,width,height,false,true);
        process(kernel,out,in,height,width,false,true);
        
        return in;
	}
	private void process(Kernel kernel, int[] in, int[] out, int width, int height, boolean alpha, boolean wrap) {
		float[] matrix = kernel.getKernelData( null );
		int cols = kernel.getWidth();
		int cols2 = cols/2;

		for (int y = 0; y < height; y++) {
			int index = y;
			int ioffset = y*width;
			for (int x = 0; x < width; x++) {
				float r = 0, g = 0, b = 0, a = 0;
				int moffset = cols2;
				for (int col = -cols2; col <= cols2; col++) {
					float f = matrix[moffset+col];

					if (f != 0) {
						int ix = x+col;
						if (ix < 0) {
							if (!wrap) ix = 0; else ix = (x+width) % width;
						} else if (ix >= width) {
							if (!wrap) ix = width-1; else ix = (x+width) % width;
						}
						int rgb = in[ioffset+ix];
						a += f * ((rgb >> 24) & 0xff);
						r += f * ((rgb >> 16) & 0xff);
						g += f * ((rgb >> 8) & 0xff);
						b += f * (rgb & 0xff);
					}
				}
				int ia = alpha ? clamp((int)(a+0.5)) : 0xff;
				int ir = clamp((int)(r+0.5));
				int ig = clamp((int)(g+0.5));
				int ib = clamp((int)(b+0.5));
				out[index] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
                index += height;
			}
		}
    }
	
	private static Kernel makeKernel(float radius) {
		int r = (int)Math.ceil(radius);
		int rows = r*2+1;
		float[] matrix = new float[rows];
		float sigma = radius/3;
		float sigma22 = 2*sigma*sigma;
		float sigmaPi2 = 2*((float)Math.PI)*sigma;
		float sqrtSigmaPi2 = (float)Math.sqrt(sigmaPi2);
		float radius2 = radius*radius;
		float total = 0;
		int index = 0;
		for (int row = -r; row <= r; row++) {
			float distance = row*row;
			if (distance > radius2) matrix[index] = 0; else matrix[index] = (float)Math.exp(-(distance)/sigma22) / sqrtSigmaPi2;
			total += matrix[index];
			index++;
		}
		for (int i = 0; i < rows; i++) matrix[i] /= total;

		return new Kernel(rows, 1, matrix);
	}
	
	private static int clamp(int c) {
		if (c < 0) return 0;
		if (c > 255) return 255;
		return c;
	}
}