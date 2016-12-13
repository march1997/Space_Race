package obstacle;

import graphics.IRenderable;
import javafx.scene.canvas.GraphicsContext;
import main.Resources;

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
}
