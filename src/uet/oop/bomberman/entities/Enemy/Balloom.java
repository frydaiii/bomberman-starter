package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.staticEntities.Wall;
import uet.oop.bomberman.entities.Enemy.findPath;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.soundEffect.Sound;

import java.util.Random;

public class Balloom extends Enemy {
    public Balloom(int x, int y, Image img, double speed) {
        super(x, y, img, speed, 100);
        super.fp = new findPathBasic();
    }

    @Override
    public void chooseSprite() {
        switch (direction) {
            //move right
            case 1:
                img = Sprite.balloom_right2.getFxImage();
                if (moving) {
                    img = Sprite.movingSprite(Sprite.balloom_right1,
                            Sprite.balloom_right2,
                            Sprite.balloom_right3,
                            animatedTime, 200_000_000).getFxImage();
                }
                break;
            //move left
            case 3:
                img = Sprite.balloom_left1.getFxImage();
                if (moving) {
                    img = Sprite.movingSprite(Sprite.balloom_left1,
                            Sprite.balloom_left2,
                            Sprite.balloom_left3,
                            animatedTime, 200_000_000).getFxImage();
                }
                break;
            //move up and down
            default:
                img = Sprite.balloom_right1.getFxImage();
                break;
        }
    }

    @Override
    public void afterDie() {
        if (dyingAnimatedTime > 0) {
            img = Sprite.movingSprite(Sprite.balloom_dead,
                    Sprite.balloom_dead,
                    Sprite.balloom_dead,
                    dyingAnimatedTime,
                    500_000_000).getFxImage();
            dyingAnimatedTime -= BombermanGame.TIME_UNIT;
        } else {
            BombermanGame.increaseScore(point);
            setVisible(false);
        }
    }
}
