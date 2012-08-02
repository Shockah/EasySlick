package pl.shockah.easyslick;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Game;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.SGL;

public final class AppGameContainer extends org.newdawn.slick.AppGameContainer {
	public AppGameContainer(Game game) throws SlickException {
		super(game);
	}
	public AppGameContainer(Game game, int width, int height, boolean fullscreen) throws SlickException {
		super(game,width,height,fullscreen);
	}
	
	public void gameLoop() throws SlickException {
		super.gameLoop();
		if (!running) {
			App.getApp().deinit(this);
			destroy();
			System.exit(0);
		}
	}
	
	public boolean getRunning() {return running;}
	public void stop() {running = false;}
	
	protected void updateAndRender(int delta) throws SlickException {
		if (smoothDeltas) {
			if (getFPS() != 0) {
				delta = 1000 / getFPS();
			}
		}
		
		input.poll(width, height);
	
		Music.poll(delta);
		if (!paused) {
			storedDelta += delta;
			
			if (storedDelta >= minimumLogicInterval) {
				try {
					if (maximumLogicInterval != 0) {
						long cycles = storedDelta / maximumLogicInterval;
						for (int i=0;i<cycles;i++) {
							game.update(this, (int) maximumLogicInterval);
						}
						
						int remainder = (int) (storedDelta % maximumLogicInterval);
						if (remainder > minimumLogicInterval) {
							game.update(this, (int) (remainder % maximumLogicInterval));
							storedDelta = 0;
						} else {
							storedDelta = remainder;
						}
					} else {
						game.update(this, (int) storedDelta);
						storedDelta = 0;
					}
				} catch (Throwable e) {App.getApp().handle(e);}
			}
		} else {
			game.update(this, 0);
		}
		
		if (hasFocus() || getAlwaysRender()) {
			if (clearEachFrame) {
				GL.glClear(SGL.GL_COLOR_BUFFER_BIT | SGL.GL_DEPTH_BUFFER_BIT);
			} 
			
			GL.glLoadIdentity();
			
			getGraphics().resetTransform();
			getGraphics().resetFont();
			getGraphics().resetLineWidth();
			getGraphics().setAntiAlias(false);
			try {
				game.render(this, getGraphics());
			} catch (Throwable e) {
				App.getApp().handle(e);
			}
			getGraphics().resetTransform();
			
			if (isShowingFPS()) {
				getDefaultFont().drawString(10, 10, "FPS: "+recordedFPS);
			}
			
			GL.flush();
		}
		
		if (targetFPS != -1) {
			Display.sync(targetFPS);
		}
	}
}