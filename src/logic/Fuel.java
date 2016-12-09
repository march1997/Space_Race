package logic;

public class Fuel implements Consumable{

	private int maxAmount, currentAmount;
	private double density;
	
	@Override
	public int getMaxAmount() {
		return maxAmount;
	}

	@Override
	public int getCurrentAmount() {
		return currentAmount;
	}

	@Override
	public boolean consume(int amount) {
		currentAmount -= amount;
		if(currentAmount<0){
			currentAmount+=amount;
			return false;
		} else{
			return true;
		}
	}

	@Override
	public boolean replenish(int amount) {
		currentAmount += amount;
		if(currentAmount>maxAmount){
			currentAmount-=amount;
			return false;
		} else{
			return true;
		}
	}
	

}
