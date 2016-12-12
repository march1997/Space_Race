package exceptions;

import graphics.GameScreen;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

public class OutOfPropellantException extends Exception{
	
	public OutOfPropellantException(){
		GameScreen.fuel = "Out of fuel";
	}
}
