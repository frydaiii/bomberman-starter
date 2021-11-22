package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.animatedEntities.AnimatedEntity;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {
    public Doll(int x, int y, Image img, double speed) {
        super(x, y, img, speed, 250);
        super.fp = new findPathBasic();
        this.MAX_STEPS = Sprite.DEFAULT_SIZE * 2;
    }

    @Override
    public void chooseSprite() {
        switch (direction) {
            //move right
            case 1:
                img = Sprite.movingSprite(Sprite.doll_right1,
                        Sprite.doll_right2,
                        Sprite.doll_right3,
                        animatedTime, 200_000_000).getFxImage();
                break;
            //move left
            case 3:
                img = Sprite.movingSprite(Sprite.doll_left1,
                        Sprite.doll_left2,
                        Sprite.doll_left3,
                        animatedTime, 200_000_000).getFxImage();
                break;
            //move up and down
            default:
                img = Sprite.doll_right1.getFxImage();
                break;
        }
    }

    @Override
    public void afterDie() {
        if (dyingAnimatedTime > 0) {
            img = Sprite.movingSprite(Sprite.doll_dead,
                    Sprite.doll_dead,
                    Sprite.doll_dead,
                    dyingAnimatedTime,
                    500_000_000).getFxImage();
            dyingAnimatedTime -= BombermanGame.TIME_UNIT;
        } else {
            BombermanGame.increaseScore(point);
            setVisible(false);
        }
    }
}
