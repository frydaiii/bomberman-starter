package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.animatedEntities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {
    public Kondoria(int x, int y, Image img, double speed, Bomber bomber) {
        super(x, y, img, speed, 500);
        super.fp = new findPathAdvanced(bomber, this);
        this.throughtWall = false;
    }

    @Override
    public void chooseSprite() {
        switch (direction) {
            //move right animation
            case 1:
                img = Sprite.movingSprite(Sprite.kondoria_right1,
                        Sprite.kondoria_right2,
                        Sprite.kondoria_right3,
                        animatedTime, 200_000_000).getFxImage();
                break;
            //move left animation
            case 3:
                img = Sprite.movingSprite(Sprite.kondoria_left1,
                        Sprite.kondoria_left2,
                        Sprite.kondoria_left3,
                        animatedTime, 200_000_000).getFxImage();
                break;
            // move up and down animation
            default:
                img = Sprite.kondoria_left1.getFxImage();
                break;
        }
    }

    @Override
    public void afterDie() {
        if (dyingAnimatedTime > 0) {
            img = Sprite.movingSprite(Sprite.kondoria_dead,
                    Sprite.kondoria_dead,
                    Sprite.kondoria_dead,
                    dyingAnimatedTime,
                    500_000_000).getFxImage();
            dyingAnimatedTime -= BombermanGame.TIME_UNIT;
        } else {
            BombermanGame.increaseScore(point);
            setVisible(false);
        }
    }
}
