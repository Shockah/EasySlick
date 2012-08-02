package pl.shockah.easyslick;

public final class GameLoop {
	protected final IGameLoop gameLoop;
	protected boolean running = true;
	
	public GameLoop(IGameLoop gameLoop) {
		this.gameLoop = gameLoop;
	}
	
	public void run() {
		if (!App.getAppGameContainer().getRunning()) return;
		
		IGameLoop oldLoop = App.getGameLoop();
		App.setGameLoop(gameLoop);
		while (running && App.getAppGameContainer().getRunning()) {
			try {
				App.getAppGameContainer().gameLoop();
			} catch (Exception e) {App.getApp().handle(e);}
		}
		App.setGameLoop(oldLoop);
	}
	
	public void stop() {
		running = false;
	}
}