package logic;

public class Propellant {

	private double mass;
	
	public Propellant(int mass) {
		this.mass = mass;
	}
	
	public double getMass(){
		return mass;
	}
	
	public boolean consume(double amount) {
		if(mass-amount<0) {
			return false;
		} else {
			mass -= amount;
			return true;
		}
	}
	
}
