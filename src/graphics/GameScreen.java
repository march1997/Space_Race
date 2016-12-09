package graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GameScreen extends StackPane{
	
	private static final int WIDTH = 480;
	private static final int HEIGHT = 720;
	
	private Canvas canvas;
	private GraphicsContext gc;
	private Image backgroundImage;
	
	public GameScreen() {
		super();
		
		canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();
		
		getChildren().add(canvas);
	}
	
	public void render() {
		gc.setFill(Color.SKYBLUE);
		gc.clearRect(0, 0, WIDTH, HEIGHT);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		for(IRenderable r:RenderableHolder.getInstance().getEntities()) {
			r.render(gc);
			System.out.println("Rendered object: " + r);
		}
		System.out.println("---------");
	}

}
