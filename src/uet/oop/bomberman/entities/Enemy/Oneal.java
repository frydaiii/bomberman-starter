package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.animatedEntities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Image img, double speed, Bomber bomber) {
        super(x, y, img, speed, 200);
        super.fp = new findPathAdvanced(bomber, this);
    }

    @Override
    public void chooseSprite() {
        switch (direction) {
            //move right animation
            case 1:
                img = Sprite.movingSprite(Sprite.oneal_right1,
                        Sprite.oneal_right2,
                        Sprite.oneal_right3,
                        animatedTime, 200_000_000).getFxImage();
                break;
            //move left animation
            case 3:
                img = Sprite.movingSprite(Sprite.oneal_left1,
                        Sprite.oneal_left2,
                        Sprite.oneal_left3,
                        animatedTime, 200_000_000).getFxImage();
                break;
            // move up and down animation
            default:
                img = Sprite.oneal_left1.getFxImage();
                break;
        }
    }

    @Override
    public void afterDie() {
        if (dyingAnimatedTime > 0) {
            img = Sprite.movingSprite(Sprite.oneal_dead,
                    Sprite.oneal_dead,
                    Sprite.oneal_dead,
                    dyingAnimatedTime,
                    500_000_000).getFxImage();
            dyingAnimatedTime -= BombermanGame.TIME_UNIT;
        } else {
            BombermanGame.increaseScore(point);
            setVisible(false);
        }
    }
}
