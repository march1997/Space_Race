package rocket;

import exceptions.OutOfPropellantException;
import graphics.IRenderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Rocket implements IRenderable{
	
	private final RocketStage firstStage;
	private final RocketStage secondStage;
	
	private int x, y, width, height;
	private double verticalSpeed, horizontalSpeed;
	private int pitch;
	private int stageCount;
	private Image rocketImage;
	
	public Rocket(int x, int y, RocketStage firstStage, RocketStage secondStage, Payload payload){
		this.firstStage = firstStage;
		this.secondStage = secondStage;
		this.x = x;
		this.y = y;
		this.verticalSpeed = -4;
		this.horizontalSpeed = 0;
		this.width = 20;
		this.height = 100;
		
	}
	
	public void propel() throws OutOfPropellantException {
		Thrust thrust;
		if(stageCount == 2) {
			thrust = firstStage.propel();
		} else if(stageCount == 1) {
			thrust = secondStage.propel();
		} else {
			//TODO all stages detached
			thrust = null;
		}
		accelerate(thrust);
	}
	
	public void accelerate(Thrust t) {
		verticalSpeed += t.getVerticalAcceleration(getMass());
		horizontalSpeed += t.getHorizontalAcceleration(getMass());
	}
	
	public void move() {
		x += horizontalSpeed;
		y += verticalSpeed;
	}
	
	public double getMass() {
		double rocketMass;
		if(stageCount == 2) {
			rocketMass = firstStage.getMass() + secondStage.getMass();
		} else if(stageCount == 1) {
			rocketMass = secondStage.getMass();
		} else {
			//TODO all stages detached
			rocketMass = 0;
		}
		return rocketMass; 
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.setFill(Color.RED);
		gc.fillRect(x, y, width, height);
		
	}
}
