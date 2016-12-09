package main;

import graphics.GameScreen;
import graphics.RenderableHolder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rocket.*;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		GameScreen gameScreen = new GameScreen();
		Scene scene = new Scene(gameScreen);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Space Race");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		addListener(gameScreen);
		
		Rocket rocket = new Rocket(220, 500, new RocketStage(5, new Engine(5, 5), new Propellant(20), 20), new RocketStage(2, new Engine(2, 2), new Propellant(4), 4), null);
		RenderableHolder.getInstance().getEntities().add(rocket);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					rocket.move();
					gameScreen.render();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
		
		
	}
	
	public void stop() {
		System.exit(0);
	}

	private void addListener(GameScreen gameScreen) {
		
//		TODO keyPressHandler
//		gameScreen.setOnKeyPressed(value);
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
