package obstacle;

import graphics.IRenderable;
import javafx.scene.canvas.GraphicsContext;
import main.Resources;
import rocket.Rocket;

public class Satellite implements IRenderable{
	
	private int x;
	private int y;
	private final int WIDTH = 180;
	private final int HEIGHT = 56;
	
	public Satellite(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(Resources.satelliteImage, x, y);
	}

	@Override
	public boolean isVisible() {
		return true;
	}
	
	public int getWidth(){
		return WIDTH;
	}
	
	public int getHeight(){
		return HEIGHT;
	}
	
	public void still(double d){
		this.y -= d;
	}
	
	public boolean isCollide(Rocket rocket){
		if((this.x <= rocket.getX() && this.x + this.getWidth() >= rocket.getX() && this.y <= rocket.getY() && this.y + this.getHeight() >= rocket.getY())
				|| (this.x <= rocket.getX() && 	this.x + this.getWidth() >= rocket.getX() && this.y <= rocket.getY() + rocket.getHeight() /2  && this.y + this.getHeight() >= rocket.getY() + rocket.getHeight() /2)
				|| (this.x <= rocket.getX() && this.x + this.getWidth() >= rocket.getX() && this.y <= rocket.getY() + rocket.getHeight() && this.y + this.getHeight() >= rocket.getY() + rocket.getHeight())
				|| (this.x <= rocket.getX() + rocket.getWidth() && this.x + this.getWidth() >= rocket.getX() + rocket.getWidth() && this.y <= rocket.getY() + rocket.getHeight() / 2 && this.y + this.getHeight() >= rocket.getY() + rocket.getHeight() / 2)
				|| (this.x <= rocket.getX() + rocket.getWidth() && this.x + this.getWidth() >= rocket.getX() + rocket.getWidth() && this.y <= rocket.getY() && this.y + this.getHeight() >= rocket.getY())	
			){
			return true;
		}
		return false;
	}
	
	public void collide(Rocket rocket){
		rocket.explosion();
	}
}
