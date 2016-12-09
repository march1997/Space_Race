package logic;

public class RocketStage {

	private int x, y, width, height;
	private int maxFuel, currentFuel;
	
	public RocketStage(int x, int y, int width, int height, int maxFuel, int currentFuel){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.maxFuel = maxFuel;
		this.currentFuel = currentFuel;
	}
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public int getMaxFuel(){
		return maxFuel;
	}

	public int getCurrentFuel(){
		return currentFuel;
	}
}
