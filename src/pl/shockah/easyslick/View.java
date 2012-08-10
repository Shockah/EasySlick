package pl.shockah.easyslick;

import java.util.ArrayList;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public final class View {
	private static ArrayList<View> views = new ArrayList<View>(), toAdd = new ArrayList<View>(), toRemove = new ArrayList<View>();
	protected static View view = null;
	
	public static View get() {return view;}
	public static View getDefault() {return get(0f);}
	public static View get(float depth) {
		for (View view : views) if (view.depth == depth) return view;
		return null;
	}
	
	public static void clear() {clear(false);}
	protected static void clear(boolean force) {
		if (force) {
			views.clear();
			toAdd.clear();
			toRemove.clear();
		} else {
			toAdd.clear();
			toRemove.addAll(views);
		}
	}
	
	public static void add(View view) {toAdd.add(view);}
	public static void remove(View view) {toRemove.add(view);}
	
	protected static void render(GraphicsHelper gh) {
		views.removeAll(toRemove);
		toRemove.clear();
		
		for (View view : toAdd) addView(view);
		toAdd.clear();
		for (View view : views) view.onRender(gh);
	}
	private static void addView(View view) {
		if (get(view.depth) != null) throw new RuntimeException("View with depth "+view.depth+" already exists");
		for (int i = 0; i < views.size(); i++) {
			if (views.get(i).depth < view.depth) {
				views.add(i,view);
				return;
			}
		}
		views.add(view);
	}
	
	public Vector2f pos, size, portPos, portSize;
	public float depth, angle = 0f;
	
	public View(Vector2f pos, Vector2f size, Vector2f portPos, Vector2f portSize, float depth) {
		this.pos = pos.copy();
		this.size = size.copy();
		this.portPos = portPos.copy();
		this.portSize = portSize.copy();
		this.depth = depth;
	}
	
	public void changeDepth(int depth) {
		this.depth = depth;
		toRemove.add(this);
		toAdd.add(this);
	}
	
	public boolean canRender(Render render) {return true;}
	protected void onRender(GraphicsHelper gh) {
		view = this;
		setGL(gh);
		gh.setClip(new Rectangle(pos.x,pos.y,size.x,size.y));
		Render.doRender(gh);
		Room.get().onRender(gh);
		resetGL(gh);
		gh.resetClip();
		view = null;
	}
	
	protected Vector2f getScale() {
		return new Vector2f(portSize.x/size.x,portSize.y/size.y);
	}
	private void setGL(GraphicsHelper gh) {
		Vector2f scale = getScale();
		gh.getPrivates();
		if (portPos.x != 0 || portPos.y != 0) gh.g.translate(-portPos.x,-portPos.y);
		if (angle != 0) gh.gl.glRotatef(-angle,0f,0f,1f);
		if (scale.x != 1f || scale.y != 1f) gh.g.scale(1f/scale.x,1f/scale.y);
		if (pos.x != 0 || pos.y != 0) gh.g.translate(-pos.x,-pos.y);
	}
	private void resetGL(GraphicsHelper gh) {
		Vector2f scale = getScale();
		if (pos.x != 0 || pos.y != 0) gh.g.translate(pos.x,pos.y);
		if (scale.x != 1f || scale.y != 1f) gh.g.scale(scale.x,scale.y);
		if (angle != 0) gh.gl.glRotatef(angle,0f,0f,1f);
		if (portPos.x != 0 || portPos.y != 0) gh.g.translate(portPos.x,portPos.y);
	}
}