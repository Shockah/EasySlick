package pl.shockah.easyslick;

import java.lang.reflect.Method;
import java.util.LinkedList;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.SGL;
import pl.shockah.Reflection;
import pl.shockah.easyslick.anim.AnimState;
import pl.shockah.easyslick.anim.IAnim;

public class GraphicsHelper {
	public static final int
		BM_NORMAL = 1,
		BM_ALPHA_MAP = 2,
		BM_ALPHA_BLEND = 3,
		BM_COLOR_MULTIPLY = 4,
		BM_SLICK_ADD = 5,
		BM_SLICK_SCREEN = 6,
		BM_ADD = 101,
		BM_SUBTRACT = 102;
	
	protected static Graphics oldG;
	protected static Method mPredraw, mPostdraw;
	
	protected LinkedList<Rectangle> clips = new LinkedList<Rectangle>();
	protected Rectangle currentClip = null;
	
	protected final Graphics g;
	protected SGL gl = null;
	protected int oldBlendMode = BM_NORMAL, blendMode = BM_NORMAL;
	
	protected AnimState stateNew = null;
	protected Color stateColor = null;
	
	public static Graphics getGraphics() {
		try {
			return Reflection.getPrivateValue(Graphics.class,"currentGraphics",null);
		} catch (Exception e) {App.getApp().handle(e);}
		return null;
	}
	public static void setGraphics(Graphics g) {
		try {
			oldG = Reflection.getPrivateValue(Graphics.class,"currentGraphics",null);
		} catch (Exception e) {App.getApp().handle(e);}
		Graphics.setCurrent(g);
	}
	public static void resetGraphics() {
		Graphics.setCurrent(oldG);
		oldG = null;
	}
	
	static {
		try {
			mPredraw = Reflection.getPrivateMethod(Graphics.class,"predraw");
			mPostdraw = Reflection.getPrivateMethod(Graphics.class,"postdraw");
		} catch (Exception e) {App.getApp().handle(e);}
	}
	
	public GraphicsHelper(Graphics g) {
		this.g = g;
		getPrivates();
	}
	public Graphics g() {return g;}
	
	public void setClip(Rectangle rect) {
		if (currentClip != null) clips.push(currentClip);
		currentClip = rect;
		g.setClip(rect);
	}
	public void resetClip() {
		if (clips.isEmpty()) {
			if (currentClip != null) {
				g.setClip(null);
				currentClip = null;
			}
			return;
		}
		currentClip = clips.pop();
		g.setClip(currentClip);
	}
	
	protected void getPrivates() {
		if (gl != null) return;
		try {
			gl = Reflection.getPrivateValue(Graphics.class,"GL",null);
		} catch (Exception e) {App.getApp().handle(e);}
	}
	public void setBlendMode(int mode) {
		oldBlendMode = blendMode;
		blendMode = mode;
		
		GL14.glBlendEquation(GL14.GL_FUNC_ADD);
		if (mode < 100) {
			g.setDrawMode(mode);
			return;
		}
		
		try {
			Reflection.invokePrivateMethod(mPredraw,g);
		} catch (Exception e) {App.getApp().handle(e);}
		gl.glEnable(SGL.GL_BLEND);
		gl.glColorMask(true,true,true,true);
		
		if (mode == BM_ADD || mode == BM_SUBTRACT) {
			gl.glBlendFunc(SGL.GL_SRC_ALPHA,SGL.GL_ONE);
			if (mode == BM_SUBTRACT) {
				GL14.glBlendEquation(GL14.GL_FUNC_SUBTRACT);
				GL14.glBlendEquation(GL14.GL_FUNC_REVERSE_SUBTRACT);
			}
		}
		
		try {
			Reflection.invokePrivateMethod(mPostdraw,g);
		} catch (Exception e) {App.getApp().handle(e);}
	}
	public void setPreviousBlendMode() {
		setBlendMode(oldBlendMode);
	}
	
