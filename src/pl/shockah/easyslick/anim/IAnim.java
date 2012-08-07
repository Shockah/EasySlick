package pl.shockah.easyslick.anim;

public interface IAnim {
	public void updateStep();
	public void updateStep(float steps);
	public void setStep(float step);
	public void setFirstStep();
	public void setLastStep();
	public AnimState getCurrentState();
}