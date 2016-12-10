package main;

import exceptions.*;
import graphics.*;
import rocket.*;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
	
	private List<Thread> threads = new ArrayList<>();
	private List<KeyCode> keysPressed = new ArrayList<>();
	
	private Rocket rocket;
	
	private GameScreen gameScreen;
	private Scene scene;
	
	private boolean gameStart;

	private long lastNanoTime;

	@Override
	public void start(Stage primaryStage) {
		gameScreen = new GameScreen();
		scene = new Scene(gameScreen);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Space Race");
		primaryStage.setResizable(false);
		primaryStage.show();

		initResources();
		initListener();
		initRocket();
		
		lastNanoTime = System.nanoTime();
		
		AnimationTimer timer = new AnimationTimer() {
			
			@Override
			public void handle(long currentNanoTime) {
				
				double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000.0;
				lastNanoTime = currentNanoTime;
				System.out.println(elapsedTime);
				
				processInput();
				updateGame();
				renderGame();
				
			}
		};
		timer.start();
		
		
	}

	protected void renderGame() {
		
		gameScreen.render();
		
	}

	protected void updateGame() {
		
		if(rocket.getY()<=260&&!gameScreen.isUpMost()){
			rocket.setY(260);
			gameScreen.moveBackgroundImageUp(rocket.getVerticalSpeed());
			if(gameScreen.isUpMost()){ // we need this because this vertical speed isn't constant :[
				gameScreen.setBackgroundY(0);
			}
		}
		else{
			rocket.move();
		}

		System.out.println(rocket.toString());
		
	}

	protected void processInput() {
		
		if(gameStart){
			if(keysPressed.contains(KeyCode.UP)) {
				try {
					rocket.propel();
				} catch (OutOfPropellantException e) {
					// TODO Auto-generated catch block
					// use run later to display "out of propellant" text on screen
					e.printStackTrace();
				}
				System.out.println("propel");
			}
		}
		
	}
	
	private void initRocket() {
		
		gameStart = false;
		
		Rocket falcon9 = new Rocket(220, 395,
						 new RocketStage(50,	new Engine(5, 500),	new Propellant(20000)), 
						 new RocketStage(20,	new Engine(2, 2),	new Propellant(4)), 
						 new Payload(2));
		
		rocket = falcon9;

		IRenderableHolder.getInstance().getEntities().add(falcon9);
		
		Thread backgroundMusic = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100); //wait for application start
					Resources.soundtrack.play(0.05);
					Resources.ldgoforlaunch.play();
//					gameStart = true;
					Thread.sleep(6000); //wait for go for launch before playing countdown
					Resources.countdown.play();
					Thread.sleep(10000); //wait for countdown before accepting input
					gameStart = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "backgroundMusic");
		threads.add(backgroundMusic);
		backgroundMusic.start();
		
	}

	private void initListener() {
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent arg0) {
				KeyCode keyCode = arg0.getCode();
				if(!keysPressed.contains(keyCode)){
					keysPressed.add(keyCode);
				}
			}
			
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				KeyCode keyCode = arg0.getCode();
				keysPressed.remove(keyCode);
				if(keyCode == KeyCode.UP) {
					rocket.stopEngine();
				}
			}
		});
		
	}

	private void initResources() {
		
		Resources.loadResource();
		
	}

	public void stop() {
		System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
