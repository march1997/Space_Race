package graphics;

import javafx.scene.image.Image;

public class DrawingUtility {// for loading every picture
	public static Image rocketImage;
	public static Image backgroundImage;
	
	static{
		loadResource();
	}
	
	public static void loadResource(){ //Load pictures at 1 time
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		rocketImage = new Image(loader.getResourceAsStream("rocketmake3.png"));
		backgroundImage = new Image(loader.getResourceAsStream("sky.png"));
	}
}
