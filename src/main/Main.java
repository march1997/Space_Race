package main;

import exceptions.OutOfPropellantException;
import graphics.*;
import rocket.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
	
	Rocket rocket;

	@Override
	public void start(Stage primaryStage) {
		GameScreen gameScreen = new GameScreen();
		Scene scene = new Scene(gameScreen);
		DrawingUtility.loadResource();
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Space Race");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		gameScreen.requestFocus();
		addListener(gameScreen);
		
		rocket = new Rocket(220, 500, new RocketStage(5, new Engine(5, 50), new Propellant(200), 20), new RocketStage(2, new Engine(2, 2), new Propellant(4), 4), new Payload(2));
		RenderableHolder.getInstance().getEntities().add(rocket);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					if(rocket.getY()<=360&&!gameScreen.isUpMost()){
						rocket.setY(360);
						gameScreen.moveBackgroundImageUp(rocket.getVerticalSpeed());
						if(gameScreen.isUpMost()){ // we need this because this vertical speed isn't constant :[
							gameScreen.setBackgroundY(0);
						}
					}
					else{
						rocket.move();
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Platform.runLater(new Runnable() {
						public void run() {
							gameScreen.render();
						}
					});
				}
			}
		});
		ThreadHolder.instance.getThreads().add(thread);
		thread.start();
		
		
	}
	
	public void stop() {
		System.exit(0);
	}

	private void addListener(GameScreen gameScreen) {
		
//		TODO keyPressHandler
//		gameScreen.setOnKeyPressed(value);
		
		gameScreen.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						KeyCode keyCode = arg0.getCode();
						if(keyCode == KeyCode.UP){
							try {
								rocket.propel();
							} catch (OutOfPropellantException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
				});
				
			}
		});
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
