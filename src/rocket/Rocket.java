package rocket;

import exceptions.OutOfPropellantException;
import graphics.IRenderable;
import javafx.scene.canvas.GraphicsContext;
import main.Resources;

public class Rocket implements IRenderable{
	
	private final RocketStage firstStage;
	private final RocketStage secondStage;
	private final Payload payload;
	
	private int x, y, width, height;
	private double verticalSpeed, horizontalSpeed;
	private double pitch;
	private int stageCount;
	private boolean isPropelling;
	private boolean engineSoundPlayed;
	
	private double verticalForce, horizontalForce;
	
	
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
		this.isPropelling = false;
		this.engineSoundPlayed = false;
	}
	
	public void propel() throws OutOfPropellantException {
		isPropelling = true;
		Thrust thrust;
		if(stageCount == 2) {
			thrust = firstStage.propel();
		} else if(stageCount == 1) {
			thrust = secondStage.propel();
		} else {
			thrust = new Thrust(0, 0);
		}
		verticalSpeed -= thrust.getVerticalAcceleration(getMass());
		horizontalSpeed += thrust.getHorizontalAcceleration(getMass());
	}
	
	public void move() {
		x += horizontalSpeed;
		y += verticalSpeed;
	}
	
	
	public void update() {
		
	}
	
	
	public void detachStage() {
		stageCount -= 1;
		//adjust width height
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
	
	public void stopEngine() {
		Resources.enginecombustion.stop();
		engineSoundPlayed = false;
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(Resources.rocketImage, x, y);
		if(isPropelling){
			gc.drawImage(Resources.enginefire, x, y+210);
			if(!Resources.enginecombustion.isPlaying() && !engineSoundPlayed) {
				Resources.enginecombustion.play();
				engineSoundPlayed = true;
			}
			isPropelling = false;
		}
	}
	
	@Override
	public String toString() {
		return "(x:" + x + ", y:" + y + ", hs:" + (int)horizontalSpeed + ", vs:" + verticalSpeed + ", p:" + (int)pitch + ")";
		
	}
	
	public int getCenterOfMassX() {
		return x + width/2;
	}
	
	public int getCenterOfMassY() {
		return y + height/2;
	}
	
	public boolean inTheMiddle() {
		if(y>=360){
			return true;
		}
		return false;
	}
	
	public double getVerticalSpeed(){
		return verticalSpeed;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y=y;
	}

	public double getX() {
		return x;
	}
}
