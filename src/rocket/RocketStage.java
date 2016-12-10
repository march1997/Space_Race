package rocket;

import exceptions.OutOfPropellantException;

public class RocketStage {
	
	private final double dryMass;
	
	private Engine engine;
	private Propellant propellant;
	private int gimbalAngle;
	private int gimbalAngleRange;
	
	
	public RocketStage(double dryMass, Engine engine, Propellant propellant) {
		this.dryMass = dryMass;
		this.engine = engine;
		this.propellant = propellant;
	}
	
	public Thrust propel() throws OutOfPropellantException {
		Thrust thrust = new Thrust(engine.combust(propellant), gimbalAngle);
		return thrust;
	}
	
	public void setGimbalAngle(int gimbalAngle){
		if(gimbalAngle > -1 * gimbalAngleRange && gimbalAngle < gimbalAngleRange) {
			this.gimbalAngle = gimbalAngle;
		}
	}
	
	public double getMass(){
		return dryMass + propellant.getMass();
	}
	
}
