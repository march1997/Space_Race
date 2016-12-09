package rocket;

import exceptions.OutOfPropellantException;
import graphics.DrawingUtility;
import graphics.IRenderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

public class Rocket implements IRenderable{
	
	private final RocketStage firstStage;
	private final RocketStage secondStage;
	private final Payload payload;
	
	private int x, y, width, height;
	private double verticalSpeed, horizontalSpeed;
	private double pitch;
	private int stageCount;
	//private Image rocketImage;
	
	public Rocket(int x, int y, RocketStage firstStage, RocketStage secondStage, Payload payload){
		this.firstStage = firstStage;
		this.secondStage = secondStage;
		this.payload = payload;
		this.stageCount = 2;
		this.x = x;
		this.y = y;
		this.verticalSpeed = 0;
		this.horizontalSpeed = 0;
		this.width = 20;
		this.height = 100;
		//loadResource();
	}
	
	public void propel() throws OutOfPropellantException {
		Thrust thrust;
		if(stageCount == 2) {
			thrust = firstStage.propel();
		} else if(stageCount == 1) {
			thrust = secondStage.propel();
		} else {
			thrust = new Thrust(0, 0);
		}
		accelerate(thrust);
	}
	
	public void accelerate(Thrust t) {
		verticalSpeed -= t.getVerticalAcceleration(getMass());
		horizontalSpeed += t.getHorizontalAcceleration(getMass());
		//TODO no gravity yet
	}
	
	public void move() {
		x += horizontalSpeed;
		y += verticalSpeed;
	}
	
	public void detachStage() {
		stageCount -= 1;
	}
	
	public double getMass() {
		double rocketMass;
		if(stageCount == 2) {
			rocketMass = firstStage.getMass() + secondStage.getMass() + payload.getMass();
		} else if(stageCount == 1) {
			rocketMass = secondStage.getMass() + payload.getMass();
		} else {
			rocketMass = payload.getMass();
		}
		return rocketMass; 
	}

	@Override
	public void render(GraphicsContext gc) {
		/*gc.setFill(Color.RED);
		gc.fillRect(x, y, width, height);*/
		gc.drawImage(DrawingUtility.rocketImage, x, y);
	}
	
	@Override
	public String toString() {
		return "(x:" + x + ", y:" + y + ", hs:" + (int)horizontalSpeed + ", vs:" + (int)verticalSpeed + ", p:" + (int)pitch + ")";
		
	}
	
	public int getCenterOfMassX() {
		return x + width/2;
	}
	
	public int getCenterOfMassY() {
		return y + height/2;
	}
}
