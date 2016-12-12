package rocket;

import exceptions.OutOfPropellantException;
import graphics.GameScreen;
import javafx.scene.canvas.GraphicsContext;

public class Propellant {

	private double mass;
	private double density;
	private double massmax;
	
	public Propellant(int mass) {
		this.mass = mass;
		this.massmax = mass;
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
			System.out.println(mass + " " + massmax);
			GameScreen.fuel = "" + (int) (mass / massmax * 100) + " %";
		}
	}
	
	public double getMassMax(){
		return massmax;
	}
	
}
