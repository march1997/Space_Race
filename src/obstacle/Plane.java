package obstacle;

import graphics.IRenderable;
import javafx.scene.canvas.GraphicsContext;
import main.Resources;
import rocket.Rocket;

public class Plane implements IRenderable{
	
	private int x;
	private int y;
	private int speed;
	private int width;
	private int height;
	private boolean isvisible;

	
	public Plane(int x, int y, int speed){
		this.x = x;
		this.y = y;
		this.isvisible = true;
		this.speed = speed;
		this.width = 90;
		this.height = 50;
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(Resources.planeImage, x, y);
	}


	@Override
	public boolean isVisible() {
		return isvisible;
	}
	
	public boolean isCollide(Rocket rocket){
		if(rocket.getX() >= x && rocket.getX() <= x + width && rocket.getY() >= y && rocket.getY() <= y + height){
			return true;
		}
		return false;
	}
	
	public void collide(Rocket rocket){
		rocket.explosion();
	}

	public void still(double move){
		this.y -= move;
	}
}
