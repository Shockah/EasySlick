package pl.shockah.easyslick;

import java.awt.Canvas;
import javax.swing.SwingUtilities;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Game;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

public class CanvasGameContainer extends Canvas {
	private static final long serialVersionUID = 7602813188402819600L;
	
	protected Container container;
	protected Game game;
	
	public CanvasGameContainer(Game game, boolean shared) throws SlickException {
		super();

		this.game = game;
		setIgnoreRepaint(true);
		requestFocus();
		setSize(500,500);
		
		container = new Container(game, shared);
		container.setForceExit(false);
	}
	
	public void start() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					Input.disableControllers();
					
					try {
						Display.setParent(CanvasGameContainer.this);
					} catch (LWJGLException e) {
						throw new SlickException("Failed to setParent of canvas", e);
					}
					
					container.setup();
					scheduleUpdate();
				} catch (SlickException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		});
	}
	
	private void scheduleUpdate() {
		if (!isVisible()) return;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					container.gameLoop();
				} catch (SlickException e) {
					e.printStackTrace();
				}
				container.checkDimensions();
				scheduleUpdate();
			}
		});
	}
	public void dispose() {}
	
	public AppGameContainer getGameContainer() {
		return container;
	}
	
	private class Container extends AppGameContainer {
		public Container(Game game, boolean shared) throws SlickException {
			super(game, CanvasGameContainer.this.getWidth(), CanvasGameContainer.this.getHeight(), false);

			width = CanvasGameContainer.this.getWidth();
			height = CanvasGameContainer.this.getHeight();
			
			if (shared) {
				enableSharedContext();
			}
		}
		
		protected void updateFPS() {
			super.updateFPS();
		}
		
		public boolean running() {
			return super.running() && CanvasGameContainer.this.isDisplayable();
		}
		
		public int getWidth() {
			return CanvasGameContainer.this.getWidth();
		}
		public int getHeight() {
			return CanvasGameContainer.this.getHeight();
		}
		
		public void checkDimensions() {
			if (width != CanvasGameContainer.this.getWidth() || height != CanvasGameContainer.this.getHeight()) {
				try {
					setDisplayMode(CanvasGameContainer.this.getWidth(),CanvasGameContainer.this.getHeight(),false);
				} catch (SlickException e) {Log.error(e);}
			}
		}
	}
}