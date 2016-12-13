package exceptions;

import graphics.GameScreen;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Alert.AlertType;
import main.Main;

public class OutOfPropellantException extends Exception{
	public OutOfPropellantException(){

	}
}
