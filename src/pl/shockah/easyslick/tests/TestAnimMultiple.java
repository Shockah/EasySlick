package pl.shockah.easyslick.tests;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;
import pl.shockah.easyslick.App;
import pl.shockah.easyslick.GeomUtil;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Room;
import pl.shockah.easyslick.anim.AnimMultiple;
import pl.shockah.easyslick.anim.AnimMultipleLine;
import pl.shockah.easyslick.anim.AnimState;
import pl.shockah.easyslick.particles.ParticleType;

public class TestAnimMultiple extends Room {
	protected AnimMultiple anim = new AnimMultiple();
	protected Polygon p;
	
	public static void main(String[] args) {
		App.start(new TestAnimMultiple(),"Test - AnimMultiple");
	}
	
	public TestAnimMultiple() {
		maxFPS = 60;
		setSize(256,256);
	}
	
	public void onCreate() {
		p = new Polygon();
		p.setClosed(true);
		p.addPoint(0,0);
		p.addPoint(192,0);
		p.addPoint(192,192);
		p.addPoint(0,192);
		p = GeomUtil.interpolate(GeomUtil.multiplyLines(p,4),32);
		
		AnimMultipleLine
			linePos = new AnimMultipleLine(p,2),
			lineColor = new AnimMultipleLine().useColor(true),
			lineAngle = new AnimMultipleLine().useAngle(true),
			lineScale = new AnimMultipleLine().useScale(true);
		
		linePos.looping = true;
		lineColor.looping = true;
		lineAngle.looping = true;
		
		lineColor.addState(new AnimState().setColor(Color.red));
		lineColor.addState(30,new AnimState().setColor(Color.orange));
		lineColor.addState(60,new AnimState().setColor(Color.yellow));
		lineColor.addState(90,new AnimState().setColor(Color.green));
		lineColor.addState(120,new AnimState().setColor(Color.cyan));
		lineColor.addState(150,new AnimState().setColor(Color.blue));
		lineColor.addState(180,new AnimState().setColor(Color.magenta));
		lineColor.copyFirstState(210);
		
		lineAngle.addState(new AnimState().setAngle(360f));
		lineAngle.addState(120,new AnimState().setAngle(0f));
		
		lineScale.addState(new AnimState().setScale(1f,.5f));
		
		anim.addLines(linePos,lineColor,lineAngle,lineScale);
		p.setLocation(32,32);
	}
	public void onTick(int delta) {
		anim.updateStep();
	}
	public void onRender(GraphicsHelper gh) {
		gh.g().setColor(Color.white);
		gh.g().draw(p);
		gh.drawImage(ParticleType.imFuzzy,32,32,1f/ParticleType.imFuzzy.getWidth()*64f,1f/ParticleType.imFuzzy.getHeight()*64f,anim);
	}
}