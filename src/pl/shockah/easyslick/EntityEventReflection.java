package pl.shockah.easyslick;

import java.lang.reflect.Method;

public abstract class EntityEventReflection extends EntityEvent {
	protected final Method method;
	protected final String methodName;
	
	public EntityEventReflection(String methodName) {this(true,false,methodName);}
	public EntityEventReflection(Method method) {this(true,false,method);}
	public EntityEventReflection(boolean tick, boolean render, String methodName) {
		super(tick,render);
		this.methodName = methodName;
		method = null;
	}
	public EntityEventReflection(boolean tick, boolean render, Method method) {
		super(tick,render);
		this.method = method;
		methodName = null;
		
		method.setAccessible(true);
	}
	
	protected void onEvent(Entity e) {
		if (method == null) {
			Class<?> cls = e.getClass();
			while (true) {
				if (cls == Entity.class) return;
				try {
					Method m = cls.getDeclaredMethod(methodName);
					m.setAccessible(true);
					m.invoke(e);
				} catch (NoSuchMethodException e1) {continue;
				} catch (Exception e1) {App.getApp().handle(e1);}
				cls = cls.getSuperclass();
			}
		} else {
			try {
				method.invoke(e);
			} catch (Exception e1) {App.getApp().handle(e1);}
		}
	}
}