	public void drawPoint(float x, float y) {
		g.drawLine(x,y,x,y);
	}
	public void drawTriangle(Vector2f p1, Vector2f p2, Vector2f p3) {
		drawTriangle(p1,p2,p3,g.getColor(),g.getColor(),g.getColor());
	}
	public void drawTriangle(Vector2f p1, Vector2f p2, Vector2f p3, Color c1, Color c2, Color c3) {
		getPrivates();
		
		try {
			Reflection.invokePrivateMethod(mPredraw,g);
		} catch (Exception e) {App.getApp().handle(e);}
		TextureImpl.bindNone();
		Color.white.bind();
		gl.glBegin(SGL.GL_TRIANGLES);
		gl.glColor4f(c1.r,c1.g,c1.b,c1.a); gl.glVertex2f(p1.x,p1.y);
		gl.glColor4f(c2.r,c2.g,c2.b,c2.a); gl.glVertex2f(p2.x,p2.y);
		gl.glColor4f(c3.r,c3.g,c3.b,c3.a); gl.glVertex2f(p3.x,p3.y);
		gl.glEnd();
		try {
			Reflection.invokePrivateMethod(mPostdraw,g);
		} catch (Exception e) {App.getApp().handle(e);}
	}
	public void drawRectangleGradient(Rectangle rect, Color topLeft, Color topRight, Color bottomLeft, Color bottomRight) {
		drawRectangleGradient(new Vector2f(rect.getMinX(),rect.getMinY()),new Vector2f(rect.getMaxX(),rect.getMaxY()),topLeft,topRight,bottomLeft,bottomRight);
	}
	public void drawRectangleGradient(Vector2f p1, Vector2f p2, Color topLeft, Color topRight, Color bottomLeft, Color bottomRight) {
		getPrivates();
		
		try {
			Reflection.invokePrivateMethod(mPredraw,g);
		} catch (Exception e) {App.getApp().handle(e);}
		TextureImpl.bindNone();
		Color.white.bind();
		GL11.glBegin(GL11.GL_POLYGON);
		Color c;
		c = topLeft; gl.glColor4f(c.r,c.g,c.b,c.a); gl.glVertex2f(p1.x,p1.y);
		c = topRight; gl.glColor4f(c.r,c.g,c.b,c.a); gl.glVertex2f(p2.x,p1.y);
		c = bottomRight; gl.glColor4f(c.r,c.g,c.b,c.a); gl.glVertex2f(p2.x,p2.y);
		c = bottomLeft; gl.glColor4f(c.r,c.g,c.b,c.a); gl.glVertex2f(p1.x,p2.y);
		gl.glEnd();
		try {
			Reflection.invokePrivateMethod(mPostdraw,g);
		} catch (Exception e) {App.getApp().handle(e);}
	}
	
	public void drawImageScaled(Image image, float x, float y, float scaleH, float scaleV) {
		g.drawImage(image,x,y,x+(image.getWidth()*scaleH),y+(image.getHeight()*scaleV),0,0,image.getWidth(),image.getHeight());
	}
	public void drawImageScaled(Image image, float x, float y, float scaleH, float scaleV, Color color) {
		g.drawImage(image,x,y,x+(image.getWidth()*scaleH),y+(image.getHeight()*scaleV),0,0,image.getWidth(),image.getHeight(),color);
	}
	
	public void drawImage(Image image, float x, float y, IAnim anim) {
		drawImage(image,x,y,1f,1f,anim.getCurrentState());
	}
	public void drawImage(Image image, float x, float y, AnimState state) {
		drawImage(image,x,y,1f,1f,state);
	}
	public void drawImage(Image image, float x, float y, float baseScaleH, float baseScaleV, IAnim anim) {
		drawImage(image,x,y,baseScaleH,baseScaleV,anim.getCurrentState());
	}
	public void drawImage(Image image, float x, float y, float baseScaleH, float baseScaleV, AnimState state) {
		if (state == null) state = new AnimState();
		float old = image.getRotation();
		image.setRotation(old+state.angle);
		g.drawImage(image,x+state.pos.x,y+state.pos.y,x+state.pos.x+(image.getWidth()*state.scaleH*baseScaleH),y+state.pos.y+(image.getHeight()*state.scaleV*baseScaleV),0,0,image.getWidth(),image.getHeight(),state.color);
		image.setRotation(old);
	}
	public void setAnimState(AnimState state) {
		if (state != null) resetAnimState();
		
		getPrivates();
		stateColor = g.getColor();
		stateNew = new AnimState(state);
		
		g.setColor(state.color); state.color.bind();
		if (state.pos.x != 0 || state.pos.y != 0) g.translate(-state.pos.x,-state.pos.y);
		if (state.angle != 0) gl.glRotatef(-state.angle,0f,0f,1f);
		if (state.scaleH != 1 || state.scaleV != 1) g.scale(1f/state.scaleH,1f/state.scaleV);
	}
	public void resetAnimState() {
		if (stateNew == null) return;
		
		if (stateNew.scaleH != 1 || stateNew.scaleV != 1) g.scale(stateNew.scaleH,stateNew.scaleV);
		if (stateNew.angle != 0) gl.glRotatef(stateNew.angle,0f,0f,1f);
		if (stateNew.pos.x != 0 || stateNew.pos.y != 0) g.translate(stateNew.pos.x,stateNew.pos.y);
		Color.white.bind(); g.setColor(stateColor);
		
		stateNew = null;
	}
}