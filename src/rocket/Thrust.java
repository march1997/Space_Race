package rocket;

public class Thrust {
	
	private double force;
	private double angle;

	public Thrust(double force, double angle) {
		this.force = force;
		this.angle = angle;
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
