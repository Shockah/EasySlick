package pl.shockah.easyslick;

import java.io.InputStream;

public class EHandler {
	public static Image newImage(String path) {
		try {
			return new Image(path);
		} catch (Exception e) {App.getApp().handle(e);}
		return null;
	}
	public static Image newImage(int width, int height) {
		try {
			return new Image(width,height);
		} catch (Exception e) {App.getApp().handle(e);}
		return null;
	}
	public static Image newImage(InputStream is, String ref, boolean flipped) {
		try {
			return new Image(is,ref,flipped);
		} catch (Exception e) {App.getApp().handle(e);}
		return null;
	}
}