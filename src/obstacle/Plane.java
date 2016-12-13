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
		this.width = 85;
		this.height = 45;
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

	public void still(double move){
		this.y -= move;
	}
	
	public void move(){
		this.x += this.speed;
		if(x > 480){
			x -= 480;
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
