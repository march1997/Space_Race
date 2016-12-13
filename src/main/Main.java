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
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import obstacle.Coin;
import obstacle.Fivecoin;
import obstacle.Onecoin;
import obstacle.Plane;
import obstacle.Satellite;

public class Main extends Application {
	
	private List<Thread> threads = new ArrayList<>();
	private List<KeyCode> keysPressed = new ArrayList<>();
	
	private Rocket rocket;
	
	private VBox startScreen;
	private Scene startScene;

	private boolean playClicked = false;
	
	private GameScreen gameScreen;
	private Scene gameScene;
	
	public static int score = 0;
	public static boolean outoffuel = false;
	
	private boolean gameStart = false;
	
	private boolean gamePause;

	private int explosiontime = 0;
	
	private long lastNanoTime;
	
	

	@Override
	public void start(Stage primaryStage) {
		
		startScreen = new VBox(20);

		Slider sliderStageOneMass = new Slider(1000, 5000, 10000);
		Slider sliderStageOneEnginePropellantRate = new Slider(1, 20, 5);
		Slider sliderStageOneEngineThrustRate = new Slider(100, 5000, 500);
		Slider sliderStageOnePropellantMass = new Slider(1000, 20000, 5000);
		Slider sliderStageTwoMass = new Slider(500, 5000, 2000);
		Slider sliderStageTwoEnginePropellantRate = new Slider(1, 10, 2);
		Slider sliderStageTwoEngineThrustRate = new Slider(100, 4000, 400);
		Slider sliderStageTwoPropellantMass = new Slider(500, 10000, 2000);
		Slider sliderPayloadMass = new Slider(100, 5000, 500);
				
		startScreen.getChildren().add(sliderStageOneMass);
		startScreen.getChildren().add(sliderStageOneEnginePropellantRate);
		startScreen.getChildren().add(sliderStageOneEngineThrustRate);
		startScreen.getChildren().add(sliderStageOnePropellantMass);
		startScreen.getChildren().add(sliderStageTwoMass);
		startScreen.getChildren().add(sliderStageTwoEnginePropellantRate);
		startScreen.getChildren().add(sliderStageTwoEngineThrustRate);
		startScreen.getChildren().add(sliderStageTwoPropellantMass);
		startScreen.getChildren().add(sliderPayloadMass);
				
		startScene = new Scene(startScreen);
		//primaryStage.setScene(startScene);
		primaryStage.setTitle("Space Race");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		gameScreen = new GameScreen();
		gameScene = new Scene(gameScreen);
		primaryStage.setScene(gameScene);
		primaryStage.setScene(startScene);
		
		//primaryStage.setScene(gameScene);
		primaryStage.show();

		initResources();
		initListener();
		initRocket();
		initCoin();
		initPlane();
		
		lastNanoTime = System.nanoTime();
		
		AnimationTimer timer = new AnimationTimer() {
			
			@Override
			public void handle(long currentNanoTime) {
				
				double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000.0;
				lastNanoTime = currentNanoTime;
				
				//if(!gamePause && !rocket.isExplosion()) {
					
					processInput();
					updateGame();
					renderGame();
				//}
			}
		};
		timer.start();
		
	}

	protected void renderGame() {
		
		gameScreen.render(rocket);
		
	}

	protected void updateGame() {

		rocket.accelerate();
		rocket.move();
		
		if(rocket.getY() <= 260 && !gameScreen.isUpMost() && rocket.getVerticalSpeed() < 0){
			rocket.setY(260);
			gameScreen.moveBackgroundImage(rocket.getVerticalSpeed());
		}
		else if(rocket.getVerticalSpeed() > 0 && gameScreen.isDownMost()){
			if(rocket.getY() >= 395){ // make rocket on the ground
				rocket.setY(395);
				rocket.setVerticalSpeed(0);
				rocket.setHorizontalSpeed(0);
				rocket.setPitch(0);
			}
		}
		else if(rocket.getY() >= 260 && rocket.getVerticalSpeed() > 0){
			rocket.setY(260);
			gameScreen.moveBackgroundImage(rocket.getVerticalSpeed());
		}	
		for(int i = IRenderableHolder.getInstance().getEntities().size() - 1 ; i >= 0 ; i --){
			IRenderable r = IRenderableHolder.getInstance().getEntities().get(i);
			if(r instanceof Coin){
				Coin coin = (Coin) r;
				if(coin.canCollect(rocket)){
					coin.collect();
					Resources.collectcoinsound.play();
				}
			}
			else if(r instanceof Plane){
				Plane plane = (Plane) r;
				if(plane.isCollide(rocket)){
					System.out.println("Collide");
					plane.collide(rocket);
				}
			}
		}
		if(!rocket.isVisible() && explosiontime == 0){
			explosiontime += 1;
			Resources.explosionsound.play();
		}
		System.out.println(rocket.toString());
		
	}

