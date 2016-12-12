package rocket;

import exceptions.OutOfPropellantException;
import graphics.IRenderable;
import javafx.scene.canvas.GraphicsContext;
import main.Main;
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
	private boolean isvisible;

	private static final double GRAVITY = 0.01;
	
	private double longitudinalForce, lateralForce;
	
	private Thrust thrust;
	
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
		this.isvisible = true;
	}
	
	public void propel() throws OutOfPropellantException {
		isPropelling = true;
		if(stageCount == 2) {
			thrust = firstStage.propel();
		} else if(stageCount == 1) {
			thrust = secondStage.propel();
		} else {
			thrust = new Thrust(0, 0);
		}
		
		thrust.setAngle(this.pitch);
		longitudinalForce = thrust.getForce() * Math.sin(Math.toRadians(thrust.getAngle()+90));
		lateralForce = thrust.getForce() * Math.cos(Math.toRadians(thrust.getAngle()-90));
//		longitudinalForce = -1*Math.sin(Math.toRadians(thrust.getAngle()+90));
//		lateralForce = Math.cos(Math.toRadians(thrust.getAngle()-90));
	}
	
	public void accelerate() {
		
		verticalAcceleration = -1 * (longitudinalForce * Math.cos(Math.toRadians(pitch)) + lateralForce * Math.sin(Math.toRadians(pitch)) - (GRAVITY * getMass())) / getMass();
		horizontalAcceleration = 	(longitudinalForce * Math.sin(Math.toRadians(pitch)) + lateralForce * Math.cos(Math.toRadians(pitch))) / getMass();
//		verticalAcceleration = longitudinalForce/20 + GRAVITY;
//		horizontalAcceleration = lateralForce/20;
//		System.out.println(this.verticalAcceleration + " " + this.horizontalAcceleration + " " + pitch);
		
		verticalSpeed += verticalAcceleration;
		horizontalSpeed += horizontalAcceleration;
		
		longitudinalForce = 0;
		lateralForce = 0;
//		System.out.println(verticalAcceleration + " " + horizontalAcceleration);
		
	}
	
	public void move() {
		x += horizontalSpeed;
		y += verticalSpeed;
		if(x>480){
			x-=480;
		}
		else if(x<0){
			x+=480;
		}
	}
	
	
	public void detachStage() {
		stageCount -= 1;
	}
	
	public void stopEngine() {
		Resources.enginecombustion.stop();
		engineSoundPlayed = false;
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.save();
		
		gc.translate(this.getCenterOfMassX(), this.getCenterOfMassY()); /* translate to rotate rocket correctly */
		gc.rotate(pitch);
		gc.translate(-this.getCenterOfMassX(), -this.getCenterOfMassY());
		gc.drawImage(Resources.rocketImage, x, y);
		
		if(isPropelling && !Main.outoffuel){
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
	
	/*public void startSoundThread(GraphicsContext gc){
		Thread backgroundRocketSound = new Thread(new Runnable() {
			public void run() {
				while(true){
					if(isPropelling){
						gc.drawImage(Resources.enginefire, x, y+210);
						if(!Resources.enginecombustion.isPlaying() && !engineSoundPlayed) {
							Resources.enginecombustion.play();
							engineSoundPlayed = true;
						}
						isPropelling = false;
					}
				}
			}
		}, "backgroundRocketSound");
		backgroundRocketSound.start();
	}*/
	
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
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getPitch(){
		return pitch;
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public void setY(int y){
		this.y=y;
	}
	
	public void setVerticalSpeed(int verticalSpeed){
		this.verticalSpeed = verticalSpeed;
	}
	
	public boolean isVisible(){
		return isvisible;
	}
	
	public void rotateCCW(){
		this.pitch -= 1;
	}
	
	public void rotateCW(){
		this.pitch +=1;
	}
	
	public void setHorizontalSpeed(double horizontalSpeed){
		this.horizontalSpeed = horizontalSpeed;
	}
	
	public boolean getPropelling(){
		return isPropelling;
	}
	
	public void setPitch(int pitch){
		this.pitch = pitch;
	}
	
	public void explosion(){
		this.isvisible = false;
	}
}
