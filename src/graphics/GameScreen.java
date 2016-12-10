package graphics;

import main.Resources;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;

public class GameScreen extends StackPane{
	
	private static final int WIDTH = 480;
	private static final int HEIGHT = 720;
	
	private Canvas canvas;
	private GraphicsContext gc;
	private WritableImage croppedImage;
	private int backgroundY=(int) (Resources.backgroundImage.getHeight()-HEIGHT); //use to move background image = backgroundheight-gamescreenheight
	
	public GameScreen() {
		super();
		
		canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();
		
		getChildren().add(canvas);
	}
	
	public void render() {
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		croppedImage = new WritableImage(Resources.backgroundImage.getPixelReader(), 0, backgroundY, 480, 720 ); // a moving background
		gc.drawImage(croppedImage, 0, 0);
		for(IRenderable r:IRenderableHolder.getInstance().getEntities()) {
			r.render(gc);
		}
	}

	public void moveBackgroundImageUp(double d){
		if(backgroundY<=0){
			backgroundY=0;
		}
		else{
			backgroundY+=d;
		}
	}
	
	public int getBackgroundY(){
		return backgroundY;
	}
	
	public void setBackgroundY(int y){
		backgroundY=y;
	}
	
	public boolean isUpMost(){
		if(backgroundY<=0){
			return true;
		}
		return false;
	}
	
	public GraphicsContext getGraphicsContext() {
		return gc;
	}
}
