package rocket;

import exceptions.OutOfPropellantException;

import graphics.GameScreen;

public class Propellant {

	private double mass;
	private double massMax;
	private double density;
	
	public Propellant(double mass) {
		this.mass = mass;
		this.massMax = mass;
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
			System.out.println(mass + " " + massMax);
			GameScreen.fuel = "" + (int) (mass / massMax * 100) + " %";
		}
	}
	
	public double getMassMax(){
		return massMax;
	}
	
}
