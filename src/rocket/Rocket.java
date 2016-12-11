package rocket;

import exceptions.OutOfPropellantException;
import graphics.IRenderable;
import javafx.scene.canvas.GraphicsContext;
import main.Resources;

public class Rocket implements IRenderable{
	
	private final RocketStage firstStage;
	private final RocketStage secondStage;
	private final Payload payload;
	
	private double x, y, width, height;
	private double pitch;
	private double verticalSpeed, horizontalSpeed, rotationalSpeed;
	private double verticalAcceleration, horizontalAcceleration, rotationalAcceleration;
	
	private int stageCount;
	
	private boolean isPropelling;
	private boolean engineSoundPlayed;

	private static final double GRAVITY = 0.01;
	
	private double longitudinalForce, lateralForce;
	
	public Rocket(int x, int y, RocketStage firstStage, RocketStage secondStage, Payload payload){
		this.firstStage = firstStage;
		this.secondStage = secondStage;
		this.payload = payload;
		this.stageCount = 2;
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 100;
		this.pitch = 0;
		this.verticalSpeed = 0;
		this.horizontalSpeed = 0;
		this.rotationalSpeed = 0;
		this.longitudinalForce = 0;
		this.lateralForce = 0;
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
		
		longitudinalForce = thrust.getForce() * Math.cos(thrust.getAngle());
		lateralForce = thrust.getForce() * Math.sin(thrust.getAngle());
		
	}
	
	public void accelerate() {
		
		verticalAcceleration = -1 * (longitudinalForce * Math.cos(pitch) + lateralForce * Math.sin(pitch) - (GRAVITY * getMass())) / getMass();
		horizontalAcceleration = 	(longitudinalForce * Math.sin(pitch) + lateralForce * Math.cos(pitch)) / getMass();
		
		verticalSpeed += verticalAcceleration;
		horizontalSpeed += horizontalAcceleration;
		
		longitudinalForce = 0;
		lateralForce = 0;
		
	}
	
	public void move() {
		x += horizontalSpeed;
		y += verticalSpeed;
	}
	
	
	public void detachStage() {
		stageCount -= 1;
		//TODO
	}
	
	public void stopEngine() {
		Resources.enginecombustion.stop();
		engineSoundPlayed = false;
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.save();
		
		gc.rotate(pitch);
		gc.drawImage(Resources.rocketImage, x, y);
		
		if(isPropelling){
			gc.drawImage(Resources.enginefire, x, y+210);
			if(!Resources.enginecombustion.isPlaying() && !engineSoundPlayed) {
				Resources.enginecombustion.play();
				engineSoundPlayed = true;
			}
			isPropelling = false;
		}

		gc.restore();
	}
	
	@Override
	public String toString() {
		return "(x:" + x + ", y:" + y + ", hs:" + horizontalSpeed + ", vs:" + verticalSpeed + ", rs:" + rotationalSpeed + ", p:" + (int)pitch + ")";
		
	}
	
	public double getWidth(){
		return width;
	}
	
	public double getHeight(){
		return height;
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
	
	public double getCenterOfMassX() {
		return x + width/2;
	}
	
	public double getCenterOfMassY() {
		return y + height/2;
	}
	
	public double getVerticalSpeed(){
		return verticalSpeed;
	}
	
	public double getHorizontalSpeed(){
		return horizontalSpeed;
	}
	
	public double getY(){
		return y;
	}
	
	public void setY(int y){
		this.y=y;
	}
	
	public double getX(){
		return x;
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public void setVerticalSpeed(int verticalSpeed){
		this.verticalSpeed = verticalSpeed;
	}
	
	public boolean isVisible(){
		return true;
	}
}
