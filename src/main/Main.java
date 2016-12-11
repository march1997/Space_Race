package main;

import exceptions.*;

import graphics.*;
import rocket.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import obstacle.Coin;
import obstacle.Fivecoin;
import obstacle.Onecoin;

public class Main extends Application {
	
	private List<Thread> threads = new ArrayList<>();
	private List<KeyCode> keysPressed = new ArrayList<>();
	
	private Rocket rocket;
	
	private GameScreen gameScreen;
	private Scene scene;
	
	private int score = 0;
	
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
		initCoin();
		
		lastNanoTime = System.nanoTime();
		
		AnimationTimer timer = new AnimationTimer() {
			
			@Override
			public void handle(long currentNanoTime) {
				
				double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000.0;
				lastNanoTime = currentNanoTime;
				
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
		
		rocket.accelerate();
		
		if(rocket.getY() <= 260 && !gameScreen.isUpMost() && rocket.getVerticalSpeed() < 0){
			rocket.move();
			rocket.setY(260);
			gameScreen.moveBackgroundImage((int)rocket.getVerticalSpeed());
		}
		else if(rocket.getVerticalSpeed() > 0 && gameScreen.isDownMost()){
			rocket.move();
			if(rocket.getY() >= 395){ // make rocket on the ground
				rocket.setY(395);
				rocket.setVerticalSpeed(0);
				rocket.setHorizontalSpeed(0);
			}
		}
		else if(rocket.getY() >= 260 && rocket.getVerticalSpeed() > 0){
			rocket.setY(260);
			gameScreen.moveBackgroundImage(rocket.getVerticalSpeed());
			rocket.move();
		}	
		else{
			rocket.move();
		}
		for(IRenderable r : IRenderableHolder.getInstance().getEntities()){
			if(r instanceof Coin){
				Coin coin = (Coin) r;
				if(coin.canCollect(rocket)){
					coin.collect();
				}
			}
		}

		System.out.println(rocket.toString());
		
	}

	protected void processInput() {
		
		if(gameStart){
			if(keysPressed.contains(KeyCode.UP)) {
				try {
					rocket.propel();
				} catch (OutOfPropellantException e) {
					// use run later to display "out of propellant" text on screen
					e.printStackTrace();
				}
			}
			else if(keysPressed.contains(KeyCode.LEFT)){
				if(rocket.getPitch()>-20) {
					rocket.rotateCCW();
				}
			}
			else if(keysPressed.contains(KeyCode.RIGHT)){
				if(rocket.getPitch()<20) {
					rocket.rotateCW();
				}
			}
		}
	}
	
	private void initRocket() {
		
		gameStart = false;
		
		Rocket falcon9 = new Rocket(220, 395,
						 new RocketStage(50,	new Engine(5, 500),	new Propellant(20000)), 
						 new RocketStage(20,	new Engine(2, 2),	new Propellant(4)), 
						 new Payload(500));
		
		rocket = falcon9;

		IRenderableHolder.getInstance().getEntities().add(falcon9);
		
		Thread backgroundMusic = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100); //wait for application start
					Resources.soundtrack.play(0.05);
					Resources.ldgoforlaunch.play();
					gameStart = true;
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
	
	private void initCoin(){ // A thread for creating coin
		Thread coinThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					Random rand = new Random();
					int random = rand.nextInt(10) + 1; //random number min 1 max 10
					int randomX = rand.nextInt((int) (480-new Onecoin(0, 0).getWidth()))+1;
					int randomY = rand.nextInt(400);
					try {
						Thread.sleep(5000);
						if(random <= 7){ // probability to create onecoin is 70%
							IRenderableHolder.getInstance().getEntities().add(new Onecoin(randomX, randomY));
						} /*need to change how to create a coin*/
						else{ // propability to create fivecoin is 30%
							IRenderableHolder.getInstance().getEntities().add(new Fivecoin(randomX, randomY));
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		coinThread.start();
		threads.add(coinThread);
	}
}
