package pl.shockah.easyslick;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import org.newdawn.slick.Input;

public abstract class Key {
	protected static ArrayList<Key> keysList = new ArrayList<Key>();
	protected static Key[] keys = new Key[256];
	protected static Key keyAnyKey = new KeyAnyKey();
	
	protected static void registerAllKeys() {
		Field[] fields = Input.class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (Modifier.isStatic(field.getModifiers()) && field.getName().startsWith("KEY_")) {
				try {
					int value = field.getInt(null);
					keysList.add(keys[value] = new KeySingle(value));
					keys[value].register(App.getAppGameContainer().getInput());
				} catch (Exception e) {App.getApp().handle(e);}
			}
		}
		
		keyAnyKey.register(App.getAppGameContainer().getInput());
	}
	protected static void updateKeys() {
		for (Key key : keysList) key.updateStates(App.getAppGameContainer().getInput());
	}
	
	public static void registerKey(Key key, Input input) {
		keysList.add(key);
		key.register(input);
	}
	public static void registerKey(Key key) {
		registerKey(key,App.getAppGameContainer().getInput());
	}
	public static void unregisterKey(Key key, Input input) {
		keysList.remove(key);
		key.unregister(input);
	}
	public static void unregisterKey(Key key) {
		unregisterKey(key,App.getAppGameContainer().getInput());
	}
	
	protected boolean pressed = false, released = false, down = false;
	
	protected abstract void register(Input input);
	protected abstract void unregister(Input input);
	protected abstract void updateStates(Input input);
	
	public final boolean pressed() {return pressed;}
	public final boolean released() {return released;}
	public final boolean down() {return down;}
	
	public static boolean pressed(int key) {return keys[key].pressed();}
	public static boolean released(int key) {return keys[key].released();}
	public static boolean down(int key) {return keys[key].down();}
}