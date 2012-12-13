package pl.shockah;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/*
 * source: http://stackoverflow.com/questions/62289/read-write-to-windows-registry-using-java
 */

public class WinRegistry {
	public static final int
		HKEY_CURRENT_USER = 0x80000001, HKEY_LOCAL_MACHINE = 0x80000002,
		REG_SUCCESS = 0, REG_NOTFOUND = 2, REG_ACCESSDENIED = 5,
		KEY_ALL_ACCESS = 0xf003f, KEY_READ = 0x20019;
	private static Preferences userRoot = Preferences.userRoot(), systemRoot = Preferences.systemRoot();
	private static Class<? extends Preferences> userClass = userRoot.getClass();
	private static Method
		regOpenKey = null, regCloseKey = null, regQueryValueEx = null, regEnumValue = null, regQueryInfoKey = null,
		regEnumKeyEx = null, regCreateKeyEx = null, regSetValueEx = null, regDeleteKey = null, regDeleteValue = null;

	static {
		try {
			regOpenKey = Reflection.getPrivateMethod(userClass,"WindowsRegOpenKey",int.class,byte[].class,int.class);
			regCloseKey = Reflection.getPrivateMethod(userClass,"WindowsRegCloseKey",int.class);
			regQueryValueEx = Reflection.getPrivateMethod(userClass,"WindowsRegQueryValueEx",int.class,byte[].class);
			regEnumValue = Reflection.getPrivateMethod(userClass,"WindowsRegEnumValue",int.class,int.class,int.class);
			regQueryInfoKey = Reflection.getPrivateMethod(userClass,"WindowsRegQueryInfoKey1",int.class);
			regEnumKeyEx = Reflection.getPrivateMethod(userClass,"WindowsRegEnumKeyEx",int.class,int.class,int.class);
			regCreateKeyEx = Reflection.getPrivateMethod(userClass,"WindowsRegCreateKeyEx",int.class,byte[].class);
			regSetValueEx = Reflection.getPrivateMethod(userClass,"WindowsRegSetValueEx",int.class,byte[].class,byte[].class);
			regDeleteValue = Reflection.getPrivateMethod(userClass,"WindowsRegDeleteValue",int.class,byte[].class);
			regDeleteKey = Reflection.getPrivateMethod(userClass,"WindowsRegDeleteKey",int.class,byte[].class);
		} catch (Exception e) {e.printStackTrace();}
	}

