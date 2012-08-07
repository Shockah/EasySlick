package pl.shockah.easyslick;

import org.newdawn.slick.geom.Polygon;

public final class GeomUtil {
	public static Polygon multiplyLines(Polygon p, int times) {
		if (times < 2) return p;
		
		Polygon ret = new Polygon();
		ret.setClosed(p.closed());
		
		for (int i = 0; i < p.getPointCount(); i++) {
			float[] point = p.getPoint(i);
			float[] pointNext = i < p.getPointCount()-1 ? p.getPoint(i+1) : (p.closed() ? p.getPoint(0) : null);
			
			ret.addPoint(point[0],point[1]);
			if (pointNext != null) {
				float aX = (pointNext[0]-point[0])/times, aY = (pointNext[1]-point[1])/times;
				for (int i2 = 1; i2 <= times; i2++) ret.addPoint(point[0]+aX*i2,point[1]+aY*i2);
			}
		}
		
		return ret;
	}
	public static Polygon interpolate(Polygon p, int points) {
		if (points < 1) return p;
		
		Polygon ret = new Polygon();
		ret.setClosed(p.closed());
		
		for (int i = 0; i < p.getPointCount(); i++) {
			float[]
				point = p.getPoint(i),
				pointNext = i < p.getPointCount()-1 ? p.getPoint(i+1) : (p.closed() ? p.getPoint(0) : null),
				pointPrev = i > 0 ? p.getPoint(i-1) : (p.closed() ? p.getPoint(p.getPointCount()-1) : null);
			
			if (pointNext == null || pointPrev == null) ret.addPoint(point[0],point[1]);
			else {
				for (int i2 = 1; i2 <= points; i2++) {
					float t = 1f/(points+1)*i2;
					ret.addPoint((1f-t)*(1f-t)*pointPrev[0]+2f*(1f-t)*t*point[0]+t*t*pointNext[0],(1f-t)*(1f-t)*pointPrev[1]+2f*(1f-t)*t*point[1]+t*t*pointNext[1]);
				}
				ret.addPoint(pointNext[0],pointNext[1]);
				i++;
			}
		}
		
		return ret;
	}
}