package pl.shockah.easyslick;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

public final class Math2 {
	public static double root(double value, double degree) {return Math.pow(value,1d/degree);}
	
	public static double distanceToLine(Vector2f p, Vector2f line1, Vector2f line2) {
		return (float)Math.sqrt(Math.pow((line2.y-line1.y)*(p.x-line1.x)+(line2.x-line1.x)*(p.y-line1.y),2)/(line1.distanceSquared(line2)));
	}
	public static double direction(Vector2f v1, Vector2f v2) {return (float)Math.toDegrees(Math.atan2(v1.y-v2.y,v2.x-v1.x));}
	
	public static float ldirX(double dist, double angle) {
		return (float)(-FastTrig.cos(Math.toRadians(angle+180))*dist);
	}
	public static float ldirY(double dist, double angle) {
		return (float)(FastTrig.sin(Math.toRadians(angle+180))*dist);
	}
	
	public static float tickSecs(float secs) {
		if (Room.get() == null) return 60f*secs;
		return Room.get().maxFPS*secs;
	}
	
	public static double frac(double value) {double sign = Math.signum(value); value = Math.abs(value); return (value-Math.floor(value))*sign;}
	
	public static int min(int... values) {int min = values[0]; for (int value : values) if (value < min) min = value; return min;}
	public static float min(float... values) {float min = values[0]; for (float value : values) if (value < min) min = value; return min;}
	public static double min(double... values) {double min = values[0]; for (double value : values) if (value < min) min = value; return min;}
	
	public static int max(int... values) {int max = values[0]; for (int value : values) if (value > max) max = value; return max;}
	public static float max(float... values) {float max = values[0]; for (float value : values) if (value > max) max = value; return max;}
	public static double max(double... values) {double max = values[0]; for (double value : values) if (value > max) max = value; return max;}
	
	public static int limit(int value, int min, int max) {return Math.min(Math.max(value,min),max);}
	public static float limit(float value, float min, float max) {return Math.min(Math.max(value,min),max);}
	public static double limit(double value, double min, double max) {return Math.min(Math.max(value,min),max);}
	
	public static Vector2f makeVector(float dist, float angle) {return new Vector2f(Math2.ldirX(dist,angle),Math2.ldirY(dist,angle));}
	public static float getAngle(Vector2f v) {return (float)Math2.direction(new Vector2f(),v);}
}