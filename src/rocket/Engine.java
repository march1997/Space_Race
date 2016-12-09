package rocket;

import exceptions.OutOfPropellantException;

public class Engine {
	
	private double propellantComsumptionRate;
	private double thrustOutputRate;
	
	public Engine(double propellantComsumptionRate, double thrustOutputRate) {
		this.propellantComsumptionRate = propellantComsumptionRate;
		this.thrustOutputRate = thrustOutputRate;
	}
	
	public double combust(Propellant p) throws OutOfPropellantException {
		p.consume(propellantComsumptionRate * 1);
		return thrustOutputRate * 1;
	}

}
