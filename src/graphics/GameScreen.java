package graphics;

import main.Main;
import main.Resources;
import obstacle.Coin;
import obstacle.Plane;
import obstacle.Satellite;
import rocket.Rocket;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameScreen extends StackPane{
	
	private static final int WIDTH = 480;
	private static final int HEIGHT = 720;
	private static final int downMostY = (int) (Resources.backgroundImage.getHeight()-HEIGHT);
	
	private Canvas canvas;
	//private GraphicsContext gc;
	private WritableImage croppedImage;
	private int backgroundY = downMostY; //use to move background image = backgroundheight-gamescreenheight
	private int explosioncount = 0;
	
	public static GraphicsContext gc;
	public static String fuel = "100 %";

	public GameScreen() {
		super();
		
		canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();
		
		getChildren().add(canvas);
	}
	
	public void render(Rocket rocket) {
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		croppedImage = new WritableImage(Resources.backgroundImage.getPixelReader(), 0, backgroundY, WIDTH, HEIGHT); // a moving background
		gc.drawImage(croppedImage, 0, 0);
		if(!rocket.isVisible()){
			explosioncount += 1;
			rocket.setHorizontalSpeed(0);
			rocket.setVerticalSpeed(0);
		}
		if(!rocket.isVisible() && explosioncount <= 65){
			gc.drawImage(Resources.explosionImage, rocket.getCenterOfMassX(), rocket.getCenterOfMassY());
		}
		for(int i = IRenderableHolder.getInstance().getEntities().size() - 1 ; i >= 0  ; i--) {
			if(IRenderableHolder.getInstance().getEntities().get(i) != null && !IRenderableHolder.getInstance().getEntities().get(i).isVisible()){
				System.out.println("Remove");
				IRenderableHolder.getInstance().getEntities().remove(i);
			}
			else if(IRenderableHolder.getInstance().getEntities().get(i) != null){
				IRenderableHolder.getInstance().getEntities().get(i).render(gc);
			}
		}
		gc.setFill(Color.WHITE);
		gc.setFont(Font.font("Consolas", 25));
		gc.fillText("Score : " + Main.score, 300, 30, 170);
		gc.fillText("Fuel : " + fuel, 10, 30);
	}

	public void moveBackgroundImage(double d){
		if((backgroundY <= 0 || backgroundY + d <= 0) && d < 0){
			backgroundY = 0;
		}
		/*else if(backgroundY >= downMostY && backgroundY + d >= downMostY && d >= 0 ){
			backgroundY = downMostY;
		}*/
		else{
			if(d > 0 && d < 1){
				d = 1;
			}
			if(d < 0 && d > -1){
				d=-1;
			}
			d = (int) d;
			backgroundY += d;
			for(Object o : IRenderableHolder.getInstance().getEntities()){ // not finished yet :[ use to make coin float in the air
				if(o instanceof Coin){
					Coin coin = (Coin) o;
					coin.still(d);
				}
				else if(o instanceof Plane){
					Plane plane = (Plane) o;
					plane.still(d);
				}
				else if(o instanceof Satellite){
					Satellite satellite = (Satellite) o;
					satellite.still(d);
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
		backgroundY = y;
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
