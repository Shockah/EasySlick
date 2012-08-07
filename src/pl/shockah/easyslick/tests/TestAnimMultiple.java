package pl.shockah.easyslick.tests;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import pl.shockah.easyslick.App;
import pl.shockah.easyslick.GraphicsHelper;
import pl.shockah.easyslick.Room;
import pl.shockah.easyslick.anim.AnimMultiple;
import pl.shockah.easyslick.anim.AnimMultipleLine;
import pl.shockah.easyslick.anim.AnimState;
import pl.shockah.easyslick.particles.ParticleType;

public class TestAnimMultiple extends Room {
	protected AnimMultiple anim = new AnimMultiple();
	
	public static void main(String[] args) {
		App.start(new TestAnimMultiple(),"Test - AnimMultiple");
	}
	
	public TestAnimMultiple() {
		maxFPS = 60;
		setSize(264,264);
	}
	
	public void onCreate() {
		AnimMultipleLine
			linePos = new AnimMultipleLine().usePos(true),
			lineColor = new AnimMultipleLine().useColor(true),
			lineAngle = new AnimMultipleLine().useAngle(true),
			lineScale = new AnimMultipleLine().useScale(true);
		
		linePos.looping = true;
		lineColor.looping = true;
		lineAngle.looping = true;
		
		linePos.addState(new AnimState().setPos(new Vector2f(20,0)));
		linePos.addState(80,new AnimState().setPos(new Vector2f(180,0)));
		linePos.addState(90,new AnimState().setPos(new Vector2f(190,10)));
		linePos.addState(100,new AnimState().setPos(new Vector2f(200,20)));
		linePos.addState(180,new AnimState().setPos(new Vector2f(200,180)));
		linePos.addState(190,new AnimState().setPos(new Vector2f(190,190)));
		linePos.addState(200,new AnimState().setPos(new Vector2f(180,200)));
		linePos.addState(280,new AnimState().setPos(new Vector2f(20,200)));
		linePos.addState(290,new AnimState().setPos(new Vector2f(10,190)));
		linePos.addState(300,new AnimState().setPos(new Vector2f(0,180)));
		linePos.addState(380,new AnimState().setPos(new Vector2f(0,20)));
		linePos.addState(390,new AnimState().setPos(new Vector2f(10,10)));
		linePos.copyFirstState(400);
		
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
	}
	public void onTick(int delta) {
		anim.updateStep();
	}
	public void onRender(GraphicsHelper gh) {
		gh.drawImage(ParticleType.imFuzzy,32,32,1f/ParticleType.imFuzzy.getWidth()*64f,1f/ParticleType.imFuzzy.getHeight()*64f,anim);
	}
}