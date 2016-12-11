package obstacle;

import graphics.IRenderable;
import javafx.scene.canvas.GraphicsContext;

public abstract class Coin implements IRenderable{
	
	private int score;
	private int x;
	private int y;
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
	
	public int getX(){
		return x;
	}
	
	public int getY(){
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
	}
	
	public void still(double d){
		this.y -= d;
	}
	
	/*public boolean isCollide(){
		//;w;
	}*/
}
