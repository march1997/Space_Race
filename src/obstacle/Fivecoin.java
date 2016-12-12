package obstacle;

import javafx.scene.canvas.GraphicsContext;
import main.Resources;

public class Fivecoin extends Coin{
	
	public Fivecoin(int x, int y){
		super(500, x, y);
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(Resources.fiveCoin, this.getX(), this.getY());
	}

}
