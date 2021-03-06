package pl.shockah.easyslick;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.transitions.Transition;

public abstract class Entity extends Render {
	private static List<Entity> entities = new LinkedList<Entity>(), toRemove = new LinkedList<Entity>(), toAdd = new LinkedList<Entity>();
	private static List<EntityEvent> eventsGlobal = new LinkedList<EntityEvent>();
	
	public static void addGlobalEvent(EntityEvent event) {eventsGlobal.add(event);}
	public void removeGlobalEvent(EntityEvent event) {eventsGlobal.remove(event);}
	
	public static void clear() {clear(false);}
	protected static void clear(boolean force) {
		if (force) {
			entities.removeAll(toRemove);
			toRemove.clear();
			
			Iterator<Entity> it = entities.iterator();
			while (it.hasNext()) if (!it.next().persistent) it.remove();
		} else toRemove.addAll(entities);
		toAdd.clear();
	}
	public static void doTick(int delta, boolean tickOnlySpecial) {
		for (Entity e : entities) {
			if (App.getGameLoop().canUpdate(e) && (!tickOnlySpecial || isSpecial(e))) e.tick(delta);
		}
		
		entities.removeAll(toRemove);
		toRemove.clear();
		
		entities.addAll(toAdd);
		toAdd.clear();
	}
	public static boolean isSpecial(Entity e) {
		if (e == Room.tIn || e == Room.tOut) return true;
		if (e instanceof Transition) return true;
		return false;
	}
	public static ArrayList<Entity> getEntities() {return new ArrayList<Entity>(entities);}
	
