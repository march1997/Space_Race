package main;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Resources {
	// for loading resources
	public static Image rocketImage;
	public static Image enginefire;
	public static Image backgroundImage;
	public static Image oneCoinImage;
	public static Image fiveCoinImage;
	public static Image explosionImage;
	public static Image planeImage;
	public static AudioClip soundtrack;
	public static AudioClip ldgoforlaunch;
	public static AudioClip countdown;
	public static AudioClip stageonepropulsionnominal;
	public static AudioClip stageseperationconfirmed;
	public static AudioClip enginecombustion;
	public static AudioClip collectcoinsound;
	public static AudioClip explosionsound;
	
	static{
		try {
			loadResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadResource(){
		//load all the resources once
		rocketImage = new Image(ClassLoader.getSystemResource("img/rocket/full.png").toString());
		enginefire = new Image(ClassLoader.getSystemResource("img/rocket/enginefire1.gif").toString());
		backgroundImage = new Image(ClassLoader.getSystemResource("img/background.jpg").toString());
		oneCoinImage = new Image(ClassLoader.getSystemResource("img/onecoin.png").toString());
		fiveCoinImage = new Image(ClassLoader.getSystemResource("img/fivecoin.png").toString());
		explosionImage = new Image(ClassLoader.getSystemResource("img/explosion.gif").toString());
		planeImage = new Image(ClassLoader.getSystemResource("img/planefix.png").toString());

		soundtrack = new AudioClip(ClassLoader.getSystemResource("audio/unity.mp3").toString());
		ldgoforlaunch = new AudioClip(ClassLoader.getSystemResource("audio/ldgoforlaunch.mp3").toString());
		countdown = new AudioClip(ClassLoader.getSystemResource("audio/countdown.mp3").toString());
		stageonepropulsionnominal = new AudioClip(ClassLoader.getSystemResource("audio/stageonepropulsionnominal.mp3").toString());
		stageseperationconfirmed = new AudioClip(ClassLoader.getSystemResource("audio/stageseperationconfirmed.mp3").toString());
		enginecombustion = new AudioClip(ClassLoader.getSystemResource("audio/enginecombustion.mp3").toString());
		collectcoinsound = new AudioClip(ClassLoader.getSystemResource("audio/collectcoinsound.mp3").toString());
		explosionsound = new AudioClip(ClassLoader.getSystemResource("audio/explosion.mp3").toString());
	}
}
