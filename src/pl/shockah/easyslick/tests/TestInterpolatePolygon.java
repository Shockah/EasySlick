package pl.shockah.easyslick.tests;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;
import pl.shockah.easyslick.App;
import pl.shockah.easyslick.GeomUtil;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Room;

public class TestInterpolatePolygon extends Room {
	protected Polygon p1, p2;
	
	public static void main(String[] args) {
		App.start(new TestInterpolatePolygon(),"Test - InterpolatePolygon");
	}
	
	public TestInterpolatePolygon() {
		maxFPS = 60;
	}
	
	public void onCreate() {
		p1 = new Polygon();
		p1.setClosed(true);
		p1.addPoint(0,0);
		p1.addPoint(0,96);
		p1.addPoint(96,96);
		p1.addPoint(96,0);
		
		p1.setLocation(16,16);
		p2 = GeomUtil.interpolate(GeomUtil.multiplyLines(p1,4),32); p2.setX(p2.getX()+160);
	}
	public void onRender(GraphicsHelper gh) {
		gh.g().setAntiAlias(true);
		gh.g().setColor(Color.white);
		gh.g().draw(p1);
		gh.g().draw(p2);
		gh.g().setAntiAlias(false);
	}
}