	public static ArrayList<Entity> getEntities(Class<?>... entityClasses) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		for (Entity entity : entities) for (Class<?> cls : entityClasses) if (cls.isAssignableFrom(entity.getClass())) list.add(entity);
		return list;
	}
	
	protected boolean didOnAfterCreate = false, checkCollision = true, persistent = false;
	public Shape shape = null;
	public Vector2f pos = new Vector2f(), shapeOffset = new Vector2f();
	protected ArrayList<EntityEvent> events = new ArrayList<EntityEvent>();
	
	protected void addEvent(EntityEvent event) {events.add(event);}
	protected void removeEvent(EntityEvent event) {events.remove(event);}
	
	protected final void tick(int delta) {
		updateShape();
		if (!didOnAfterCreate) {onAfterCreate(); didOnAfterCreate = true;}
		onTick(delta);
		updateShape();
		for (EntityEvent ev : eventsGlobal) if (ev.tick) ev.run(this);
		for (EntityEvent ev : events) if (ev.tick) ev.run(this);
	}
	protected final void render(GraphicsHelper gh) {
		if (!didOnAfterCreate) {onAfterCreate(); didOnAfterCreate = true;}
		onRender(gh);
		for (EntityEvent ev : eventsGlobal) if (ev.render) ev.run(this);
		for (EntityEvent ev : events) if (ev.render) ev.run(this);
	}
	
	public final void create(Vector2f pos) {create(pos.x,pos.y);}
	public final void create(float x, float y) {
		pos = new Vector2f(x,y);
		create();
	}
	public final void create() {
		onCreate();
		super.create();
		toAdd.add(this);
	}
	public final void destroy() {
		super.destroy();
		toRemove.add(this);
		onDestroy();
	}
	
	protected void onCreate() {}
	protected void onAfterCreate() {}
	protected void onDestroy() {}
	protected void onTick(int delta) {}
	protected void onRender(GraphicsHelper gh) {}
	protected void onRoomStart() {}
	protected void onRoomEnd() {}
	protected void onAppEnd() {}
	
	public boolean collides(Entity entity) {
		if (!checkCollision) return false;
		if (entity == null) return false;
		if (!entity.checkCollision) return false;
		if (shape == null) return false;
		if (entity.shape == null) return false;
		if (equals(entity)) return false;
		if (!equals(entity)) entity.updateShape();
		return shape.intersects(entity.shape) || shape.contains(entity.shape);
	}
	public boolean collides(List<Entity> entities) {
		if (shape == null) return false;
		for (Entity entity : entities) if (collides(entity)) return true;
		return false;
	}
	public boolean collides(Class<? extends Entity> entityClass) {
		if (shape == null) return false;
		for (Entity entity : entities) if (entityClass.isAssignableFrom(entity.getClass()) && collides(entity)) return true;
		return false;
	}
	public boolean collides(Class<? extends Entity>... entityClasses) {
		if (shape == null) return false;
		for (Entity entity : entities) for (Class<? extends Entity> cls : entityClasses) if (cls.isAssignableFrom(entity.getClass()) && collides(entity)) return true;
		return false;
	}
	
	public boolean collidesShape(Shape shape) {
		if (!checkCollision) return false;
		if (this.shape == null) return false;
		if (shape == null) return false;
		return this.shape.intersects(shape) || this.shape.contains(shape);
	}
	public boolean collidesShape(List<Shape> shapes) {
		if (shape == null) return false;
		for (Shape sh : shapes) if (collidesShape(sh)) return true;
		return false;
	}
	
	public static boolean shapeCollides(Shape shape, Entity entity) {
		if (entity == null) return false;
		return entity.collidesShape(shape);
	}
	public static boolean shapeCollides(Shape shape, List<Entity> entities) {
		if (shape == null) return false;
		for (Entity entity : entities) if (shapeCollides(shape,entity)) return true;
		return false;
	}
	public static boolean shapeCollides(Shape shape, Class<? extends Entity> entityClass) {
		if (shape == null) return false;
		for (Entity entity : entities) if (entityClass.isAssignableFrom(entity.getClass()) && shapeCollides(shape,entity)) return true;
		return false;
	}
	public static boolean shapeCollides(Shape shape, Class<? extends Entity>... entityClasses) {
		if (shape == null) return false;
		for (Entity entity : entities) for (Class<? extends Entity> cls : entityClasses) if (cls.isAssignableFrom(entity.getClass()) && shapeCollides(shape,entity)) return true;
		return false;
	}
	
	public List<Entity> collidesWith(List<Entity> entities) {
		List<Entity> list = new LinkedList<Entity>();
		if (shape == null) return list;
		for (Entity entity : entities) if (collides(entity)) list.add(entity);
		return list;
	}
	
	public boolean collides(Vector2f pos, Entity entity) {
		if (shape == null) return false;
		updateShape(pos);
		boolean collide = collides(entity);
		updateShape();
		return collide;
	}
	public boolean collides(Vector2f pos, List<Entity> entities) {
		if (shape == null) return false;
		updateShape(pos);
		boolean collide = collides(entities);
		updateShape();
		return collide;
	}
	public boolean collides(Vector2f pos, Class<? extends Entity> entityClass) {
		if (shape == null) return false;
		updateShape(pos);
		boolean collide = collides(entityClass);
		updateShape();
		return collide;
	}
	public boolean collides(Vector2f pos, Class<? extends Entity>... entityClasses) {
		if (shape == null) return false;
		updateShape(pos);
		boolean collide = collides(entityClasses);
		updateShape();
		return collide;
	}
	
	public boolean collidesShape(Vector2f pos, Shape shape) {
		if (this.shape == null) return false;
		updateShape(pos);
		boolean collide = collidesShape(shape);
		updateShape();
		return collide;
	}
	public boolean collidesShape(Vector2f pos, List<Shape> shapes) {
		if (shape == null) return false;
		updateShape(pos);
		boolean collide = collidesShape(shapes);
		updateShape();
		return collide;
	}
	
	public void updateShape(Vector2f pos) {
		if (shape != null) {shape.setX(pos.x-shapeOffset.x); shape.setY(pos.y-shapeOffset.y);}
	}
	public void updateShape() {
		updateShape(pos);
	}
	
	public boolean posOutside() {return posOutside(0);}
	public boolean posOutside(float tolerance) {
		return pos.x < -tolerance || pos.y < -tolerance || pos.x > Room.get().size.x+tolerance || pos.y > Room.get().size.y+tolerance;
	}
}