package pl.shockah.easyslick;

import java.util.ArrayList;

public abstract class Render {
	private static ArrayList<Render> renders = new ArrayList<Render>(), toAdd = new ArrayList<Render>(), toRemove = new ArrayList<Render>();
	private static int nextID = 0;
	
	public static void clear() {clear(false);}
	protected static void clear(boolean force) {
		if (force) {
			renders.clear();
			toAdd.clear();
			toRemove.clear();
		} else {
			toAdd.clear();
			toRemove.addAll(renders);
		}
	}
	public static void doRender(GraphicsHelper gh) {
		renders.removeAll(toRemove);
		toRemove.clear();
		
		for (Render render : toAdd) addRender(render);
		toAdd.clear();
		for (Render render : renders) if (App.getGameLoop().canRender(render) && View.get().canRender(render)) render.render(gh);
	}
	private static void addRender(Render render) {
		for (int i = 0; i < renders.size(); i++) {
			if (renders.get(i).depth < render.depth) {
				renders.add(i,render);
				return;
			}
		}
		renders.add(render);
	}
	
	public final int id;
	public float depth = 0;
	
	protected Render() {
		id = nextID++;
	}
	public boolean equals(Object instance) {
		if (instance == null) return false;
		if (!(instance instanceof Render)) return false;
		return id == ((Render)instance).id;
	}
	public int hashCode() {return id;}
	
	public void changeDepth(int depth) {
		this.depth = depth;
		toRemove.add(this);
		toAdd.add(this);
	}
	protected abstract void render(GraphicsHelper gh);
	
	public void create() {toAdd.add(this);}
	public void destroy() {toRemove.add(this);}
	
	public static float xOnView(float x) {return xOnView(View.getDefault(),x);}
	public static float yOnView(float y) {return xOnView(View.getDefault(),y);}
	public static float xOnView(View view, float x) {return view.pos.x+x;}
	public static float yOnView(View view, float y) {return view.pos.y+y;}
}