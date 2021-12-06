package uet.oop.bomberman.soundEffect;

import java.nio.file.Paths;
import javafx.scene.media.*;
import javafx.util.Duration;


public class Sound {
    static MediaPlayer mediaPlayer;

    //file test
    public static void music() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//test.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    //file bonusStage
    public static void bonusStage() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//bonusStage.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file death
    public static void death() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//death.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file ending
    public static void ending() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//ending.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file explosion
    public static void explosion() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//explosion.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file findTheExit
    public static void findTheExit() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//findTheExit.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file gameOver
    public static void gameOver() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//gameOver.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file inGame
    public static void inGame() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//inGame.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file invincibility
    public static void invincibility() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//invincibility.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file item
    public static void item() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//item.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file levelComplete
    public static void levelComplete() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//levelComplete.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file levelStart
    public static void levelStart() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//levelStart.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file planBomb
    public static void planBomb() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//planBomb.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file speedItem
    public static void speedItem() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//speedItem.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file titleScreen
    public static void titleScreen() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//titleScreen.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file walkHorizontally
    public static void walkHorizontally() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//walkHorizontally.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1);
    }

    //file walkVertically
    public static void walkVertically() {
        String s = "src//uet//oop//bomberman//soundEffect//fileWav//walkVertically.wav";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
//        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setCycleCount(1);
    }

}