	/**
	 * Read a value from key and value name
	 * 
	 * @param hkey
	 *            HKEY_CURRENT_USER/HKEY_LOCAL_MACHINE
	 * @param key
	 * @param valueName
	 * @return the value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static String readString(int hkey, String key, String valueName) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		if (hkey == HKEY_LOCAL_MACHINE) return readString(systemRoot,hkey,key,valueName);
		else if (hkey == HKEY_CURRENT_USER) return readString(userRoot,hkey,key,valueName);
		else throw new IllegalArgumentException("hkey="+hkey);
	}

	/**
	 * Read value(s) and value name(s) form given key
	 * 
	 * @param hkey
	 *            HKEY_CURRENT_USER/HKEY_LOCAL_MACHINE
	 * @param key
	 * @return the value name(s) plus the value(s)
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map<String,String> readStringValues(int hkey, String key) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		if (hkey == HKEY_LOCAL_MACHINE) return readStringValues(systemRoot,hkey,key);
		else if (hkey == HKEY_CURRENT_USER) return readStringValues(userRoot,hkey,key);
		else throw new IllegalArgumentException("hkey="+hkey);
	}

	/**
	 * Read the value name(s) from a given key
	 * 
	 * @param hkey
	 *            HKEY_CURRENT_USER/HKEY_LOCAL_MACHINE
	 * @param key
	 * @return the value name(s)
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static List<String> readStringSubKeys(int hkey, String key) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		if (hkey == HKEY_LOCAL_MACHINE) return readStringSubKeys(systemRoot,hkey,key);
		else if (hkey == HKEY_CURRENT_USER) return readStringSubKeys(userRoot,hkey,key);
		else throw new IllegalArgumentException("hkey="+hkey);
	}

	/**
	 * Create a key
	 * 
	 * @param hkey
	 *            HKEY_CURRENT_USER/HKEY_LOCAL_MACHINE
	 * @param key
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void createKey(int hkey, String key) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		int[] ret;
		if (hkey == HKEY_LOCAL_MACHINE) {
			ret = createKey(systemRoot,hkey,key);
			regCloseKey.invoke(systemRoot,ret[0]);
		} else if (hkey == HKEY_CURRENT_USER) {
			ret = createKey(userRoot,hkey,key);
			regCloseKey.invoke(userRoot,ret[0]);
		} else throw new IllegalArgumentException("hkey="+hkey);
		if (ret[1] != REG_SUCCESS) throw new IllegalArgumentException("rc="+ret[1]+"  key="+key);
	}

	/**
	 * Write a value in a given key/value name
	 * 
	 * @param hkey
	 * @param key
	 * @param valueName
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void writeStringValue(int hkey, String key, String valueName, String value) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		if (hkey == HKEY_LOCAL_MACHINE) writeStringValue(systemRoot,hkey,key,valueName,value);
		else if (hkey == HKEY_CURRENT_USER) writeStringValue(userRoot,hkey,key,valueName,value);
		else throw new IllegalArgumentException("hkey="+hkey);
	}

	/**
	 * Delete a given key
	 * 
	 * @param hkey
	 * @param key
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void deleteKey(int hkey, String key) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		int rc = -1;
		if (hkey == HKEY_LOCAL_MACHINE) rc = deleteKey(systemRoot,hkey,key);
		else if (hkey == HKEY_CURRENT_USER) rc = deleteKey(userRoot,hkey,key);
		if (rc != REG_SUCCESS) throw new IllegalArgumentException("rc="+rc+"  key="+key);
	}

	/**
	 * delete a value from a given key/value name
	 * 
	 * @param hkey
	 * @param key
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void deleteValue(int hkey, String key, String value) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		int rc = -1;
		if (hkey == HKEY_LOCAL_MACHINE) rc = deleteValue(systemRoot,hkey,key,value);
		else if (hkey == HKEY_CURRENT_USER) rc = deleteValue(userRoot,hkey,key,value);
		if (rc != REG_SUCCESS) throw new IllegalArgumentException("rc="+rc+"  key="+key+"  value="+value);
	}

	private static int deleteValue(Preferences root, int hkey, String key, String value) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		int[] handles = (int[])regOpenKey.invoke(root,new Object[]{new Integer(hkey),toCstr(key),new Integer(KEY_ALL_ACCESS)});
		if (handles[1] != REG_SUCCESS) return handles[1]; // can be REG_NOTFOUND, REG_ACCESSDENIED
		int rc = ((Integer)regDeleteValue.invoke(root,new Object[]{new Integer(handles[0]),toCstr(value)})).intValue();
		regCloseKey.invoke(root,new Object[]{new Integer(handles[0])});
		return rc;
	}

	private static int deleteKey(Preferences root, int hkey, String key) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		int rc = ((Integer)regDeleteKey.invoke(root,hkey,toCstr(key))).intValue();
		return rc; // can REG_NOTFOUND, REG_ACCESSDENIED, REG_SUCCESS
	}

	private static String readString(Preferences root, int hkey, String key, String value) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		int[] handles = (int[])regOpenKey.invoke(root,hkey,toCstr(key),KEY_READ);
		if (handles[1] != REG_SUCCESS) return null;
		byte[] valb = (byte[])regQueryValueEx.invoke(root,handles[0],toCstr(value));
		regCloseKey.invoke(root,handles[0]);
		return valb != null ? new String(valb).trim() : null;
	}

	private static Map<String,String> readStringValues(Preferences root, int hkey, String key) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		HashMap<String,String> results = new HashMap<String,String>();
		int[] handles = (int[])regOpenKey.invoke(root,hkey,toCstr(key),KEY_READ);
		if (handles[1] != REG_SUCCESS) return null;
		int[] info = (int[])regQueryInfoKey.invoke(root,handles[0]);

		int count = info[0]; // count
		int maxlen = info[3]; // value length max
		for (int index = 0; index < count; index++) {
			byte[] name = (byte[])regEnumValue.invoke(root,handles[0],index,maxlen+1);
			String value = readString(hkey,key,new String(name));
			results.put(new String(name).trim(),value);
		}
		regCloseKey.invoke(root,handles[0]);
		return results;
	}

	private static List<String> readStringSubKeys(Preferences root, int hkey, String key) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		List<String> results = new ArrayList<String>();
		int[] handles = (int[])regOpenKey.invoke(root,hkey,toCstr(key),KEY_READ);
		if (handles[1] != REG_SUCCESS) return null;
		int[] info = (int[])regQueryInfoKey.invoke(root,handles[0]);

		int count = info[0]; // Fix: info[2] was being used here with wrong results. Suggested by davenpcj, confirmed by Petrucio
		int maxlen = info[3]; // value length max
		for (int index = 0; index < count; index++) {
			byte[] name = (byte[])regEnumKeyEx.invoke(root,handles[0],index,maxlen+1);
			results.add(new String(name).trim());
		}
		regCloseKey.invoke(root,handles[0]);
		return results;
	}

	private static int[] createKey(Preferences root, int hkey, String key) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		return (int[])regCreateKeyEx.invoke(root,hkey,toCstr(key));
	}

	private static void writeStringValue(Preferences root, int hkey, String key, String valueName, String value) throws IllegalArgumentException,IllegalAccessException,InvocationTargetException {
		int[] handles = (int[])regOpenKey.invoke(root,hkey,toCstr(key),KEY_ALL_ACCESS);
		regSetValueEx.invoke(root,handles[0],toCstr(valueName),toCstr(value));
		regCloseKey.invoke(root,handles[0]);
	}

	private static byte[] toCstr(String str) {
		byte[] result = new byte[str.length()+1];

		for (int i = 0; i < str.length(); i++) result[i] = (byte)str.charAt(i);
		result[str.length()] = 0;
		return result;
	}
}