package logic;

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
	
	private int x, y, width, height;
	private int verticalSpeed, horizontalSpeed;
	private int pitch;
	private int stageCount;
	//private Image rocketImage;
	
	public Rocket(int x, int y, RocketStage firstStage, RocketStage secondStage){
		this.firstStage = firstStage;
		this.secondStage = secondStage;
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
			//TODO all stages detached
			thrust = null;
		}
		accelerate(thrust);
	}
	
	public void accelerate(Thrust t) {
		verticalSpeed += t.getVerticalAcceleration(getMass());
		horizontalSpeed += t.getHorizontalAcceleration(getMass());
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
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public int getPitch(){
		return pitch;
	}

	@Override
	public void render(GraphicsContext gc) {
		/*gc.setFill(Color.RED);
		gc.fillRect(x, y, width, height);*/
		gc.drawImage(DrawingUtility.rocketImage, x, y);
	}
}
