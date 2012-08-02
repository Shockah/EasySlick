package pl.shockah.easyslick;

public class SpriteSheet extends org.newdawn.slick.SpriteSheet {
	public SpriteSheet(Image image, int tw, int th) {super(image,tw,th);}
	
	public Image getSubImage(int x, int y) {
		return (Image)super.getSubImage(x,y);
	}
	public Image getSprite(int x, int y) {
		return (Image)super.getSprite(x,y);
	}
	
	public void setOffset(float x, float y) {
		for (int yy = 0; yy < getVerticalCount(); yy++)
		for (int xx = 0; xx < getHorizontalCount(); xx++) getSubImage(xx,yy).setOffset(x,y);
	}
	public void center() {
		for (int yy = 0; yy < getVerticalCount(); yy++)
		for (int xx = 0; xx < getHorizontalCount(); xx++) getSubImage(xx,yy).center();
	}
}