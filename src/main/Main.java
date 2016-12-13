package main;

import exceptions.*;


import graphics.*;
import rocket.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.zip.InflaterInputStream;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
	public static boolean isEnding;
	
	private boolean gameStart = false;
	
	private boolean gamePause;

	private int explosiontime = 0;
	
	private long lastNanoTime;
	
	private double stageOneMass;
	private double stageOneEnginePropellantRate;
	private double stageOneEngineThrustRate;
	private double stageOnePropellantMass;
	private double stageTwoMass;
	private double stageTwoEnginePropellantRate;
	private double stageTwoEngineThrustRate;
	private double stageTwoPropellantMass;
	private double payloadMass;

	@Override
	public void start(Stage primaryStage) {
		
		isEnding = false;
		startScreen = new VBox(10);
		startScreen.setPrefSize(480, 720);

		Slider sliderStageOneMass = new Slider(10000, 50000, 22200);
		Slider sliderStageOneEnginePropellantRate = new Slider(10000, 40000, 25000);
		Slider sliderStageOneEngineThrustRate = new Slider(1000000, 10000000, 7607000);
		Slider sliderStageOnePropellantMass = new Slider(100000, 600000, 409500);
		Slider sliderStageTwoMass = new Slider(1000, 10000, 4000);
		Slider sliderStageTwoEnginePropellantRate = new Slider(1000, 4000, 2500);
		Slider sliderStageTwoEngineThrustRate = new Slider(500000, 2000000, 934000);
		Slider sliderStageTwoPropellantMass = new Slider(50000, 200000, 107500);
		Slider sliderPayloadMass = new Slider(5000, 50000, 20000);
		Button playButton = new Button("Play");
		
		Label spacerace = new Label("Space Race");
		spacerace.setFont(Font.font("Arial", 30));
		//spacerace.setTextFill(Color.BLACK);
		spacerace.setText("Space Race");
		
		Label lb1 = new Label("Rocket stage one mass :");
		Label lb2 = new Label("");
		Label lb3 = new Label("");
		Label lb4 = new Label("");
		Label lb5 = new Label("");
		Label lb6 = new Label("");
		Label lb7 = new Label("");
		Label lb8 = new Label("");
		Label lb9 = new Label("");
		SimpleIntegerProperty s1 = new SimpleIntegerProperty();
		SimpleIntegerProperty s2 = new SimpleIntegerProperty();
		SimpleIntegerProperty s3 = new SimpleIntegerProperty();
		SimpleIntegerProperty s4 = new SimpleIntegerProperty();
		SimpleIntegerProperty s5 = new SimpleIntegerProperty();
		SimpleIntegerProperty s6 = new SimpleIntegerProperty();
		SimpleIntegerProperty s7 = new SimpleIntegerProperty();
		SimpleIntegerProperty s8 = new SimpleIntegerProperty();
		SimpleIntegerProperty s9 = new SimpleIntegerProperty();
		//lb1.textProperty().bind(sliderStageOneEnginePropellantRate.valueProperty().asString());
		s1.bind(sliderStageOneMass.valueProperty());
		lb1.textProperty().bind(s1.asString());
		s2.bind(sliderStageOneEnginePropellantRate.valueProperty());
		lb2.textProperty().bind(s2.asString());
		s3.bind(sliderStageOneEngineThrustRate.valueProperty());
		lb3.textProperty().bind(s3.asString());
		s4.bind(sliderStageOnePropellantMass.valueProperty());
		lb4.textProperty().bind(s4.asString());
		s5.bind(sliderStageTwoMass.valueProperty());
		lb5.textProperty().bind(s5.asString());
		s6.bind(sliderStageTwoEnginePropellantRate.valueProperty());
		lb6.textProperty().bind(s6.asString());
		s7.bind(sliderStageTwoEngineThrustRate.valueProperty());
		lb7.textProperty().bind(s7.asString());
		s8.bind(sliderStageTwoPropellantMass.valueProperty());
		lb8.textProperty().bind(s8.asString());
		s9.bind(sliderPayloadMass.valueProperty());
		lb9.textProperty().bind(s9.asString());
				
		startScreen.getChildren().add(spacerace);
		startScreen.getChildren().add(sliderStageOneMass);
		startScreen.getChildren().add(sliderStageOneEnginePropellantRate);
		startScreen.getChildren().add(sliderStageOneEngineThrustRate);
		startScreen.getChildren().add(sliderStageOnePropellantMass);
		startScreen.getChildren().add(sliderStageTwoMass);
		startScreen.getChildren().add(sliderStageTwoEnginePropellantRate);
		startScreen.getChildren().add(sliderStageTwoEngineThrustRate);
		startScreen.getChildren().add(sliderStageTwoPropellantMass);
		startScreen.getChildren().add(sliderPayloadMass);
		startScreen.getChildren().add(playButton);
		startScreen.getChildren().add(lb1);
		startScreen.getChildren().add(lb2);
		startScreen.getChildren().add(lb3);
		startScreen.getChildren().add(lb4);
		startScreen.getChildren().add(lb5);
		startScreen.getChildren().add(lb6);
		startScreen.getChildren().add(lb7);
		startScreen.getChildren().add(lb8);
		startScreen.getChildren().add(lb9);
		
		startScreen.setAlignment(Pos.CENTER);
		
		startScene = new Scene(startScreen);
		primaryStage.setScene(startScene);
		primaryStage.setTitle("Space Race");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		Thread gameThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						gameScreen = new GameScreen();
						gameScene = new Scene(gameScreen);
						primaryStage.setScene(gameScene);
						initResources();
						initListener();
						initRocket();
						initCoin();
						initPlane();
						initSatellite();
						lastNanoTime = System.nanoTime();
						AnimationTimer timer = new AnimationTimer() {

							@Override
							public void handle(long currentNanoTime) {

								double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000.0;
								lastNanoTime = currentNanoTime;
								if(!isEnding){
									processInput();
									updateGame();
								}
									renderGame();
								
							}
						};
						timer.start();
					}
				});
				
			}
		}, "gameThread");
		threads.add(gameThread);
		
		playButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				stageOneMass = sliderStageOneMass.getValue();
				stageOneEnginePropellantRate = sliderStageOneEnginePropellantRate.getValue();
				stageOneEngineThrustRate = sliderStageOneEngineThrustRate.getValue();
				stageOnePropellantMass = sliderStageOnePropellantMass.getValue();
				stageTwoMass = sliderStageTwoMass.getValue();
				stageTwoEnginePropellantRate = sliderStageTwoEnginePropellantRate.getValue();
				stageTwoEngineThrustRate = sliderStageTwoEngineThrustRate.getValue();
				stageTwoPropellantMass = sliderStageTwoMass.getValue();
				payloadMass = sliderPayloadMass.getValue();
				
				gameThread.start();
			}
		});
		
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
			if(rocket.getY() >= 395 && rocket.isRocketStageOne()){ // make rocketstageone on the ground
				rocket.setY(395);
				rocket.setVerticalSpeed(0);
				rocket.setHorizontalSpeed(0);
				rocket.setPitch(0);
			}
			else if(rocket.getY() >= 530 && !rocket.isRocketStageOne()){ // make rocketstagetwo on the ground
				rocket.setY(530);
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
			if(r instanceof Coin ){
				Coin coin = (Coin) r;
				if(rocket.isVisible() && coin.canCollect(rocket)){
					coin.collect();
					Resources.collectcoinsound.play();
				}
			}
			else if(r instanceof Plane){
				Plane plane = (Plane) r;
				if(plane.isCollide(rocket)){
					plane.collide(rocket);
				}
			}
			else if(r instanceof Satellite){
				Satellite satellite = (Satellite) r;
				if(satellite.isCollide(rocket)){
					satellite.collide(rocket);
				}
			}
		}
		if(!rocket.isVisible() && explosiontime == 0){
			explosiontime += 1;
			Resources.explosionsound.play();
		}
		System.out.println(rocket.toString());
		
		if((!rocket.isVisible() && explosiontime == 1) || (rocket.getY() <= 0 && explosiontime == 0)){ // show score at the end of the game
			explosiontime += 1;
			isEnding = true;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Space Race");
			alert.setHeaderText(null);
			alert.setContentText("Your score is " + score + "\n" + "Thank you for playing!");
			alert.show();
			//Optional<ButtonType> result = alert.showAndWait();
			//System.exit(0);
			alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
				
				@Override
				public void handle(DialogEvent event) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
		}
		
		if(rocket.isRocketStageOne() && rocket.getY() <= 260){
			rocket.changeState();
		}
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
		          new RocketStage(stageOneMass, new Engine(stageOneEnginePropellantRate/60, stageOneEngineThrustRate/300), new Propellant(stageOnePropellantMass)), 
		          new RocketStage(stageTwoMass, new Engine(stageTwoEnginePropellantRate/60, stageTwoEngineThrustRate/300), new Propellant(stageTwoPropellantMass)), 
		          new Payload(payloadMass));
		
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
				int k = 0;
				int count = 0;
				while(true){
					Random rand = new Random();
					k -= 200;
					if(count > 70){
						break;
					}
					count+=1;
					int random = rand.nextInt(10) + 1; //random number min 1 max 10
					int randomX = rand.nextInt((int) (480-new Onecoin(0, 0).getWidth()))+1;
					int randomY = rand.nextInt(100) + k;
						if(random <= 7){ // probability to create onecoin is 70%
							IRenderableHolder.getInstance().getEntities().add(new Onecoin(randomX, randomY));
						}
						else{ // propability to create fivecoin is 30%
							IRenderableHolder.getInstance().getEntities().add(new Fivecoin(randomX, randomY));
						}
				}
			}
		});
		coinThread.start();
		threads.add(coinThread);
	}
	
	private void initSatellite(){
		Thread satelliteThread = new Thread(new Runnable() {
			public void run() {
				int k = -2880;
				int count = 0;
				while(true){
					Random rand = new Random();
					if(count > 10){
						break;
					}
					count += 1;
					k -= 500;
					int randomX = rand.nextInt((int) (480 - new Satellite(0, 0).getWidth()))+1;
					int randomY = rand.nextInt(200) + k;
					IRenderableHolder.getInstance().getEntities().add(new Satellite(randomX, randomY));
					
				}
			}
		});
		satelliteThread.start();
		threads.add(satelliteThread);
	}
}
