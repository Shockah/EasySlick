package pl.shockah.easyslick;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import pl.shockah.easyslick.transitions.Transition;

public final class App extends BasicGame implements IGameLoop,Thread.UncaughtExceptionHandler {
	private static App app;
	private static AppGameContainer acontainer;
	private static IAppHooks hooks;
	private static IGameLoop gameLoop;
	private static Room firstRoom;
	private static GraphicsHelper gh = null;
	
	public static void start(Room firstRoom, String windowTitle) {start(null,firstRoom,windowTitle);}
	public static void start(IAppHooks hooks, Room firstRoom, String windowTitle) {
		try {
			App.hooks = hooks;
			acontainer = new AppGameContainer(app = new App(windowTitle));
			
			App.firstRoom = firstRoom;
			firstRoom.setupRoom();
			Window.setup(firstRoom);
			
			acontainer.start();
		} catch (Exception e) {App.getApp().handle(e);}
	}
	
	public App(String title) {
		super(title);
		gameLoop = this;
	}
	
	public void init(GameContainer container) {
		container.setShowFPS(false);
		container.setUpdateOnlyWhenVisible(false);
		container.setAlwaysRender(true);
		Window.addKeys();
		if (hooks != null) hooks.onInit();
		Room.set(firstRoom);
	}
	public void deinit(GameContainer container) {
		if (hooks != null) hooks.onDeinit();
	}
	
	public void render(GameContainer container, Graphics g) {
		if (gh == null) gh = new GraphicsHelper(g);
		
		if (hooks != null) hooks.preRender(gh);
		View.render(gh);
		if (hooks != null) hooks.onRender(gh);
	}
	
	public void update(GameContainer container, int delta) {
		Key.updateKeys();
		Window.updateMouse();
		
		boolean tickOnlySpecial = false;
		if (Room.transition != ETransition.None) {
			Transition tr = Room.transition == ETransition.Out ? Room.tOut : Room.tIn;
			if (tr != null && tr.stopsGameplay()) tickOnlySpecial = true;
		}
		Entity.doTick(delta,tickOnlySpecial);
		
		if (!tickOnlySpecial) {
			Room.get().onTick(delta);
			if (hooks != null) hooks.onTick(delta);
		}
	}
	
	public void uncaughtException(Thread thread, Throwable throwable) {
		handle(throwable);
	}
	
	public void handle(Throwable throwable) {
		try {
			if (hooks != null) hooks.onException(throwable);
			throwable.printStackTrace();
		} catch (Throwable t) {}
	}
	
	public static void setGameLoop(IGameLoop gameLoop) {
		App.gameLoop = gameLoop == null ? app : gameLoop;
		extraLoop();
	}
	public static IGameLoop getGameLoop() {return gameLoop;}
	public static void extraLoop() {
		try {
			acontainer.gameLoop();
		} catch (Exception e) {App.getApp().handle(e);}
	}
	public boolean canRender(Render render) {return true;}
	public boolean canUpdate(Entity entity) {return true;}
	
	public static void stop() {acontainer.stop();}
	public static App getApp() {return app;}
	public static AppGameContainer getAppGameContainer() {return acontainer;}
	
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {App.getApp().handle(e);}
	}
}