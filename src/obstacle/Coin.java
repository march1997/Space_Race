package obstacle;

import graphics.IRenderable;
import graphics.IRenderableHolder;
import javafx.scene.canvas.GraphicsContext;
import main.Main;
import rocket.Rocket;

public abstract class Coin implements IRenderable{
	
	private int score;
	private double x;
	private double y;
	private final int WIDTH = 60;
	private final int HEIGHT = 60;
	private boolean visible; // if it is not visible, it will be deleted
	
	public abstract void render(GraphicsContext gc);
	
	public Coin(int score, int x, int y){
		this.score = score;
		this.x = x;
		this.y = y;
		this.visible = true;
	}
	
	public int getScore(){
		return score;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public int getWidth(){
		return WIDTH;
	}
	
	public int getHeight(){
		return HEIGHT;
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void collect(){
		this.visible = false;
		Main.score += this.score;
	}
	
	public void still(double d){
		this.y -= d;
	}
	
	public boolean canCollect(Rocket rocket){
		if(this.x < rocket.getX() && this.x + this.getWidth() > rocket.getX() && this.y < rocket.getY() && this.y + this.getHeight() > rocket.getY()){
			return true;
		}
		return false;
	}
}
