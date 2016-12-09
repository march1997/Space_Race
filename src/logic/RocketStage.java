package logic;

import exceptions.OutOfPropellantException;

public class RocketStage {
	
	private final double dryMass;
	
	private Engine engine;
	private Propellant propellant;
	private int tankCapacity;
	private int gimbalAngle;
	private int gimbalAngleRange;
	
	
	public RocketStage(double dryMass, Engine engine, Propellant propellant, int tankCapacity) {
		this.dryMass = dryMass;
		this.engine = engine;
		this.propellant = propellant;
		this.tankCapacity = tankCapacity;
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
	
	

	

//	
//	public boolean replenish(RocketStage stage, int amount) {
//		mass += amount;
//		if(mass>stage.getTankCapacity()){
//			mass-=amount;
//			return false;
//		} else{
//			return true;
//		}
//	}
//	
}
