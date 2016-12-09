package graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GameScreen extends StackPane{
	
	private static final int WIDTH = 480;
	private static final int HEIGHT = 720;
	
	private Canvas canvas;
	private GraphicsContext gc;
	//private Image backgroundImage;
	private WritableImage croppedImage;
	private int backgroundY=630; //use to move background image
	
	public GameScreen() {
		super();
		
		canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();
		
		getChildren().add(canvas);
	}
	
	public void render() {
		//gc.setFill(Color.SKYBLUE);
		//gc.fillRect(0, 0, WIDTH, HEIGHT);
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		croppedImage = new WritableImage(DrawingUtility.backgroundImage.getPixelReader(), 0, backgroundY, 480, 720 ); // a moving background
		gc.drawImage(croppedImage, 0, 0);
		for(IRenderable r:RenderableHolder.getInstance().getEntities()) {
			r.render(gc);
//			System.out.println("Rendered object: " + r);
		}
//		System.out.println("---------");
	}

	public void moveBackgroundImageUp(double d){
		if(backgroundY<=0){
			backgroundY=0;
		}
		else{
			backgroundY+=d;
			System.out.println(backgroundY);
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
}
