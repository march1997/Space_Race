package main;

import graphics.DrawingUtility;
import graphics.GameScreen;
import graphics.RenderableHolder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Engine;
import logic.Propellant;
import logic.Rocket;
import logic.RocketStage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		DrawingUtility.loadResource();
		
		GameScreen root = new GameScreen();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Space Race");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		RenderableHolder.getInstance().getEntities().add(new Rocket(220, 500, new RocketStage(5, new Engine(5, 5), new Propellant(20), 20), new RocketStage(2, new Engine(2, 2), new Propellant(4), 4)));
		root.render();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
