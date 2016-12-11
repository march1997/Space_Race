package rocket;

public class Thrust {
	
	private double force;
	private double angle;

	public Thrust(double force, double angle) {
		this.force = force;
		this.angle = angle;
	}
	
	public double getVerticalAcceleration(double mass) {
		double acceleration = force * Math.cos(angle) / mass;
		return acceleration;
	}
	
	public double getHorizontalAcceleration(double mass) {
		double acceleration = force * Math.sin(angle) / mass;
		return acceleration;
	}
	
	public double getForce() {
		return force;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double pitch){
		this.angle = pitch;
	}
	
}
