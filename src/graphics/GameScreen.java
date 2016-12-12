package graphics;

import main.Resources;
import obstacle.Coin;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;

public class GameScreen extends StackPane{
	
	private static final int WIDTH = 480;
	private static final int HEIGHT = 720;
	private static final int downMostY = (int) (Resources.backgroundImage.getHeight()-HEIGHT);
	
	private Canvas canvas;
	private GraphicsContext gc;
	private WritableImage croppedImage;
	private int backgroundY = downMostY; //use to move background image = backgroundheight-gamescreenheight
	
	public GameScreen() {
		super();
		
		canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();
		
		getChildren().add(canvas);
	}
	
	public void render() {
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		croppedImage = new WritableImage(Resources.backgroundImage.getPixelReader(), 0, backgroundY, WIDTH, HEIGHT); // a moving background
		gc.drawImage(croppedImage, 0, 0);
		for(int i = IRenderableHolder.getInstance().getEntities().size()-1 ; i >= 0  ; i--) {
			if(!IRenderableHolder.getInstance().getEntities().get(i).isVisible()){
				IRenderableHolder.getInstance().getEntities().remove(i);
			}
			else{
				IRenderableHolder.getInstance().getEntities().get(i).render(gc);
			}
		}
	}

	public void moveBackgroundImage(double d){
		if((backgroundY <= 0 || backgroundY + d <= 0) && d < 0){
			backgroundY = 0;
		}
		/*else if(backgroundY >= downMostY && backgroundY + d >= downMostY && d >= 0 ){
			backgroundY = downMostY;
		}*/
		else{
			backgroundY += d;
			for(Object o : IRenderableHolder.getInstance().getEntities()){ // not finished yet :[ use to make coin float in the air
				if(o instanceof Coin){
					Coin coin = (Coin) o;
					coin.still((int)d);
				}
			}
			if(backgroundY >= downMostY){
				backgroundY = downMostY;
			}
		}
	}
	
	public int getBackgroundY(){
		return backgroundY;
	}
	
	public void setBackgroundY(int y){
		backgroundY=y;
	}
	
	public boolean isUpMost(){
		if(backgroundY <= 0){
			return true;
		}
		return false;
	}
	
	public GraphicsContext getGraphicsContext() {
		return gc;
	}
	
	public boolean isDownMost(){
		if(backgroundY >= (int) (Resources.backgroundImage.getHeight()-HEIGHT)){
			return true;
		}
		return false;
	}
	
	public int getDownMostY(){
		return downMostY;
	}
}
