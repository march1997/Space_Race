package logic;

import exceptions.OutOfPropellantException;

public class Engine {
	
	private double propellantComsumptionRate;
	private double thrustOutputRate;
	
	public Engine(double propellantComsumptionRate, double thrustOutputRate) {
		this.propellantComsumptionRate = propellantComsumptionRate;
		this.thrustOutputRate = thrustOutputRate;
	}
	
	public double combust(Propellant p) throws OutOfPropellantException {
		if(p.consume(propellantComsumptionRate * 1)) {
			return thrustOutputRate * 1;
		} else {
			throw new OutOfPropellantException();
		}
	}

}
