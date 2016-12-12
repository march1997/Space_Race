package obstacle;

import javafx.scene.canvas.GraphicsContext;
import main.Resources;

public class Onecoin extends Coin{

	public Onecoin(int x, int y) {
		super(100, x, y);
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(Resources.oneCoinImage, this.getX(), this.getY());
	}
}
