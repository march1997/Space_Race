package rocket;

import exceptions.OutOfPropellantException;

public class Propellant {

	private double mass;
	private double density;
	
	public Propellant(int mass) {
		this.mass = mass;
	}
	
	public double getMass() {
		return mass;
	}
	
	public double getVolume() {
		double volume = mass / density;
		return volume;
	}
	
	public void consume(double amount) throws OutOfPropellantException {
		if(mass-amount<0) {
			throw new OutOfPropellantException();
		} else {
			mass -= amount;
		}
	}
	
}
