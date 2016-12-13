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
		if(rocket.getX() >= x && rocket.getX() <= x + WIDTH && rocket.getY() >= y && rocket.getY() <= y + HEIGHT){
			return true;
		}
		return false;
	}
	
	public void collide(Rocket rocket){
		rocket.explosion();
	}
}