	protected void processInput() {
		
		if(gameStart){
			if(keysPressed.contains(KeyCode.UP)) {
				try {
					rocket.propel();
				} catch (OutOfPropellantException e) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							outoffuel = true;
							gameScreen.fuel = "OUT OF FUEL";
						}
					});
				}
			}
			if(keysPressed.contains(KeyCode.LEFT)){
				if(rocket.getPitch()>-20) {
					rocket.rotateCCW();
				}
			}
			if(keysPressed.contains(KeyCode.RIGHT)){
				if(rocket.getPitch()<20) {
					rocket.rotateCW();
				}
			}
		}
		
	}
	
	private void initRocket() {
		
		gameStart = false;
		
		Rocket falcon9 = new Rocket(220, 395,
		          new RocketStage(5000, new Engine(5, 500), new Propellant(10000)), 
		          new RocketStage(2000, new Engine(2, 2), new Propellant(4)), 
		          new Payload(500));
		
		rocket = falcon9;

		//IRenderableHolder.getInstance().getEntities().add(falcon9);
		IRenderableHolder.getInstance().getEntities().add(rocket);
		
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
		
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent arg0) {
				KeyCode keyCode = arg0.getCode();
				if(!keysPressed.contains(keyCode)){
					keysPressed.add(keyCode);
				}
				if(keyCode == KeyCode.ENTER) {
					gamePause = !gamePause;
				}
			}
			
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

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
	
	private void initPlane(){
		Thread planeThread = new Thread(new Runnable() { // generate planes
			
			@Override
			public void run() {
				int count=0;
				int k=- 100;
				while(count <= 5){
					Random rand = new Random();
					int randomX = rand.nextInt((int) (480-new Onecoin(0, 0).getWidth()))+1;
					int randomspeed = rand.nextInt(3) + 1;
					//int randomY = k;
					int randomY = - rand.nextInt(200) + k;
					System.out.println(randomY);
					count += 1;
					k -= 500;
					IRenderableHolder.getInstance().getEntities().add(new Plane(randomX, randomY, randomspeed));
				}
			}
		});
		planeThread.start();
		threads.add(planeThread);
		
		Thread planeThread2 = new Thread(new Runnable() { // make planes move
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(true){
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(IRenderable r : IRenderableHolder.getInstance().getEntities()){
						if(r instanceof Plane){
							Plane plane = (Plane) r;
							plane.move();
						}
					}
				}
			}
		});
		planeThread2.start();
	}
	
	private void initCoin(){ // A thread for creating coin
		Thread coinThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int k=0;
				int count=0;
				while(true){
					Random rand = new Random();
					k-=200;
					if(count>50){
						break;
					}
					count+=1;
					int random = rand.nextInt(10) + 1; //random number min 1 max 10
					int randomX = rand.nextInt((int) (480-new Onecoin(0, 0).getWidth()))+1;
					//int randomY = rand.nextInt(200) - 200;
					int randomY = k;
					//try {
						//Thread.sleep(3000);
						if(random <= 7){ // probability to create onecoin is 70%
							IRenderableHolder.getInstance().getEntities().add(new Onecoin(randomX, randomY));
						}
						else{ // propability to create fivecoin is 30%
							IRenderableHolder.getInstance().getEntities().add(new Fivecoin(randomX, randomY));
						}
					/*} catch (InterruptedException e) {
						e.printStackTrace();
					}*/
				}
			}
		});
		coinThread.start();
		threads.add(coinThread);
	}
	
	/*private void initSatellite(){
		Thread satelliteThread = new Thread(new Runnable() {
			public void run() {
				int k=0;
				int count=0;
				while(true){
					Random rand = new Random();
					k-=200;
					if(count>10){
						break;
					}
					count+=1;
					int randomX = rand.nextInt((int) (480-new Onecoin(0, 0).getWidth()))+1;
					//int randomY = rand.nextInt(200) - 200;
					int randomY = k;
					
				}
			}
		});
		satelliteThread.start();
		threads.add(satelliteThread);
	}*/
}
