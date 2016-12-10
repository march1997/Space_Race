package graphics;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class DrawingUtility {// for loading every picture
	public static Image rocketImage;
	public static Image backgroundImage;
	public static AudioClip soundtrack;
	public static AudioClip ldgoforlaunch;
	public static AudioClip countdown;
	public static AudioClip stageonepropulsionnominal;
	public static AudioClip stageseperationconfirmed;
	public static AudioClip enginecombustion;
	
	static{
		loadResource();
	}
	
	public static void loadResource(){ //Load pictures at 1 time
//		ClassLoader loader = ClassLoader.getSystemClassLoader();
		rocketImage = new Image(ClassLoader.getSystemResource("img/rocketmake3.png").toString());
		backgroundImage = new Image(ClassLoader.getSystemResource("img/background.jpg").toString());

		soundtrack = new AudioClip(ClassLoader.getSystemResource("audio/unity.mp3").toString());
		ldgoforlaunch = new AudioClip(ClassLoader.getSystemResource("audio/ldgoforlaunch.mp3").toString());
		countdown = new AudioClip(ClassLoader.getSystemResource("audio/countdown.mp3").toString());
		stageonepropulsionnominal = new AudioClip(ClassLoader.getSystemResource("audio/stageonepropulsionnominal.mp3").toString());
		stageseperationconfirmed = new AudioClip(ClassLoader.getSystemResource("audio/stageseperationconfirmed.mp3").toString());
		enginecombustion = new AudioClip(ClassLoader.getSystemResource("audio/enginecombustion.mp3").toString());
	}
}
