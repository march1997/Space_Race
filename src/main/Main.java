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
		
		rocket = new Rocket(180, 500, 
				 new RocketStage(5, new Engine(5, 500), new Propellant(2000), 20), 
				 new RocketStage(2, new Engine(2, 2), new Propellant(4), 4), 
				 new Payload(2));
		
		RenderableHolder.getInstance().getEntities().add(rocket);
		
		Thread audioThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				DrawingUtility.soundtrack.play(0.05);
				DrawingUtility.ldgoforlaunch.play();
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				DrawingUtility.countdown.play();
			}
		});
		ThreadHolder.getInstance().getThreads().add(audioThread);
		audioThread.start();
		
		Thread movingThread = new Thread(new Runnable() {
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
//					System.out.println(rocket.toString());
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Platform.runLater(new Runnable() {
						public void run() {
							gameScreen.render();
						}
					});
				}
			}
		}, "movingThread");
		ThreadHolder.getInstance().getThreads().add(movingThread);
		movingThread.start();
		
		
	}
	
	public void stop() {
		System.exit(0);
	}

	private void addListener(GameScreen gameScreen) {
		
		Thread listener = new Thread(new Runnable() {
			
			@Override
			public void run() {
				gameScreen.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent arg0) {
						KeyCode keyCode = arg0.getCode();
						if(keyCode == KeyCode.UP){
							try {
								rocket.propel();
//								DrawingUtility.enginecombustion.play();
								System.out.println("propelled");
							} catch (OutOfPropellantException e) {
								e.printStackTrace();
							}
						}
					}
				});
			}
		}, "listener");
		listener.start();
		ThreadHolder.getInstance().getThreads().add(listener);
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
