package logic;

public interface Consumable {

	public int getMaxAmount();
	public int getCurrentAmount();
	public boolean consume(int amount);
	public boolean replenish(int amount);

}
